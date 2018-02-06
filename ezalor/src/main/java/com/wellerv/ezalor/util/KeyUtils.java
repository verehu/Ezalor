package com.wellerv.ezalor.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huwei on 18-1-8.
 */

public class KeyUtils {
    private static Map<Integer, StringBuilder> sKeyBuilder = new ConcurrentHashMap<>();

    public static String getKey(int fid) {
        int tid = android.os.Process.myTid();
        StringBuilder builder = sKeyBuilder.get(tid);

        if (builder == null) {
            builder = new StringBuilder();
            builder.append(fid);
            builder.append(":");
            builder.append(tid);

            sKeyBuilder.put(tid, builder);
        }

        return builder.toString();
    }
}
