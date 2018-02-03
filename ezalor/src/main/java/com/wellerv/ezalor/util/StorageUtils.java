package com.wellerv.ezalor.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

public class StorageUtils {
    public static boolean isSdcardMounted() {
        return Environment.MEDIA_MOUNTED.equals(getExternalStorageState());
    }

    private static String getExternalStorageState() {
        try {
            return Environment.getExternalStorageState();
        } catch (Exception e) {
            LogUtils.logeForce(e);
            return Environment.MEDIA_UNMOUNTED;
        }

    }

    public static List<Long> getAllStorageSizes(Context context) {
        List<Long> allStorageSizes = new ArrayList<Long>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", paramClasses);
            getVolumePathsMethod.setAccessible(true);
            Object[] params = {};
            String[] invoke = (String[]) getVolumePathsMethod.invoke(storageManager, params);
            if (invoke == null) {
                return allStorageSizes;
            }
            for (int i = 0; i < invoke.length; i++) {
                String path = invoke[i];
                if (isMounted(getStorageState(context, path))) {
                    allStorageSizes.add(getTotalSizeByPath(path));
                }
            }

        } catch (Exception e1) {
            LogUtils.logeForce(e1);
        }

        return allStorageSizes;
    }

    public static String getStorageState(Context context, String path) {
        String state = Environment.MEDIA_UNMOUNTED;
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {String.class};
            Method getVolumeStateMethod = StorageManager.class.getMethod("getVolumeState", paramClasses);
            getVolumeStateMethod.setAccessible(true);
            state = (String) getVolumeStateMethod.invoke(storageManager, path);
        } catch (Exception exception) {
            LogUtils.logeForce(exception);
        }
        return state;
    }

    private static boolean isMounted(String state) {
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    @SuppressLint("SdCardPath")
    public static String getExternalStorageDirectoryPath() {
        String rootpath = "/sdcard";
        try {
            rootpath = Environment.getExternalStorageDirectory().getPath();
        } catch (Exception e) {
            LogUtils.loge(StorageUtils.class.getSimpleName(),
                    LogUtils.getMethodName("getExternalStorageDirectoryPath") + e.toString());
        }

        if (!rootpath.endsWith(File.separator)) {
            rootpath += File.separator;
        }
        return rootpath;
    }

    public static boolean isSdcardNotMounted() {
        return !isSdcardMounted();
    }

    @SuppressLint("NewApi")
    public static long getTotalSizeByPath(String path) {
        StatFs stat = new StatFs(path);
        long blockCount = 0;
        long blockSize = 0;
        long totalSize = 0;
        try {
            if (Build.VERSION.SDK_INT >= 19) {
                blockCount = stat.getBlockCountLong();
                blockSize = stat.getBlockSizeLong();
            } else {

                blockCount = stat.getBlockCount();
                blockSize = stat.getBlockSize();
            }

        } catch (Exception e) {
            LogUtils.logeForce(e);
        }

        totalSize = blockCount * blockSize;
        return totalSize;
    }
}
