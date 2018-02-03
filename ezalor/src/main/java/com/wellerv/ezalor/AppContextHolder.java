package com.wellerv.ezalor;

import android.content.Context;


import com.wellerv.ezalor.util.LogUtils;
import com.wellerv.ezalor.util.ReflectUtils;

import java.lang.reflect.Method;

/**
 * Created by huwei on 18-1-10.
 */

public class AppContextHolder {
    private static Context sAppContext;

    public static Context getAppContext() {
        if (sAppContext == null) {
            try {
                Class cl = Class.forName("android.app.ActivityThread");
                Method method = ReflectUtils.getMethod(cl, "currentApplication");
                sAppContext = (Context) ReflectUtils.invokeMethod(method, cl);
            } catch (Throwable e) {
                LogUtils.logeForce(e);
            }
        }
        return sAppContext;
    }

    public static void setContext(Context context) {
        sAppContext = context;
    }
}
