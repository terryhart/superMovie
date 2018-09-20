package dev.baofeng.com.supermovie;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.xunlei.downloadlib.XLTaskHelper;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;

import java.io.InputStream;
import java.util.LinkedList;

import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.https.OkHttpUrlLoader;
import dev.baofeng.com.supermovie.utils.SPUtils;
import dev.baofeng.com.supermovie.view.GlobalMsg;

/**
 * Created by oceanzhang on 2017/9/28.
 */

public class MyApp extends LitePalApplication {

    public static MyApp instance = null;
    public SPUtils spUtils;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Connector.getDatabase();//创建数据库
        XLTaskHelper.init(getApplicationContext());
        spUtils = new SPUtils(this,"SuperMovie");//初始化SP
        GlobalMsg.downQueue =new LinkedList<String>();//初始化全局未下载队列，存本地数据库
        GlobalMsg.doneQueue =new LinkedList<String>();//初始化全局已下载队列，存本地数据库
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

    public Handler getHandler() {
        Looper.prepare();
        if (handler==null){
            handler = new Handler();
        }
        return handler;
    }
}
