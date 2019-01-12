package com.huangyong.downloadlib.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.presenter.DownLoadPresenter;
import com.huangyong.downloadlib.room.AppDatabaseManager;
import com.huangyong.downloadlib.room.DowningTaskDao;
import com.huangyong.downloadlib.room.data.DoneTaskInfo;
import com.huangyong.downloadlib.room.data.DowningTaskInfo;
import com.huangyong.downloadlib.utils.NetUtil;
import com.xunlei.downloadlib.XLTaskHelper;

import java.util.List;


public class DownLoadService extends Service implements ITask {

    private static DownLoadPresenter presenter;

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
        presenter = new DownLoadPresenter(this, this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
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
                        //presenter.restartNormalTask(downTaskInfo);
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
                        DowningTaskDao taskDao = AppDatabaseManager.getInstance(getApplicationContext()).donwingDao();
                        List<DowningTaskInfo> downingTaskInfos = taskDao.getAll();
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

    @Override
    public void updateIngTask(List<DowningTaskInfo> taskInfo) {
    }

    @Override
    public void updateDoneTask(List<DoneTaskInfo> taskInfo) {
    }
}
