package com.huangyong.downloadlib.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

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
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class DownLoadService extends Service implements ITask {

    private DownLoadPresenter presenter;
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

                        Log.e("sdkjgsdlsldlldd",taskInfo.mFileSize+"--**--"+taskInfo.mTaskStatus);
                        if (taskInfo.mDownloadSize!=0&&taskInfo.mFileSize!=0&&taskInfo.mDownloadSize== Long.parseLong(taskInfos.get(i).getTotalSize())){
                            //文件下载完成，此数据在下一秒移动到已完成数据库。
                            DoneTaskInfo task = new DoneTaskInfo();
                            task.setPostImgUrl(taskInfos.get(i).getPostImgUrl());
                            task.setReceiveSize(String.valueOf(taskInfo.mFileSize));
                            task.setTotalSize(String.valueOf(taskInfo.mDownloadSize));
                            task.setLocalPath(taskInfos.get(i).getLocalPath());
                            task.setTitle(taskInfos.get(i).getTitle());
                            task.setTaskId(taskInfos.get(i).getTaskId());
                            task.setUrlMd5(taskInfos.get(i).getUrlMd5());

                            //添加到数据库
                            taskedDao.add(task);

                            //然后删除下载中的记录
                             taskDao.delete(taskInfos.get(i).getId());

                            //提示下载完成
                            Intent intent = new Intent();
                            intent.putExtra(Params.TASK_ID_KEY,taskInfos.get(i).getId());
                            intent.putExtra(Params.TASK_TITLE_KEY,taskInfos.get(i).getTitle());
                            BroadCastUtils.sendIntentBroadCask(getApplicationContext(),intent,Params.TASK_COMMPLETE);
                        }else {
                            taskDao.update(taskInfos.get(i));
                        }
                    }
                }
                BroadCastUtils.sendIntentBroadCask(getApplicationContext(),new Intent(),Params.UPDATE_PROGERSS);

               /* if (taskId.size()>0){
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
                          *//*  try {
                                XLTaskHelper.instance().addTorrentTask(path,Params.DEFAULT_PATH,null);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*//*
                                }
                            }
                        }



                    }
                }*/



            }
        };
        subscribe = Observable.interval(0, 1, TimeUnit.SECONDS).subscribe(subscriber);
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
                if (presenter!=null){
                    presenter.addTask(downTaskInfo);
                }
            }
            if (Params.TASK_PAUSE.equals(intent.getAction())){
                if (presenter!=null){
                    presenter.pauseTask("");
                }
            }
            if (Params.TASK_DELETE.equals(intent.getAction())){

            }

            if (Params.TASK_COMMPLETE.equals(intent.getAction())){
                String title = intent.getStringExtra(Params.TASK_TITLE_KEY);
                Toast.makeText(getApplicationContext(), title+"\n下载完成", Toast.LENGTH_SHORT).show();
            }
            if (Params.HISTORY_SAVE.equals(intent.getAction())){


                //播放退出，保存观看信息
                String title =intent.getStringExtra(Params.TASK_TITLE_KEY);
                String path = intent.getStringExtra(Params.LOCAL_PATH_KEY);
                String progress = intent.getStringExtra(Params.MOVIE_PROGRESS);
                String urlMd5 = intent.getStringExtra(Params.URL_MD5_KEY);
                Log.e("baocunjilu","baocunjilu"+urlMd5);
                String posterUrl = intent.getStringExtra(Params.POST_IMG_KEY);
                HistoryDao dao = HistoryDao.getInstance(context);
                List<HistoryInfo> historyInfos = dao.queryAll();
                if (historyInfos!=null&&historyInfos.size()>0){
                    //更新本地数据
                    List<HistoryInfo> urlMd5Info = dao.queryForFeilds("urlMd5", urlMd5);
                    if (urlMd5Info!=null&&urlMd5Info.size()>0){
                        //更新数据
                        urlMd5Info.get(0).setProgress(progress);
                        dao.updata(urlMd5Info.get(0));
                        Log.e("baocunjilu","更新数据baocunjilu");
                    }else {
                        //插入新数据
                        HistoryInfo info = new HistoryInfo();
                        info.setUrlMd5(urlMd5);
                        info.setTitle(title);
                        info.setPostImgUrl(posterUrl);
                        info.setProgress(progress);
                        info.setLocalPath(path);
                        dao.add(info);
                        Log.e("baocunjilu","插入新电影数据baocunjilu");
                    }
                }else {
                    //插入新数据
                    HistoryInfo info = new HistoryInfo();
                    info.setUrlMd5(urlMd5);
                    info.setTitle(title);
                    info.setPostImgUrl(posterUrl);
                    info.setProgress(progress);
                    info.setLocalPath(path);
                    dao.add(info);
                    Log.e("baocunjilu","baocunjil插入新数据u");
                }

            }

        }
    };

    @Override
    public void repeatAdd(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
