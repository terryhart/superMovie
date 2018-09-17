package dev.baofeng.com.supermovie.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by huangyong on 2018/9/16.
 */

public class DownLoadTaskService extends IntentService {
    private static final String TAG = "MyIntentService";



    public DownLoadTaskService() {
        super("DownLoadTaskService");
        Log.i(TAG, "构造函数服务线程id:" + Thread.currentThread(). getId());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ");
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // 打印当前线程的id
        Log.i(TAG, "服务线程id:" + Thread.currentThread(). getId());
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
