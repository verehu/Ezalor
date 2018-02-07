# Ezalor

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/WellerV/Ezalor/blob/master/LICENSE.txt)
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![PRs Welcome](https://img.shields.io/badge/prs-welcome-brightgreen.svg)](http://makeapullrequest.com)
[![Code Climate](https://img.shields.io/codeclimate/issues/github/me-and/mdf.svg)](https://github.com/WellerV/Ezalor/issues)

Ezalor是Android平台上的io监控库，它支持记录所有的io操作。
您可以获得每个io操作的性能。
 
 ![Ezalor][1]
 
## 开始
将以下代码添加到build.gradle来添加依赖关系。 
```groovy
dependencies {
    //ezalor lib
   compile 'com.wellerv.ezalor:ezalor:0.1.0'
}
```
然后你需要application初始化Ezalor。
```java
public class SampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Ezalor.get().init(this);
    }
}
```
鉴于它只是您的应用程序的工具，我建议将其用于调试版本。

## 导出数据
Ezalor现在可以通过[EzalorTools][2]导出excel表格.

## 已知问题
在你初始化Ezalor之前,请确保应用拥有sdcard写入权限.

## 支持
有任何问题?

1. 了解[sample][3]
2. 提交issues
3. 通过[email]联系我[4]


  [1]: http://on8vjlgub.bkt.clouddn.com/ezalor%E5%8E%9F%E7%90%86%E5%9B%BE.png
  [2]: https://github.com/WellerV/EzalorTools
  [3]: https://github.com/WellerV/Ezalor/tree/master/sample
  [4]: huweigoodboy@126.com