package com.wellerv.ezalor;

import com.wellerv.ezalor.util.Utils;

/**
 * Created by huwei on 18-1-23.
 */

public class Env {
    public static String WORKSPACE = android.os.Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/ezalor/";
    public static String DBDIR = WORKSPACE + Utils.getPackageName();


    public interface ConfigKey {
        public static final String TARGET_PKG_LIST = "TARGET_PKG_LIST";
    }
}
