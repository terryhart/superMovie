package dev.baofeng.com.supermovie.bt;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.xunlei.downloadlib.XLTaskHelper;

import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.domain.TaskInfo;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.onAddListener;

/**
 * 使用方式：
 *  DownloadInfo info = new DownloadInfo()//封装了下载文件的信息自己定义
 *  ComDownloadTask task = new ComDownloadTask()
 *  ThreadPollUtils.execute(task)
 *
 * Created by Huangyong on 2018/3/1.
 */

public class BTDownloadTask extends ThreadUtils.Task{
    private Context context;
    private TaskInfo  info;

    public BTDownloadTask(Context context, TaskInfo info) {
        this.context = context;
        this.info = info;
    }

    @Override
    protected void work() {
        //下载任务
        downloadApk(info);
    }
    public TaskInfo getInfo(){
        if (this.info!=null){
            return this.info;
        }
        return null;
    }
    private void downloadApk(TaskInfo info) {
        //开始下载并存入本地
        try {
            long taskId = 0;
            taskId = XLTaskHelper.instance(MyApp.appInstance()).addTorrentTask(info.getPath(), "/sdcard/", null);
            info.setTaskid(taskId+"");
            info.setIsWaiting(0);
            info.save();
            //发送广播，更新进度条，更新数据库
            Intent intent = new Intent();
            intent.putExtra(GlobalMsg.TASKID,taskId+"");
            intent.setAction(GlobalMsg.ACTION);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
            localBroadcastManager.sendBroadcast(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private onAddListener listener;
    public void setOnAddListener(onAddListener listener) {
        this.listener = listener;
    }

}
