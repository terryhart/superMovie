package com.ghost.thunder.demo;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.xunlei.downloadlib.XLTaskHelper;

/**
 * Created by oceanzhang on 2017/9/28.
 */

public class MyApp extends Application{

    public static MyApp instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        XLTaskHelper.init(getApplicationContext());
    }

    public static MyApp appInstance() {
        return instance;
    }
    @Override
    public String getPackageName() {
        if(Log.getStackTraceString(new Throwable()).contains("com.xunlei.downloadlib")) {
            return "com.xunlei.downloadprovider";
        }
        return super.getPackageName();
    }
    @Override
    public PackageManager getPackageManager() {
        if(Log.getStackTraceString(new Throwable()).contains("com.xunlei.downloadlib")) {
            return new DelegateApplicationPackageManager(super.getPackageManager());
        }
        return super.getPackageManager();
    }
}
