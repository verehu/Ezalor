package com.wellerv.ezalor.tools;

import android.os.SystemProperties;
import android.text.TextUtils;

import com.wellerv.ezalor.AppContextHolder;
import com.wellerv.ezalor.Env;
import com.wellerv.ezalor.util.LogUtils;
import com.wellerv.ezalor.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


import static com.wellerv.ezalor.data.SystemPropertiesContants.CONFIG_PATH;

/**
 * Created by huwei on 18-1-3.
 */

public class Config {

    private Properties mProperties = new Properties();
    private boolean mLoadSuccess = false;

    public Config() {
        FileInputStream fis = null;
        try {
            String configPath = SystemProperties.get(CONFIG_PATH, "");
            if (TextUtils.isEmpty(configPath)) return;
            File file = new File(configPath);
            if (file.exists() && !file.isDirectory()) {
                fis = new FileInputStream(file);
                mProperties.load(fis);

                mLoadSuccess = true;
            }
        } catch (Exception e) {
            LogUtils.logeForce(e);
        } finally {
            Utils.closeIOStream(fis);
        }
    }

    public List<String> getAssignPackageList() {
        if (!mLoadSuccess) {
            return null;
        }
        return Arrays.asList(mProperties.getProperty(Env.ConfigKey.TARGET_PKG_LIST).split(","));
    }

    public boolean matchAssignPackage() {
        if (!mLoadSuccess) {
            return false;
        }
        List<String> pkgList = getAssignPackageList();
        String appPackageName = AppContextHolder.getAppContext().getPackageName();
        return !TextUtils.isEmpty(appPackageName) && pkgList.contains(appPackageName);
    }
}
