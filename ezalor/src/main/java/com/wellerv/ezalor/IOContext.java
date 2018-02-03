package com.wellerv.ezalor;

/**
 * Created by huwei on 17-12-21.
 */
final class IOContext {
    public static final int IDLE = 0;
    public static final int OPEN = 1;
    public static final int STREAMING = 2; //读或者写中
    public static final int CLOSED = 3;

    public int state;
    public final IORecord ioRecord;

    public IOContext() {
        ioRecord = new IORecord();
    }
}
