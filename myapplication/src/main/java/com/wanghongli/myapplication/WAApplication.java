package com.wanghongli.myapplication;

import android.app.Application;

import com.com.wanghongli.myapplication.dao.DaoMaster;
import com.com.wanghongli.myapplication.dao.DaoSession;


public class WAApplication extends Application {

    public static Application mApplicationContext;
    private static DaoSession mDaoSession;


    public static boolean mIsLogin = false;


    @Override
    public void onCreate() {
        super.onCreate();
        initdb();
        mApplicationContext = this;
    }
    private void initdb() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "tab");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }
    public  static DaoSession getmDaoSession(){
        return  mDaoSession;
    }

}
