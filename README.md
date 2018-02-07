# Ezalor

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![PRs Welcome](https://img.shields.io/badge/prs-welcome-brightgreen.svg)](http://makeapullrequest.com)
[![Code Climate](https://img.shields.io/codeclimate/issues/github/me-and/mdf.svg)](https://github.com/WellerV/Ezalor/issues)

Ezalor is a io monitor library for Android, it support record all io operations.
You can get the performance of each io operation.
 
 ![Ezalor][1]
 
 ## [中文版点击这里](README_CN.md)
 
## Getting started
Add dependencies by adding the following lines to your app/build.gradle.
```groovy
dependencies {
    //ezalor lib
   compile 'com.wellerv.ezalor:ezalor:0.1.0'
}
```
Then you should init the ezalor in your application.
```java
public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Ezalor.get().init(this);
    }
}
```
Given that it is just a tool for your application, I recommend that it be used for the debug version.

## Export Data
Ezalor now supports excel export via [EzalorTools][2].

## Known Issues
Before you initialize ezalor, the application must have sdcard write permission.

## Support
Any problem?

1. Learn more from [sample][3]
2. Submit issues
3. Contact me for help by [email][4]


  [1]: http://on8vjlgub.bkt.clouddn.com/ezalor%E5%8E%9F%E7%90%86%E5%9B%BE.png
  [2]: https://github.com/WellerV/EzalorTools
  [3]: https://github.com/WellerV/Ezalor/tree/master/sample
  [4]: huweigoodboy@126.com