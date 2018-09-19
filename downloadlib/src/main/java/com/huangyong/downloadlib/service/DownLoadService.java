package com.huangyong.downloadlib.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.huangyong.downloadlib.domain.DownTaskInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.presenter.DownLoadPresenter;

public class DownLoadService extends Service implements ITask {

    private DownLoadPresenter presenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initReceiver();
        presenter = new DownLoadPresenter(this,this);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Params.NetWorkChangeAction);
        intentFilter.addAction(Params.TASK_DELETE);
        intentFilter.addAction(Params.TASK_PAUSE);
        intentFilter.addAction(Params.TASK_START);
        registerReceiver(taskReceiver,intentFilter);
    }
    BroadcastReceiver taskReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Params.TASK_START.equals(intent.getAction())){
                DownTaskInfo downTaskInfo = new DownTaskInfo();

                downTaskInfo.setId(intent.getIntExtra(Params.TASK_ID,0));
                downTaskInfo.setLocalPath(intent.getStringExtra(Params.LOCAL_PATH_KEY));
                downTaskInfo.setPostImgUrl(intent.getStringExtra(Params.POST_IMG_KEY));
                downTaskInfo.setTaskUrl(intent.getStringExtra(Params.TASK_URL_KEY));
                if (presenter!=null){
                    presenter.addTask(downTaskInfo);
                }
            }
            if (Params.TASK_PAUSE.equals(intent.getAction())){

            }
            if (Params.TASK_DELETE.equals(intent.getAction())){

            }
        }
    };
    @Override
    public void TaskStart(String taskId, String url) {

    }

    @Override
    public void TaskPause(String taskId) {

    }

    @Override
    public void restartTask(String taskId) {

    }

    @Override
    public void deleteTask(String taskId) {

    }
}
