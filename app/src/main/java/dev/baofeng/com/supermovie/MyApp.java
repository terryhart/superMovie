package dev.baofeng.com.supermovie;

import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.xunlei.downloadlib.XLTaskHelper;

import java.io.InputStream;

import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.https.OkHttpUrlLoader;

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

        //让Glide能用HTTPS
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ApiManager.getClientInstance()));
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
