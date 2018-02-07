package com.wellerv.ezalor.sample;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.wellerv.ezalor.Ezalor;

/**
 * Created by huwei on 18-2-2.
 */

public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            Ezalor.get().init(this);
        } else {
            startActivity(new Intent(this, PermissionActivity.class));
        }
    }
}
