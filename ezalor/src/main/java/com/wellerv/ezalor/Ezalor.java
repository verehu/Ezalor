package com.wellerv.ezalor;

import android.content.Context;

/**
 * Created by huwei on 18-2-2.
 */

public class Ezalor {
    private static Ezalor mInstance = new Ezalor();

    private Ezalor() {

    }

    public static Ezalor get() {
        return mInstance;
    }

    public void init(Context context) {
        IOHook.get().init(context);
    }
}
