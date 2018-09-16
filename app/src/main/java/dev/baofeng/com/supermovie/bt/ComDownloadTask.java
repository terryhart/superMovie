package dev.baofeng.com.supermovie.bt;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.xunlei.downloadlib.XLTaskHelper;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.domain.RunTaskInfo;
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

public class ComDownloadTask extends ThreadUtils.Task{
    private Context context;
    private String  url;
    public ComDownloadTask(Context context, String url) {
        this.context = context;
        this.url = url;

    }

    @Override
    protected void work() {
        //下载任务
        downloadApk(url);
    }
    //当从队列中取出一个任务开始下载时，将状态改为正在下载，这里其实肯定是正在下载
    private void downloadApk(String url) {

        //开始下载并存入本地
        try {
            long taskId = 0;
            if (url.startsWith("magnet:?")){
                taskId = XLTaskHelper.instance().addMagnetTask(url, "/sdcard/", null);
            }else if (url.endsWith(".torrent")){
                taskId = XLTaskHelper.instance().addTorrentTask(url, "/sdcard/", null);
            }else {
                taskId = XLTaskHelper.instance().addThunderTask(url, "/sdcard/", null);
            }
            //根据url判断下载任务是否唯一，同时也根据url找到唯一的那个下载任务。
            List<TaskInfo> taskInfos = DataSupport.where("path=?", url).find(TaskInfo.class);
            //严格来说，任务仅有一个，所以taskInfos必然只有一个元素
            taskInfos.get(0).setTaskid(taskId+"");//update这条记录
            taskInfos.get(0).setIsWaiting(0);
            taskInfos.get(0).save();//将taskId更新到数据库这条记录里。之前的记录里taskId为空，此时赋值，数据才完整
            List<TaskInfo> all = DataSupport.findAll(TaskInfo.class);

            //执行到这里，说明数据库中肯定有一条，所以应当取数据库中的对象，不要用参数传递来的对象，容易出错
            if (listener!=null){
                listener.onAddSuccess(taskId+"");
            }
            Log.d("下载任务基数：","当前下载任务添加，总数量为"+all.size());
            //发送广播，通知下载任务列表更新数据，注意，任务列表数据也由数据库获取，保证源唯一，避免数据不统一。
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
