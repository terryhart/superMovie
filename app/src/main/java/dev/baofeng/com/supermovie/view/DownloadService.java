package dev.baofeng.com.supermovie.view;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.xunlei.downloadlib.XLTaskHelper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import dev.baofeng.com.supermovie.bt.ComDownloadTask;
import dev.baofeng.com.supermovie.bt.ThreadUtils;
import dev.baofeng.com.supermovie.domain.RunTaskInfo;

/**
 * Created by huangyong on 2018/2/12.
 */

public class DownloadService extends Service {

    private Timer timer;
    private TimerTask task;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void stopTheTask(String taskid) {
        XLTaskHelper.instance(this).stopTask(Long.parseLong(taskid));
    }

    public void reStartTheTask(String path) {
        try {
            ComDownloadTask task = new ComDownloadTask(this,path);
            GlobalMsg.service.addTask(task);
        }catch (Exception e){

        }

    }

    public void toggleTask(String path, String taskid) {

    }


    public class LocalBinder extends Binder {
        public DownloadService getService() {
            // Return this instance of LocalService so clients can call public methods
            return DownloadService.this;
        }
    }
    public void addTask(ComDownloadTask task){
        task.setOnAddListener(taskId -> {
            if (listener!=null){
                listener.onAddSuccess(taskId);
            }
        });

        ThreadUtils.execute(task);
    }
    private onAddListener listener;
    public void setAddListener(onAddListener listener){
        this.listener = listener;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
