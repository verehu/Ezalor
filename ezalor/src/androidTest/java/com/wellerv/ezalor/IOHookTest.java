package com.gionee.ezalor;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.gionee.ezalor.base.AppContextHolder;
import com.gionee.ezalor.base.util.LogUtils;
import com.wellerv.ezalor.IOHook;
import com.wellerv.ezalor.tools.Config;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileDescriptor;
import android.os.SystemProperties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Created by huwei on 18-1-2.
 */
@RunWith(AndroidJUnit4.class)
public class IOHookTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.gionee.ezalor.test", appContext.getPackageName());
    }

    @Test
    public void getContext() throws Exception {
        LogUtils.logdForce("context:" + AppContextHolder.getAppContext());
    }

    @Test
    public void init() throws Exception{
        IOHook.get().init(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void fileOperate() throws Exception {
        init();

        Thread.sleep(300);

        for (int i = 0; i < 20; i++) {
            IOHook.get().writeCall(FileDescriptor.out, (i + 1) * 1024 * 1000);
            Thread.sleep(i * 200);
        }

        IOHook.get().closeBeforeCall(FileDescriptor.out);
    }

    @Test
    public void setProc() throws Exception {
        SystemProperties.set("persist.ezalor.enable", "2343242423523");
        //LogUtils.logi("getprop:" + SystemProperties.getBoolean("persist.ezalor.enable", true));
    }

    @Test
    public void testString() throws Exception {
        String a = new String("qwertyuiop");
        String b = new String("qwertyuiop");

        assertSame(a, b);
    }

    @Test
    public void testConfig() throws Exception {
        new Config().matchAssignPackage();
    }
}
