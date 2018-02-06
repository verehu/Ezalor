package com.wellerv.ezalor.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wellerv.ezalor.util.LogUtils;
import com.wellerv.ezalor.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private boolean isStartIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onStartIOClick(View view) {
        if (isStartIO) {
            Toast.makeText(getBaseContext(), "io has been start!", Toast.LENGTH_LONG).show();
            return;
        }

        final File cacheDir = getExternalCacheDir();
        if (cacheDir != null && !cacheDir.exists()) {
            if (!cacheDir.mkdirs()) {
                Toast.makeText(getBaseContext(),
                        "cacheDir create failed.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        Toast.makeText(getBaseContext(), "start io", Toast.LENGTH_LONG).show();

        isStartIO = true;

        //common io
        Observable.intervalRange(0, 20, 0, 1200, TimeUnit.MILLISECONDS)
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

        //unbuffered io
        Observable.intervalRange(0, 6, 0, 200, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtils.logi(TAG, "unbuffered io count:" + aLong +
                                "   thread:" + Thread.currentThread());

                        String fileName = aLong + "unbuffered.test";
                        OutputStream os = new FileOutputStream(cacheDir + "/" + fileName);
                        int randomCount = new Random().nextInt(20) + 10;

                        byte[] temp = new byte[100];
                        for (int i = 0; i < randomCount; i++) {
                            os.write(temp);
                        }
                        Utils.closeIOStream(os);
                    }
                });

        //io in main thread
        Observable.intervalRange(0, 3, 0, 500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        LogUtils.logi(TAG, "unbuffered io count:" + aLong +
                                "   thread:" + Thread.currentThread());

                        String fileName = aLong + "main.test";
                        OutputStream os = new FileOutputStream(cacheDir + "/" + fileName);
                        int randomCount = new Random().nextInt(20) + 10;

                        byte[] temp = new byte[4096];
                        for (int i = 0; i < randomCount; i++) {
                            os.write(temp);
                        }
                        Utils.closeIOStream(os);
                    }
                });

        //start remote service
        startService(new Intent(this, RemoteService.class));
    }
}
