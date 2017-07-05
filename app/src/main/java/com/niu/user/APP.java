package com.niu.user;

import android.app.Application;

/**
 * Created by niu on 2017/7/1.
 */

public class APP extends Application {
    private static APP application;




    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

    }

    public static APP getContext() {
        return application;
    }
}
