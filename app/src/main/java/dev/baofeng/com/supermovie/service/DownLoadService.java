package dev.baofeng.com.supermovie.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;

import dev.baofeng.com.supermovie.db.TaskInfoData;
import dev.baofeng.com.supermovie.view.GlobalMsg;

/**
 * Created by huangyong on 2018/9/18.
 */

public class DownLoadService extends Service {

    //存储下载列表任务id和封装
    HashMap<String,TaskInfoData> taskMap = new HashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        initQuery();
    }

    private void initQuery() {

        Intent intent = new Intent();
        intent.setAction(GlobalMsg.ACTION);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastManager.sendBroadcast(intent);
    }


}
