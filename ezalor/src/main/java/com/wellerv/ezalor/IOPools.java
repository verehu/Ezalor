package com.wellerv.ezalor;

import android.content.Context;
import android.os.Process;


import com.wellerv.ezalor.data.database.IOHistoryDao;
import com.wellerv.ezalor.util.LogUtils;
import com.wellerv.ezalor.util.Utils;


import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static com.wellerv.ezalor.data.Mode.READ;
import static com.wellerv.ezalor.util.KeyUtils.getKey;

/**
 * Created by huwei on 17-12-21.
 */

final class IOPools {
    private static final int RECYCLER_MAX_SIZE = 20;

    private Map<String, IOContext> mPools = new Hashtable<>();
    private ConcurrentLinkedQueue<IOContext> mRecyclers = new ConcurrentLinkedQueue<>();

    private ExecutorService mThreadPool;
    private IOHistoryDao mIOHistoryDao;
    private Context mContext;

    public IOPools(Context context) {
        this.mContext = context;

        mThreadPool = Executors.newCachedThreadPool();
        mIOHistoryDao = new IOHistoryDao(mContext);
    }

    private void open(int fd) {
        LogUtils.logi(IOHook.class.getSimpleName() + " openAfterCall fd:" + fd);

        final IOContext ioContext = getIOContext();
        ioContext.state = IOContext.OPEN;
        ioContext.ioRecord.fd = fd;
        ioContext.ioRecord.path = Utils.getPathByFD(fd);
        ioContext.ioRecord.openTime = System.currentTimeMillis();

        ioContext.ioRecord.process = Utils.getProcessName();
        ioContext.ioRecord.thread = Thread.currentThread().getName();
        ioContext.ioRecord.processId = Process.myPid();
        ioContext.ioRecord.threadId = Process.myTid();

        ioContext.ioRecord.stacktrace = getStackTrace();

        mPools.put(getKey(ioContext.ioRecord.fd), ioContext);
    }

    private String getStackTrace() {
        StringBuilder builder = new StringBuilder();
        Throwable t = new Throwable();

        StackTraceElement[] elements = t.getStackTrace();
        //去除框架内部调用堆栈
        for (int i = 5; i < elements.length; i++) {
            builder.append(elements[i]);
            builder.append("\n");
        }

        return builder.toString();
    }

    private void preHandleOpen(int fd) {
        LogUtils.logi("preHandleOpen key:" + getKey(fd) + "     pools:" + mPools);

        if (!isOpen(fd)) {
            open(fd);
        }
    }

    private boolean isOpen(int fd) {
        return mPools.containsKey(getKey(fd));
    }

    public void stream(int fd, int byteCount, int mode) {
        preHandleOpen(fd);

        final IOContext ioContext = findIOContext(fd);
        if (ioContext == null || (ioContext.state != IOContext.OPEN
                && ioContext.state != IOContext.STREAMING)) {
            return;
        }

        LogUtils.logi("stream ---> fd:" + fd + "    byteCount:" + byteCount + " mode:" + mode);

        ioContext.ioRecord.mode = mode;
        ioContext.state = IOContext.STREAMING;
        if (mode == READ) {
            ioContext.ioRecord.readCount++;
            ioContext.ioRecord.readBytes += byteCount;
        } else {
            ioContext.ioRecord.writeCount++;
            ioContext.ioRecord.writeBytes += byteCount;
        }
    }

    public void close(int fd) {
        final IOContext ioContext = findIOContext(fd);

        LogUtils.logi("findIOContext ioContext:" + ioContext + "    " + (ioContext != null ? ("state:" + ioContext.state) : ""));

        if (ioContext == null || IOContext.CLOSED == ioContext.state) {
            return;
        }

        ioContext.state = IOContext.CLOSED;
        ioContext.ioRecord.closeTime = System.currentTimeMillis();
        ioContext.ioRecord.completeFields();

        final String recycleKey = getKey(fd);
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                LogUtils.logi(Thread.currentThread().getName() + ": ioRecord insert :" + ioContext.ioRecord);
                mIOHistoryDao.insert(ioContext.ioRecord);

                recycle(ioContext, recycleKey);
            }
        });
    }

    private IOContext getIOContext() {
        IOContext ioContext = null;
        final ConcurrentLinkedQueue<IOContext> recyclerQueue = mRecyclers;
        if (recyclerQueue != null && !recyclerQueue.isEmpty()) {
            ioContext = recyclerQueue.poll();
        }

        if (ioContext == null) {
            ioContext = new IOContext();
        }

        ioContext.state = IOContext.IDLE;
        ioContext.ioRecord.reset();

        return ioContext;
    }

    private IOContext findIOContext(int fd) {
        return mPools.get(getKey(fd));
    }

    private void recycle(IOContext ioContext, String key) {
        if (ioContext != null && mRecyclers.size() < RECYCLER_MAX_SIZE) {
            mRecyclers.add(ioContext);
        }

        mPools.remove(key);

        LogUtils.logi("remove key from pools--- key:" + key
                + "  pools:" + mPools);
    }
}
