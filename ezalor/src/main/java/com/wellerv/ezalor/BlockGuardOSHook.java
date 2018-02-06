package com.wellerv.ezalor;

import android.system.ErrnoException;

import java.io.FileDescriptor;
import java.io.InterruptedIOException;
import java.nio.ByteBuffer;

import libcore.io.ForwardingOs;
import libcore.io.Os;

/**
 * 替换BlockGuradOS的类
 * <p>
 * Created by huwei on 18-1-8.
 */

public class BlockGuardOSHook extends ForwardingOs {

    public BlockGuardOSHook(Os os) {
        super(os);
    }

    @Override
    public int pread(FileDescriptor fd, ByteBuffer buffer, long offset) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.pread(fd, buffer, offset);
        IOHook.get().readCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int pread(FileDescriptor fd, byte[] bytes, int byteOffset, int byteCount, long offset) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.pread(fd, bytes, byteOffset, byteCount, offset);
        IOHook.get().readCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int read(FileDescriptor fd, ByteBuffer buffer) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.read(fd, buffer);
        IOHook.get().readCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int read(FileDescriptor fd, byte[] bytes, int byteOffset, int byteCount) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.read(fd, bytes, byteOffset, byteCount);
        IOHook.get().readCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int readv(FileDescriptor fd, Object[] buffers, int[] offsets, int[] byteCounts) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.readv(fd, buffers, offsets, byteCounts);
        IOHook.get().readCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int pwrite(FileDescriptor fd, ByteBuffer buffer, long offset) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.pwrite(fd, buffer, offset);
        IOHook.get().writeCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int pwrite(FileDescriptor fd, byte[] bytes, int byteOffset, int byteCount, long offset) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.pwrite(fd, bytes, byteOffset, byteCount, offset);
        IOHook.get().writeCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int write(FileDescriptor fd, ByteBuffer buffer) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.write(fd, buffer);
        IOHook.get().writeCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int write(FileDescriptor fd, byte[] bytes, int byteOffset, int byteCount) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.write(fd, bytes, byteOffset, byteCount);
        IOHook.get().writeCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public int writev(FileDescriptor fd, Object[] buffers, int[] offsets, int[] byteCounts) throws ErrnoException, InterruptedIOException {
        int hookByteCount = super.writev(fd, buffers, offsets, byteCounts);
        IOHook.get().writeCall(fd, hookByteCount);
        return hookByteCount;
    }

    @Override
    public void close(FileDescriptor fd) throws ErrnoException {
        IOHook.get().closeBeforeCall(fd);
        super.close(fd);
    }
}
