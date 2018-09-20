package com.huangyong.downloadlib.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.huangyong.downloadlib.domain.DownTaskInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.presenter.DownLoadPresenter;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class DownLoadService extends Service implements ITask {

    private DownLoadPresenter presenter;
    private LinkedList taskId;
    private Subscription subscribe;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initReceiver();
        initQuery();
        presenter = new DownLoadPresenter(this,this);
    }

    /**
     * 开启一个轮询，不断获取进度，并发送广播传送出去
     */
    private void initQuery() {
        //TODO 查询数据库，获取进度等信息



        Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Long aLong) {
                System.out.println("Next:" + aLong.toString());

                if (taskId.size()>0){
                    for (int i = 0; i < taskId.size(); i++) {

                        Log.e("ksdjglkdsl","查询数据库"+taskId.get(i).toString());

                        String tastid = taskId.get(i).toString();
                        //TODO 查询数据库，获取进度等信息
                        if (!TextUtils.isEmpty(taskId.get(i).toString())) {
                            XLTaskInfo taskInfo = XLTaskHelper.instance().getTaskInfo(Long.parseLong(tastid));
                            Log.e("正在下载", taskInfo.mFileSize + "--" + taskInfo.mDownloadSize);

                            if (taskInfo.mFileSize==taskInfo.mDownloadSize){
                                String name = XLTaskHelper.instance().getFileName("magnet:?xt=urn:btih:4KTOIW3OPAH4BLJ6COVWNFMVCLMWK6HU&dn=%e7%9b%ae%e5%87%bb%e8%80%85%2e720p%2eBD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e66ys%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337");
                                if (name.endsWith("torrent")){
                                    String path = "/sdcard/"+name;
                                    TorrentInfo torrentInfo = XLTaskHelper.instance().getTorrentInfo(path);
                                    Log.e("torrentInfo",torrentInfo.mIsMultiFiles+"--"+torrentInfo.mFileCount+"--"+torrentInfo.mSubFileInfo.length);
                                    List list = new ArrayList();
                                    list.add(torrentInfo.mSubFileInfo.length);

                                    for (int j = 0; i <torrentInfo.mSubFileInfo.length ; j++) {
                                        Log.e("torrentInfo--",torrentInfo.mSubFileInfo[j].mFileName);
                                        Log.e("torrentInfo--",torrentInfo.mSubFileInfo[j].mFileSize+"");
                                        Log.e("torrentInfo--",torrentInfo.mSubFileInfo[j].mFileIndex+"");
//                                list.add(torrentInfo.mSubFileInfo[i].mFileIndex);
                                    }
                                    //TODO 对话框提示，下载其中哪个文件，list保存其中的index
                                    presenter.addTorrentTask(path,Params.DEFAULT_PATH,list);
//                            XLTaskHelper.instance().addTorrentTask(path,Params.DEFAULT_PATH);
                          /*  try {
                                XLTaskHelper.instance().addTorrentTask(path,Params.DEFAULT_PATH,null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/
                                }
                            }
                        }



                    }
                }



            }
        };
        subscribe = Observable.interval(0, 2, TimeUnit.SECONDS).subscribe(subscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscribe.unsubscribe();
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
                downTaskInfo.setLocalPath(intent.getStringExtra(Params.DEFAULT_PATH));
                downTaskInfo.setPostImgUrl(intent.getStringExtra(Params.POST_IMG_KEY));
                downTaskInfo.setTaskUrl(intent.getStringExtra(Params.TASK_URL_KEY));

                if (presenter!=null){
                    presenter.addTask(downTaskInfo);
                }
            }
            if (Params.TASK_PAUSE.equals(intent.getAction())){
                if (presenter!=null){
                    presenter.pauseTask(intent.getStringExtra(Params.TASK_URL_KEY));
                }
            }
            if (Params.TASK_DELETE.equals(intent.getAction())){

            }
            if (Params.UPDATE_PROGERSS.equals(intent.getAction())){
                initQuery();
            }

        }
    };

    @Override
    public void TaskStart(LinkedList<String> taskId, String url) {
        this.taskId = taskId;
    }
}
