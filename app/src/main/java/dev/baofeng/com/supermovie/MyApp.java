package dev.baofeng.com.supermovie;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.model.Params;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.youngfeng.snake.Snake;
import com.zchu.rxcache.RxCache;
import com.zchu.rxcache.diskconverter.SerializableDiskConverter;

import java.io.File;
import java.io.InputStream;

import byc.imagewatcher.ImageWatcher;
import byc.imagewatcher.ImageWatcherHelper;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.https.OkHttpUrlLoader;
import dev.baofeng.com.supermovie.utils.SPUtils;

/**
 * Created by oceanzhang on 2017/9/28.
 */

public class MyApp extends Application{

    public static MyApp instance = null;
    public SPUtils spUtils;
    private static RxCache rxCache;
    private ImageWatcherHelper iwHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //初始化SP
        spUtils = new SPUtils(this,"SuperMovie");

        initDownloadLib();

        Snake.init(this);
        //初始化缓存管理
        initCache();
        //让Glide能用HTTPS
        Glide.get(this).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ApiManager.getClientInstance()));

        //初始化友盟统计
        UMConfigure.init(this, Params.UMENG_KEY, "zmovie",  UMConfigure.DEVICE_TYPE_PHONE, "");
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub https://github.com/H07000223/FlycoSystemBar  https://github.com/codevscolor/MaterialPreference
                // TODO https://github.com/lufficc/StateLayout 多状态 https://github.com/tarek360/RichPath  https://github.com/Jaouan/Revealator
                // TODO https://github.com/didixyy/BilibiliSearchView
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }
            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);


    }


    private void initCache() {
        //支持Serializable、Json(GsonDiskConverter)
        rxCache = new RxCache.Builder()
                .appVersion(1)
                .diskDir(new File(getCacheDir().getPath() + File.separator + "data-cache"))
                .diskConverter(new SerializableDiskConverter())//支持Serializable、Json(GsonDiskConverter)
                .memoryMax(2 * 1024 * 1024)
                .diskMax(20 * 1024 * 1024)
                .build();
    }

    private void initDownloadLib() {
       TaskLibHelper.init(instance);
    }

    public static MyApp appInstance() {
        return instance;
    }
    public static RxCache getCacheInstance() {
        return rxCache;
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
