package dev.baofeng.com.supermovie;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.model.Params;
import com.umeng.commonsdk.UMConfigure;

import java.io.InputStream;

import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.https.OkHttpUrlLoader;
import dev.baofeng.com.supermovie.utils.SPUtils;

/**
 * Created by oceanzhang on 2017/9/28.
 */

public class MyApp extends Application{

    public static MyApp instance = null;
    public SPUtils spUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化SP
        spUtils = new SPUtils(this,"SuperMovie");

        initDownloadLib();

        //让Glide能用HTTPS
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ApiManager.getClientInstance()));

        //初始化友盟统计
        UMConfigure.init(this, Params.UMENG_KEY, "zmovie",  UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    private void initDownloadLib() {
        TaskLibHelper.init(this);
    }

    public static MyApp appInstance() {
        return instance;
    }


    public static Context getContext() {
        return appInstance();
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
