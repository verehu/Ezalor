package com.wellerv.ezalor.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by huwei on 18-1-8.
 */

public class KeyUtils {
    private static Map<Long, StringBuilder> sKeyBuilder = new ConcurrentHashMap<>();

    public static String getKey(int fid) {
        long tid = Thread.currentThread().getId();
        StringBuilder builder = sKeyBuilder.get(tid);

        if (builder == null) {
            builder = new StringBuilder();
            builder.append(fid);
            builder.append(":");
            builder.append(Thread.currentThread().getId());

            sKeyBuilder.put(tid, builder);
        }

        return builder.toString();
    }
}
