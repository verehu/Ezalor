package com.gionee.ezalor.util;

import android.content.Context;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class UtilsTest {
    private static String TAG = "UtilsTest";
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.gionee.ezalor", appContext.getPackageName());
    }

    @Test
    public void getFilePathByFileDescriptor() throws Exception {
        File file = new File(Environment.getExternalStorageDirectory() + "/test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileInputStream is = new FileInputStream(file);
        FileDescriptor fileDescriptor = is.getFD();
        int fd = ReflectUtils.getObjectByFieldName(fileDescriptor, "descriptor", Integer.class);

        Log.i(TAG, "filePath:" + Utils.readlink(fd));
    }
}
