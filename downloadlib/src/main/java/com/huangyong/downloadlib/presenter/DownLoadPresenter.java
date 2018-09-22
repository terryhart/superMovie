package com.huangyong.downloadlib.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.xunlei.downloadlib.XLDownloadManager;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;

import java.util.ArrayList;
import java.util.List;

public class DownLoadPresenter implements IPresenter {

    private static String taskId;
    private  ITask iTask;
    private Context context;


    public DownLoadPresenter(Context context,ITask iTask) {
        this.context = context;
        this.iTask = iTask;
    }


    @Override
    public void addTask(DowningTaskInfo info) {
        //查询下已完成列表，是否有记录，有则不下载
        TaskedDao taskedDao = TaskedDao.getInstance(context);
        List<DoneTaskInfo> doneTaskInfos = taskedDao.queryAll();
        if (doneTaskInfos!=null&&doneTaskInfos.size()>0){
            for (int i = 0; i < doneTaskInfos.size(); i++) {
                if (doneTaskInfos.get(i).getUrlMd5().equals(info.getUrlMd5())){
                    if (iTask!=null){
                        iTask.repeatAdd("该影片已下载完成了，去中心看看吧");
                        Log.e("repeatAdd","该影片已下载完成了");
                        return;
                    }
                }
            }
        }

        //TODO 获取到taskId,localPath，存入数据库,发送广播到任务列表，查询数据库，查询并显示更新进度
        String link = info.getTaskUrl();
        String path = info.getLocalPath();
        //获取文件名
        String taskName = XLTaskHelper.instance().getFileName(link);
        info.setTitle(taskName);
        //数据库存一份，先查询数据库是否已有记录，没有则添加，有则不添加，0为停止状态，1正在进行和4为正在暂停，连接中的状态
        TaskDao taskDao = TaskDao.getInstance(context);
        List<DowningTaskInfo> taskInfos = taskDao.queryAll();
        if (taskInfos!=null&&taskInfos.size()>0){
            for (int i = 0; i < taskInfos.size(); i++) {
                if (taskInfos.get(i).getUrlMd5().equals(info.getUrlMd5())){
                    //已在任务列表，查看状态，如果是正在下载，不添加
                    if (taskInfos.get(i).getStatu()==1||taskInfos.get(i).getStatu()==4&&info.getTaskFrom()){
                        iTask.repeatAdd("该影片已在下载列表中");
                        return;
                    }
                }
            }
        }

        if (link.contains("magnet") || XLTaskHelper.instance().getFileName(link).endsWith("torrent")) {
            if (link.startsWith("magnet")) {
                taskId = addMagnetTask(link, path, null);
            } else {
                taskId = addMagnetTask(getRealUrl(link), path, null);
            }
        } else {
            taskId = addThunderTask(link, path, null, context);
        }

        //这个是用来重新启动任务的，更新数据库，而不插入新的记录，一种取巧的重启任务的策略
        if (taskInfos!=null&&taskInfos.size()>0){
            for (int i = 0; i < taskInfos.size(); i++) {
                if (taskInfos.get(i).getUrlMd5().equals(info.getUrlMd5())){
                    //已在任务列表，查看状态，如果是正在下载，不添加
                    //更新表数据,如果是暂停，或者停止
                    if (taskInfos.get(i).getStatu()==0||taskInfos.get(i).getStatu()==4){
                        taskInfos.get(i).setTaskId(taskId);
                        taskDao.update(taskInfos.get(i));
                        Intent intent = new Intent();
                        intent.putExtra(Params.TASK_ID,taskId);
                        BroadCastUtils.sendIntentBroadCask(context,intent, Params.UPDATE_PROGERSS);
                        return;
                    }
                }
            }
        }

        info.setTaskId(taskId);
        //存入数据库
        taskDao.add(info);
        //广播通知更新
        Intent intent = new Intent();
        intent.putExtra(Params.TASK_ID,taskId);
        BroadCastUtils.sendIntentBroadCask(context,intent, Params.UPDATE_PROGERSS);
    }

    @Override
    public void pauseTask(String url) {
        if (!TextUtils.isEmpty(taskId)){
            XLTaskHelper.instance().stopTask(Long.parseLong(taskId));
        }
    }

    @Override
    public void restartTask(String url) {
    }


    private static String addThunderTask(String url, String savePath, String fileName, Context context) {
        try {
            taskId = String.valueOf(XLTaskHelper.instance().addThunderTask(url, savePath, fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String localUrl = XLTaskHelper.instance().getLoclUrl(savePath +
                XLTaskHelper.instance().getFileName(url));
        return taskId;
    }

    private static String addMagnetTask(String url, String savePath, String fileName) {
        try {
            taskId = String.valueOf(XLTaskHelper.instance().addMagnetTask(url, savePath, fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskId;
    }

    public  String addTorrentTask(String torrentPath,String savePath,List index){
        List list = new ArrayList();

        TorrentInfo torrentInfo = XLTaskHelper.instance().getTorrentInfo(torrentPath);
        for (int i = 0; i < torrentInfo.mSubFileInfo.length; i++) {
            list.add(1);
        }

        try {
             taskId = String.valueOf(XLTaskHelper.instance().addTorrentTask(torrentPath,savePath, index));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskId;
    }


    public void  restartTasks(String taskId) {
        XLTaskHelper.instance().startTask(Long.parseLong(taskId));
    }

    /**
     * 迅雷thunder://地址与普通url地址转换
     * 其实迅雷的thunder://地址就是将普通url地址加前缀‘AA’、后缀‘ZZ’，再base64编码后得到的字符串
     *
     * @param url
     * @return
     */
    public static String getRealUrl(String url) {
        if (url.startsWith("thunder://")) url = XLDownloadManager.getInstance().parserThunderUrl(url);
        return url;
    }


}
