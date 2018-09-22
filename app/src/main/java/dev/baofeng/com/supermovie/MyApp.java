package dev.baofeng.com.supermovie;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

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
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        spUtils = new SPUtils(this,"SuperMovie");//初始化SP
        //让Glide能用HTTPS
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ApiManager.getClientInstance()));
    }

    public static MyApp appInstance() {
        return instance;
    }

    public Handler getHandler() {
        Looper.prepare();
        if (handler==null){
            handler = new Handler();
        }
        return handler;
    }

    public static Context getContext() {
        return appInstance();
    }
}
