package com.huangyong.downloadlib.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.xunlei.downloadlib.XLDownloadManager;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;

import java.util.ArrayList;
import java.util.LinkedList;
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


        //TODO 获取到taskId,localPath，存入数据库,发送广播到任务列表，查询数据库，查询并显示更新进度
        String link = info.getTaskUrl();
        String path = Params.DEFAULT_PATH;
        //获取文件名
        String taskName = XLTaskHelper.instance().getFileName(link);

        info.setTitle(taskName);

        //数据库存一份，先查询数据库是否已有记录，没有则添加，有则不添加
        TaskDao taskDao = TaskDao.getInstance(context);
        List<DowningTaskInfo> taskInfos = taskDao.queryAll();
        if (taskInfos!=null&&taskInfos.size()>0){
            for (int i = 0; i < taskInfos.size(); i++) {
                if (taskInfos.get(i).getUrlMd5().equals(info.getUrlMd5())){
                    //已在任务列表，不继续添加，直接返回
                    if (iTask!=null){
                        iTask.repeatAdd();
                    }
                    String taskId = taskInfos.get(i).getTaskId();
                    XLTaskHelper.instance().startTask(Long.parseLong(taskId));
                    return;
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


        info.setTaskId(taskId);
        //存入数据库
        taskDao.add(info);

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
