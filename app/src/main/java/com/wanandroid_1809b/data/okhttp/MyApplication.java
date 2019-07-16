package com.wanandroid_1809b.data.okhttp;

import android.app.Application;

public class MyApplication extends Application {
    public static Application mApplicationContext;


    public static boolean mIsLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext=this;
    }
}
