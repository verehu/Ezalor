package com.wellerv.ezalor.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.wellerv.ezalor.AppContextHolder;
import com.wellerv.ezalor.BuildConfig;

public class LogUtils {

    public static final String GLOBAL_TAG = "Ezalor";
    private static final String ENABLE_SAVELOG_FLAG_FOLDER = "Ezalor_1234567890savelog";
    private static final String TAG = "LogUtils";
    private static final String POINT = ".";
    // 默认关闭
    private static boolean sEnableAllLog = true;
    private static boolean sIsSaveLog = false;

    public static void logi(String msg) {
        logi(GLOBAL_TAG, msg);
    }

    public static void logi(String tag, String msg) {
        if (disableAllLog()) {
            return;
        }

        Log.i(getCurrentAppTag() + POINT + tag, msg);
        saveLogIfNeeded(tag, msg, "i");
    }

    private static boolean disableAllLog() {
        return !sEnableAllLog;
    }

    public static void logv(String tag, String msg) {
        if (disableAllLog()) {
            return;
        }
        Log.v(getCurrentAppTag() + POINT + tag, msg);
        saveLogIfNeeded(tag, msg, "V");
    }

    public static void logd(String tag, String msg) {
        if (disableAllLog()) {
            return;
        }

        Log.d(getCurrentAppTag() + POINT + tag, msg);
        saveLogIfNeeded(tag, msg, "D");

    }

    public static void loge(String msg) {
        loge(GLOBAL_TAG, msg);
    }

    public static void loge(String tag, String msg) {
        if (disableAllLog()) {
            return;
        }

        Log.e(getCurrentAppTag() + POINT + tag, msg);
        saveLogIfNeeded(tag, msg, "E");

    }

    public static void loge(String tag, String msg, Throwable e) {
        if (disableAllLog()) {
            return;
        }

        Log.e(getCurrentAppTag() + POINT + tag, msg, e);
        saveLogIfNeeded(tag, msg, "E");

    }

    public static String getMethodName(String methodName) {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append(Thread.currentThread().getName());
            sb.append("-> ");
            sb.append(methodName);
            sb.append("()");
            sb.append(" ");
        } catch (Exception e) {
            printException(e);
        }
        return sb.toString();
    }

    private static void saveLogIfNeeded(String tag, String msg, String logLevel) {
        if (sIsSaveLog) {
            try {
                if (AppContextHolder.getAppContext() == null) {
                    Log.d(getCurrentAppTag() + POINT + tag, "  IOHook.get().getAppContext() is null");
                    return;
                }
            } catch (Exception e) {
                printException(e);
            }
        }
    }

    private static void printException(Exception e) {
        //日志类的异常统一输出，不能再次调用自己，否则可以导致死循环
        e.printStackTrace();
    }

    // *****************************
    public static void logdForce(String msg) {
        Log.d(getCurrentAppTagForce(), msg);
        saveLogIfNeeded(getCurrentAppTagForce(), msg, "D");
    }

    public static void logwForce(String msg) {
        Log.w(getCurrentAppTagForce(), msg);
        saveLogIfNeeded(getCurrentAppTagForce(), msg, "W");
    }

    public static void logwForce(Throwable e) {
        String msg = Log.getStackTraceString(e);
        Log.w(getCurrentAppTagForce(), msg);
        saveLogIfNeeded(getCurrentAppTagForce(), msg, "W");
    }

    public static void logeForce(Throwable e) {
        String msg = Log.getStackTraceString(e);
        Log.e(getCurrentAppTagForce(), msg);
        saveLogIfNeeded(getCurrentAppTagForce(), msg, "E");
    }

    /**
     * 获取当前应用的日志TAG
     */
    private static String sCurrentAppTag = "";

    private static String getCurrentAppTag() {
        if (TextUtils.isEmpty(sCurrentAppTag)) {
            synchronized (LogUtils.class) {
                if (TextUtils.isEmpty(sCurrentAppTag)) { // NOSONAR
                    Context context = AppContextHolder.getAppContext();
                    if (context != null) {
                        sCurrentAppTag = GLOBAL_TAG + "_" + context.getPackageName() + "_"
                                + BuildConfig.VERSION_NAME;
                        return sCurrentAppTag;
                    } else {
                        return GLOBAL_TAG;
                    }
                }
            }
        }


        return sCurrentAppTag;
    }

    /**
     * 获取当前应用的Force日志TAG
     */
    private static String sCurrentAppTagForce = "";

    /**
     * 获取强制输出的日志TAG
     *
     * @return
     */
    private static String getCurrentAppTagForce() {
        if (TextUtils.isEmpty(sCurrentAppTagForce)) {
            String tag = getCurrentAppTag();
            if (GLOBAL_TAG.equals(tag)) {
                return tag + "_Force";
            }
            sCurrentAppTagForce = tag + "_Force";
        }
        return sCurrentAppTagForce;
    }

}