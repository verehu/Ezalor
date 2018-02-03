package com.wellerv.ezalor.sample;

import android.app.Application;

import com.wellerv.ezalor.Ezalor;

/**
 * Created by huwei on 18-2-2.
 */

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Ezalor.get().init(this);
    }
}
