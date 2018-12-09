package com.huangyong.downloadlib.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.db.HistoryDao;
import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.domain.HistoryInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.presenter.DownLoadPresenter;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.huangyong.downloadlib.utils.FileUtils;
import com.huangyong.downloadlib.utils.NetUtil;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class DownLoadService extends Service implements ITask {

    private static DownLoadPresenter presenter;
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
        presenter = DownLoadPresenter.getInstance(this,this);
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

                //查询已完成数据库，获取最新数据
                TaskedDao taskedDao = TaskedDao.getInstance(getApplicationContext());

                //查询数据库，获取最新的数据
                TaskDao taskDao = TaskDao.getInstance(getApplicationContext());

                List<DowningTaskInfo> taskInfos = taskDao.queryAll();

                if (taskInfos!=null&&taskInfos.size()>0){
                    for (int i = 0; i < taskInfos.size(); i++) {
                        String taskId = taskInfos.get(i).getTaskId();
                        XLTaskInfo taskInfo = XLTaskHelper.instance().getTaskInfo(Long.parseLong(taskId));
                        taskInfos.get(i).setTotalSize(String.valueOf(taskInfo.mFileSize));
                        taskInfos.get(i).setStatu(taskInfo.mTaskStatus);
                        taskInfos.get(i).setReceiveSize(String.valueOf(taskInfo.mDownloadSize));
                        taskInfos.get(i).setSpeed(FileUtils.convertFileSize(taskInfo.mDownloadSpeed));

                        Log.e("sdkjgsdlsldlldd",taskInfo.mDownloadSpeed+"--**--"+taskInfos.get(i).getTaskId()+"----"+taskInfos.get(i).getTitle());
                        if (taskInfo.mDownloadSize!=0&&taskInfo.mFileSize!=0&&taskInfo.mDownloadSize== Long.parseLong(taskInfos.get(i).getTotalSize())){
                            //添加到数据库
                            synchronized (DownLoadService.class){
                                //文件下载完成，此数据在下一秒移动到已完成数据库。
                                DoneTaskInfo task = new DoneTaskInfo();
                                task.setPostImgUrl(taskInfos.get(i).getPostImgUrl());
                                task.setTaskUrl(taskInfos.get(i).getTaskUrl());
                                task.setReceiveSize(String.valueOf(taskInfo.mFileSize));
                                task.setTotalSize(String.valueOf(taskInfo.mDownloadSize));
                                task.setLocalPath(taskInfos.get(i).getLocalPath());
                                task.setFilePath(taskInfos.get(i).getFilePath());
                                task.setTitle(taskInfos.get(i).getTitle());
                                task.setTaskId(taskInfos.get(i).getTaskId());
                                task.setUrlMd5(taskInfos.get(i).getUrlMd5());
                                taskedDao.add(task);

                                //然后删除下载中的记录
                                taskDao.delete(taskInfos.get(i).getId());

                                //提示下载完成
                                Intent intent = new Intent();
                                intent.putExtra(Params.TASK_ID_KEY,taskInfos.get(i).getId());
                                intent.putExtra(Params.TASK_TITLE_KEY,taskInfos.get(i).getTitle());
                                BroadCastUtils.sendIntentBroadCask(getApplicationContext(),intent,Params.TASK_COMMPLETE);

                                //提示下载完成
                                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                r.play();
                            }
                        }else {
                            taskDao.update(taskInfos.get(i));
                        }
                    }
                }
                BroadCastUtils.sendIntentBroadCask(getApplicationContext(),new Intent(),Params.UPDATE_PROGERSS);
            }
        };

        subscribe = Observable.interval(0, 2, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).subscribe(subscriber);
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
        intentFilter.addAction(Params.TASK_START);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(Params.TASK_COMMPLETE);
        intentFilter.addAction(Params.HISTORY_SAVE);
        registerReceiver(taskReceiver,intentFilter);
    }
    BroadcastReceiver taskReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (Params.TASK_START.equals(intent.getAction())){

                String taskUrl = intent.getStringExtra(Params.TASK_URL_KEY);
                String taskPath = intent.getStringExtra(Params.LOCAL_PATH_KEY);
                String taskPoster = intent.getStringExtra(Params.POST_IMG_KEY);
                String urlMd5 = intent.getStringExtra(Params.URL_MD5_KEY);
                Boolean taskFrom = intent.getBooleanExtra(Params.IS_TASK_NEW,true);

                Log.e("dlslsls",taskPoster);
                DowningTaskInfo downTaskInfo = new DowningTaskInfo();

                downTaskInfo.setLocalPath(taskPath);
                downTaskInfo.setPostImgUrl(taskPoster);
                downTaskInfo.setTaskUrl(taskUrl);
                downTaskInfo.setUrlMd5(urlMd5);
                downTaskInfo.setReceiveSize("0");
                downTaskInfo.setTaskFrom(taskFrom);
                //新任务or 重启的任务，区别是，新任务会在数据库插入记录，重启的任务不插入新记录，而是更新已有的记录
                if (taskFrom){
                    if (presenter!=null){
                        presenter.addTask(downTaskInfo);
                    }
                }else {
                    if (presenter!=null){
                        presenter.restartNormalTask(downTaskInfo);
                    }
                }

            }

            if (Params.TASK_COMMPLETE.equals(intent.getAction())){
                String title = intent.getStringExtra(Params.TASK_TITLE_KEY);
                Toast.makeText(getApplicationContext(), title+"\n下载完成", Toast.LENGTH_SHORT).show();

            }
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                int netWorkState = NetUtil.getNetWorkState(context);
                // 接口回调传过去状态的类型
                switch (netWorkState){
                    case NetUtil.NETWORK_NONE:
                        Toast.makeText(context, "当前网络连接异常，可能无法下载影片", Toast.LENGTH_LONG).show();
                        break;
                    case NetUtil.NETWORK_MOBILE:
                        Toast.makeText(context, "当前网络为移动网络，已停止所有下载任务\n如仍然需下载,可手动启动任务", Toast.LENGTH_LONG).show();
                        //不允许4G时下载
                        //获取下载列表，遍历并停止下载任务
                        TaskDao taskDao = TaskDao.getInstance(getApplicationContext());
                        List<DowningTaskInfo> downingTaskInfos = taskDao.queryAll();
                        if (downingTaskInfos!=null&&downingTaskInfos.size()>0){
                            for (int i = 0; i < downingTaskInfos.size(); i++) {
                                XLTaskHelper.instance().stopTask(Long.parseLong(downingTaskInfos.get(i).getTaskId()));
                            }
                        }
                        break;
                    case NetUtil.NETWORK_WIFI:
                        break;
                    default:
                        break;
                }
            }
        }
    };

    @Override
    public void repeatAdd(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
