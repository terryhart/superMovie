package com.huangyong.downloadlib.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.huangyong.downloadlib.room.AppDatabaseManager;
import com.huangyong.downloadlib.room.DowningTaskDao;
import com.huangyong.downloadlib.room.DoneTaskDao;
import com.huangyong.downloadlib.room.data.DoneTaskInfo;
import com.huangyong.downloadlib.room.data.DowningTaskInfo;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.huangyong.downloadlib.utils.FileUtils;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;


/**
 * creator huangyong
 * createTime 2018/12/21 下午3:24
 * path com.huangyong.downloadlib.model
 * description:电影下载状态的viewmodle,数据不停的更新
 * 理论上，只需要根据下载id去更新数据即可，所以，数据库一开始就存了下载id，这样不需要开启服务去轮询了，只在页面显示时去更新数据
 */
public class MovieDownloadDataModel extends AndroidViewModel {

    private MutableLiveData<List<DowningTaskInfo>> mDownloadingTaskData = new MutableLiveData<>();

    private Subscription subscribe;

    public MovieDownloadDataModel(@NonNull final Application application) {
        super(application);
//调用下载sdk里的api获取下载进度，更新页面的同时，更新到数据库里，避免了在服务中轮询并存储数据库造成的io开销，页面退出时，将进度数据保存到数据库,避免频繁读写数据库
        getRealDataInfo(application);
    }

    private void getRealDataInfo(final Application application) {
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
                //查询正在下载的数据库，获取最新数据
                DowningTaskDao downingTaskDao = AppDatabaseManager.getInstance(getApplication()).donwingDao();
                List<DowningTaskInfo> downingTaskDaoAll = downingTaskDao.getAll();


                if (downingTaskDaoAll != null && downingTaskDaoAll.size() > 0) {
                    for (int i = 0; i < downingTaskDaoAll.size(); i++) {
                        String taskId = downingTaskDaoAll.get(i).getTaskId();
                        XLTaskInfo taskInfo = XLTaskHelper.instance().getTaskInfo(Long.parseLong(taskId));
                        downingTaskDaoAll.get(i).setTotalSize(String.valueOf(taskInfo.mFileSize));
                        downingTaskDaoAll.get(i).setStatu(taskInfo.mTaskStatus);
                        downingTaskDaoAll.get(i).setReceiveSize(String.valueOf(taskInfo.mDownloadSize));
                        downingTaskDaoAll.get(i).setSpeed(FileUtils.convertFileSize(taskInfo.mDownloadSpeed));

                        Log.e("sdkjgsdlsldlldd", taskInfo.mDownloadSpeed + "--**--" + downingTaskDaoAll.get(i).getTaskId() + "----" + downingTaskDaoAll.get(i).getTitle());
                        if (taskInfo.mDownloadSize != 0 && taskInfo.mFileSize != 0 && taskInfo.mFileSize == Long.parseLong(downingTaskDaoAll.get(i).getReceiveSize())) {
                            //添加到数据库
                            synchronized (MovieDownloadDataModel.class) {
                                //文件下载完成，此数据在下一秒移动到已完成数据库。
                                DoneTaskInfo task = new DoneTaskInfo();
                                task.setPostImgUrl(downingTaskDaoAll.get(i).getPostImgUrl());
                                task.setTaskUrl(downingTaskDaoAll.get(i).getTaskUrl());
                                task.setReceiveSize(String.valueOf(taskInfo.mFileSize));
                                task.setTotalSize(String.valueOf(taskInfo.mDownloadSize));
                                task.setLocalPath(downingTaskDaoAll.get(i).getLocalPath());
                                task.setFilePath(downingTaskDaoAll.get(i).getFilePath());
                                task.setTitle(downingTaskDaoAll.get(i).getTitle());
                                task.setTaskId(downingTaskDaoAll.get(i).getTaskId());
                                task.setUrlMd5(downingTaskDaoAll.get(i).getUrlMd5());


                                //查询已完成数据库，获取最新数据
                                DoneTaskDao doneTaskDao = AppDatabaseManager.getInstance(getApplication()).doneTaskDao();
                                doneTaskDao.insert(task);

                                //然后删除下载中的记录
                                downingTaskDao.delete(downingTaskDaoAll.get(i));

                                //提示下载完成
                                Intent intent = new Intent();
                                intent.putExtra(Params.TASK_ID_KEY, downingTaskDaoAll.get(i).getId());
                                intent.putExtra(Params.TASK_TITLE_KEY, downingTaskDaoAll.get(i).getTitle());
                                BroadCastUtils.sendIntentBroadCask(application, intent, Params.TASK_COMMPLETE);

                                //提示下载完成
                                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                Ringtone r = RingtoneManager.getRingtone(application, notification);
                                r.play();
                            }
                        } else {
                            //保存数据
                            downingTaskDao.update(downingTaskDaoAll.get(i));
                        }
                    }
                    //更新数据
                    setmTaskData(downingTaskDaoAll);
                } else if (downingTaskDaoAll != null && downingTaskDaoAll.size() == 0) {
                    //更新数据
                    setmTaskData(downingTaskDaoAll);
                }
                BroadCastUtils.sendIntentBroadCask(application, new Intent(), Params.UPDATE_PROGERSS);
            }
        };

        subscribe = Observable.interval(0, 2, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).subscribe(subscriber);
    }


    public LiveData<List<DowningTaskInfo>> getRealTimeTaskInfo() {
        return mDownloadingTaskData;
    }

    /**
     * 更新model数据
     *
     * @param downingTaskInfo
     */
    public void setmTaskData(List<DowningTaskInfo> downingTaskInfo) {
        mDownloadingTaskData.postValue(downingTaskInfo);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        subscribe.unsubscribe();
    }

}
