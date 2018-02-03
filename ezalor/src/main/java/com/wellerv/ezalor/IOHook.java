package com.wellerv.ezalor;

import android.content.Context;
import android.content.pm.PackageManager;

import com.wellerv.ezalor.util.LogUtils;
import com.wellerv.ezalor.util.Utils;

import java.io.FileDescriptor;

import libcore.io.Libcore;

import static com.wellerv.ezalor.data.Mode.READ;
import static com.wellerv.ezalor.data.Mode.WRITE;

/**
 * Created by huwei on 17-12-21.
 */
final class IOHook {

    private IOPools mPools;
    private Context mContext;

    private volatile boolean mInitFinshed = false;
    private static IOHook mInstance;

    private IOHook() {

    }

    private boolean canDo() {
        return true;
    }

    private void checkPermission() {
        PackageManager pm = AppContextHolder.getAppContext().getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", Utils.getPackageName()));

        if (!permission) {
            throw new RuntimeException("Don't has android.permission.WRITE_EXTERNAL_STORAGE!");
        }
    }

    public static IOHook get() {
        if (mInstance == null) {
            synchronized (IOHook.class) {
                if (mInstance == null) {
                    mInstance = new IOHook();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        if (mInitFinshed || !canDo()) {
            return;
        }

        if (context == null) {
            return;
        }

        if (context.getApplicationContext() != null) {
            mContext = context.getApplicationContext();
        } else {
            mContext = context;
        }

        checkPermission();

        AppContextHolder.setContext(mContext);
        mPools = new IOPools(mContext);

        //替换成  框架的os
        Libcore.os = new BlockGuardOSHook(Libcore.os);
        mInitFinshed = true;

        LogUtils.logi(IOHook.class.getSimpleName() + " init success, version:" + BuildConfig.VERSION_NAME);
    }

    public void closeBeforeCall(FileDescriptor fd) {
        if (!mInitFinshed || !canDo()) {
            return;
        }

        LogUtils.logi(IOHook.class.getSimpleName() + " closeBeforeCall fd:" + Utils.getFD(fd));

        mPools.close(Utils.getFD(fd));
    }

    public void readCall(FileDescriptor fd, int byteCount) {
        if (!mInitFinshed || !canDo()) {
            return;
        }

        mPools.stream(Utils.getFD(fd), byteCount, READ);

        LogUtils.logi(IOHook.class.getSimpleName() + " readCall fd:" + Utils.getFD(fd));
    }

    public void writeCall(FileDescriptor fd, int byteCount) {
        if (!mInitFinshed || !canDo()) {
            return;
        }

        mPools.stream(Utils.getFD(fd), byteCount, WRITE);

        LogUtils.logi(IOHook.class.getSimpleName() + " writeCall fd:" + Utils.getFD(fd));
    }
}
