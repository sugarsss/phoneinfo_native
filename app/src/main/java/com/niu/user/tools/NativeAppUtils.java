package com.niu.user.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.niu.user.entrty.NativeAppInfoBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by niu on 2017/7/1.
 * 本机安装的App列表
 */

public class NativeAppUtils {

    /**
     * 返回系统中所有应用信息列表
     **/
    public static List<NativeAppInfoBean> getAllApps(Context context, long lastPostTime) throws Throwable {
        PackageManager packageManager = context.getPackageManager();
        List<NativeAppInfoBean> list = new ArrayList<>();
        NativeAppInfoBean myAppInfo;
        //获取到所有安装了的应用程序的信息，包括那些卸载了的，但没有清除数据的应用程序
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo info : packageInfos) {
            myAppInfo = new NativeAppInfoBean();
            String packageName = info.packageName;
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //初次安装时间
            myAppInfo.setFirstInstallTime(sfd.format(new Date(info.firstInstallTime)));
            ApplicationInfo appInfo = info.applicationInfo;
            //程序名
            String appName = appInfo.loadLabel(packageManager).toString();
            myAppInfo.setPackageName(packageName);
            myAppInfo.setAppName(appName);
            if (filterApp(appInfo)) {
                myAppInfo.setSystemApp(false);
            } else {
                myAppInfo.setSystemApp(true);
            }
            if (lastPostTime == 0) {
                list.add(myAppInfo);
            } else if (info.firstInstallTime > lastPostTime) {
                list.add(myAppInfo);
            }
        }
        return list;
    }

    /**
     * 判断是不是系统应用
     */
    public static boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}
