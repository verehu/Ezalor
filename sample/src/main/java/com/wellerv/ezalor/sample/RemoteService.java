package com.wellerv.ezalor.sample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wellerv.ezalor.util.LogUtils;
import com.wellerv.ezalor.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by huwei on 18-2-6.
 */

public class RemoteService extends Service {
    private static final String TAG = "RemoteService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final File cacheDir = getExternalCacheDir();
        //common io
        Observable.intervalRange(0, 8, 0, 1200, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtils.logi(TAG, "common io count:" + aLong +
                                "   thread:" + Thread.currentThread());

                        String fileName = aLong + "common.test";
                        OutputStream os = new FileOutputStream(cacheDir + "/" + fileName);
                        int randomCount = new Random().nextInt(50) + 5;

                        byte[] temp = new byte[1024];
                        for (int i = 0; i < randomCount; i++) {
                            os.write(temp);
                        }
                        Utils.closeIOStream(os);
                    }
                });

        return super.onStartCommand(intent, flags, startId);
    }
}
