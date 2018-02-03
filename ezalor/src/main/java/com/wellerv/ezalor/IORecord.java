package com.wellerv.ezalor;

import android.content.ContentValues;

import com.wellerv.ezalor.data.Mode;

/**
 * Created by huwei on 17-12-21.
 */

public final class IORecord {

    public int fd;
    public int mode;

    public String path;
    public String process;
    public String thread;
    public int processId;
    public long threadId;
    public int readCount;
    public int readBytes;
    public long readTime;
    public int writeCount;
    public int writeBytes;
    public long writeTime;
    public String stacktrace;

    public long openTime;
    public long closeTime;

    public void reset() {
        fd = -1;
        path = null;
        process = null;
        thread = null;
        processId = -1;
        threadId = -1;
        readCount = 0;
        readBytes = 0;
        readTime = 0;
        writeCount = 0;
        writeBytes = 0;
        writeTime = 0;
        stacktrace = null;
    }

    /**
     * 补全字段
     */
    public void completeFields() {
        if (mode == Mode.READ) {
            readTime = closeTime - openTime;
        } else {
            writeTime = closeTime - openTime;
        }
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("path", path);
        contentValues.put("process", process);
        contentValues.put("thread", thread);
        contentValues.put("processId", processId);
        contentValues.put("threadId", threadId);
        contentValues.put("readCount", readCount);
        contentValues.put("readBytes", readBytes);
        contentValues.put("readTime", readTime);
        contentValues.put("writeCount", writeCount);
        contentValues.put("writeBytes", writeBytes);
        contentValues.put("writeTime", writeTime);
        contentValues.put("stacktrace", stacktrace);

        contentValues.put("openTime", openTime);
        contentValues.put("closeTime", closeTime);

        return contentValues;
    }

    @Override
    public String toString() {
        return "IORecord{" +
                "fd=" + fd +
                ", mode=" + mode +
                ", path='" + path + '\'' +
                ", process='" + process + '\'' +
                ", thread='" + thread + '\'' +
                ", readCount=" + readCount +
                ", readBytes=" + readBytes +
                ", readTime=" + readTime +
                ", writeCount=" + writeCount +
                ", writeBytes=" + writeBytes +
                ", writeTime=" + writeTime +
                ", stacktrace='" + stacktrace + '\'' +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}
