package com.wellerv.ezalor.util;

import android.os.Build;
import android.os.Process;
import android.system.Os;

import com.wellerv.ezalor.AppContextHolder;

import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Created by huwei on 17-12-20.
 */

public class Utils {
    private static String sProcessName;

    static {
        System.loadLibrary("ioutils-lib");
    }

    public static String getPathByFD(int fd) {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                return Os.readlink(String.format("/proc/%d/fd/%d", android.os.Process.myPid(), fd));
            }
            return readlink(fd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getFD(FileDescriptor fileDescriptor) {
        return ReflectUtils.getObjectByFieldName(fileDescriptor, "descriptor", Integer.class);
    }

    public static String getProcessName() {
        if (sProcessName == null) {
            try {
                Class cl = Class.forName("android.app.ActivityThread");
                Method method = ReflectUtils.getMethod(cl, "currentActivityThread");
                Method getProcessNameMethod = ReflectUtils.getMethod(cl, "getProcessName");
                sProcessName = (String) ReflectUtils.invokeMethod(getProcessNameMethod, ReflectUtils.invokeMethod(method, cl));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sProcessName;
    }


    public static String getPackageName() {
        return AppContextHolder.getAppContext().getPackageName();
    }

    /**
     * 关闭io流
     *
     * @param closeable
     */
    public static void closeIOStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LogUtils.logeForce(e);
            }
        }
    }

    public static boolean isAppProcess() {
        int uid = Process.myUid();
        return uid >= Process.FIRST_APPLICATION_UID && uid <= Process.LAST_APPLICATION_UID;
    }

    public static String getDBNameByProcess(String process) {
        return "ezalor_" + process + ".db";
    }

    public static native String readlink(int fd);
}
