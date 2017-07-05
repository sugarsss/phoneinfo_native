package com.niu.user.entrty;

/**
 * Created by niu on 2017/7/1.
 *
 * 本地安装的app信息
 */

public class NativeAppInfoBean {

    //应用名
    private String appName;
    //应用包名
    private String packageName;
    //是否是系统应用
    private boolean isSystemApp;
    //初次安装时间
    private String firstInstallTime;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    public String getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(String firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    @Override
    public String toString() {
        return "NativeAppInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", isSystemApp=" + isSystemApp +
                ", firstInstallTime='" + firstInstallTime + '\'' +
                '}';
    }
}
