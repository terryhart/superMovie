package com.huangyong.downloadlib.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.huangyong.downloadlib.utils.FileUtils;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.xunlei.downloadlib.XLDownloadManager;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.TorrentInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DownLoadPresenter implements IPresenter {

    private static String taskId;
    private  ITask iTask;
    private Context context;
    private String newTaskId;


    private DownLoadPresenter(Context context,ITask iTask) {
        this.context = context;
        this.iTask = iTask;
    }

    private static volatile DownLoadPresenter downLoadPresenter;

    public static DownLoadPresenter getInstance(Context context,ITask iTask){
        if (downLoadPresenter==null){
            synchronized (DownLoadPresenter.class){
                if (downLoadPresenter==null){
                    downLoadPresenter = new DownLoadPresenter(context,iTask);
                }
            }
        }
        return downLoadPresenter;
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
        info.setFilePath(path+"/"+taskName);
        info.setTitle(taskName);
        //数据库存一份，先查询数据库是否已有记录，没有则添加，有则不添加，0为停止状态，1正在进行和4为正在暂停，连接中的状态
        TaskDao taskDao = TaskDao.getInstance(context);
        List<DowningTaskInfo> taskInfos = taskDao.queryAll();
        if (taskInfos!=null&&info.getUrlMd5()!=null&&taskInfos.size()>0){
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

//        //这个是用来重新启动任务的，更新数据库，而不插入新的记录，一种取巧的重启任务的策略
//        if (taskInfos!=null&&taskInfos.size()>0){
//            for (int i = 0; i < taskInfos.size(); i++) {
//                if (taskInfos.get(i).getUrlMd5().equals(info.getUrlMd5())){
//                    //已在任务列表，查看状态，如果是正在下载，不添加
//
//                    //更新表数据,如果是暂停，或者停止
//                    if (taskInfos.get(i).getStatu()==0||taskInfos.get(i).getStatu()==4){
//                        taskInfos.get(i).setTaskId(taskId);
//                        taskDao.update(taskInfos.get(i));
//                        Intent intent = new Intent();
//                        intent.putExtra(Params.TASK_ID,taskId);
//                        BroadCastUtils.sendIntentBroadCask(context,intent, Params.UPDATE_PROGERSS);
//                        return;
//                    }
//                }
//            }
//        }
        Log.e("testdownload","-----yes"+taskId);
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

    public  void addTorrentTask(DoneTaskInfo taskInfo, String torrentPath, String savePath, List index, TorrentInfo torrentInfo, boolean newTask){
        //index存储了需要下载的文件的索引，0，1，2，3，根据这个从种子文件中搜索需要下载的内容
        //列表只显示下载的标题就行了，暂时取第一个索引的文件名，后面跟文件数量即可
        //任务id是只有一个的，根据任务id来重启和删除任务
        //查询下已完成列表，是否有记录，有则不下载
        TaskedDao taskedDao = TaskedDao.getInstance(context);
        List<DoneTaskInfo> doneTaskInfos = taskedDao.queryAll();
        if (doneTaskInfos!=null&&doneTaskInfos.size()>0){
            for (int i = 0; i < doneTaskInfos.size(); i++) {
                if (doneTaskInfos.get(i).getTitle().equals(torrentInfo.mSubFileInfo[0].mFileName+"共"+index.size()+"个文件")){
                    if (iTask!=null){
                        iTask.repeatAdd("该影片已下载完成了，去中心看看吧");
                        Log.e("repeatAdd","该影片已下载完成了");
                        return;
                    }
                }
            }
        }
        //数据库存一份，先查询数据库是否已有记录，没有则添加，有则不添加，0为停止状态，1正在进行和4为正在暂停，连接中的状态
        TaskDao taskDao = TaskDao.getInstance(context);
        List<DowningTaskInfo> taskInfos = taskDao.queryAll();
        if (taskInfos!=null&&taskInfos.size()>0){
            for (int i = 0; i < taskInfos.size(); i++) {
                if (taskInfos.get(i).getTitle().equals(torrentInfo.mSubFileInfo[0].mFileName)){
                    //已在任务列表，查看状态，如果是正在下载，不添加
                    if (taskInfos.get(i).getStatu()==1||taskInfos.get(i).getStatu()==4&&newTask){
                        iTask.repeatAdd("该影片已在下载列表中");
                        return;
                    }
                }
            }
        }

        try {

            StringBuffer indexList= new StringBuffer();
            for (int i = 0; i < index.size(); i++) {
                indexList.append(","+index.get(i));
            }

            String taskIds = String.valueOf(XLTaskHelper.instance().addTorrentTask(torrentPath,savePath, index));

            DowningTaskInfo info = new DowningTaskInfo();

            info.setPostImgUrl(taskInfo.getPostImgUrl());
            info.setTaskId(taskIds);
            info.setTitle(torrentInfo.mSubFileInfo[0].mFileName+"共"+index.size()+"个文件");
            info.setPostImgUrl(taskInfo.getPostImgUrl());
            info.setLocalPath(taskInfo.getLocalPath());
            info.setTorrentPath(torrentPath);
            String urlMd5 = MD5Utils.stringToMD5(taskInfo.getTaskUrl());
            info.setUrlMd5(urlMd5);
            info.setTotalSize(torrentInfo.mSubFileInfo[0].mFileSize+"");
            info.setReceiveSize("0");
            info.setFilePath(savePath+"/"+torrentInfo.mSubFileInfo[0].mFileName);
            info.setIndex(indexList.toString());
            //存入数据库
            taskDao.add(info);
            //广播通知更新
            Intent intent = new Intent();
            intent.putExtra(Params.TASK_ID,taskIds);
            BroadCastUtils.sendIntentBroadCask(context,intent, Params.UPDATE_PROGERSS);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "下载出错，请重试", Toast.LENGTH_SHORT).show();
        }

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


    public void restartTorrent(DowningTaskInfo info) {
        //这个是用来重新启动任务的，更新数据库，而不插入新的记录，一种取巧的重启任务的策略
        try {
            String savePath = FileUtils.isExistDir(Params.DEFAULT_PATH);

            String[] split = info.getIndex().split(",");
            List<Integer> indexList = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                if (!TextUtils.isEmpty(split[i])){
                    indexList.add(Integer.valueOf(split[i]));
                }
            }

            String taskIds = String.valueOf(XLTaskHelper.instance().addTorrentTask(info.getTorrentPath(),savePath,indexList));

            TaskDao taskDao = TaskDao.getInstance(context);
            List<DowningTaskInfo> taskInfos = taskDao.queryAll();
            if (taskInfos!=null&&taskInfos.size()>0){
                for (int i = 0; i < taskInfos.size(); i++) {
                    if (taskInfos.get(i).getTitle().equals(info.getTitle())){
                        //已在任务列表，查看状态，如果是正在下载，不添加
                        //更新表数据,如果是暂停，或者停止
                        if (taskInfos.get(i).getStatu()==0||taskInfos.get(i).getStatu()==4){
                            taskInfos.get(i).setTaskId(taskIds);
                            taskDao.update(taskInfos.get(i));
                            Intent intent = new Intent();
                            intent.putExtra(Params.TASK_ID,taskIds);
                            BroadCastUtils.sendIntentBroadCask(context,intent, Params.UPDATE_PROGERSS);
                            return;
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     *  重启已存在列表中的任务，原理上也是新任务，不过不插入数据库，而是更新已有记录，先添加，拿到ID后再更新记录里的ID
     */
    public void restartNormalTask(DowningTaskInfo downTaskInfo) {

        try {
            String path = FileUtils.isExistDir(Params.DEFAULT_PATH);
            TaskDao taskDao = TaskDao.getInstance(context);
            List<DowningTaskInfo> taskInfos = taskDao.queryAll();

            String link = downTaskInfo.getTaskUrl();
            if (link.contains("magnet") || XLTaskHelper.instance().getFileName(link).endsWith("torrent")) {
                if (link.startsWith("magnet")) {
                    newTaskId = addMagnetTask(link, path, null);
                } else {
                    newTaskId = addMagnetTask(getRealUrl(link), path, null);
                }
            } else {
                newTaskId = addThunderTask(link, path, null, context);
            }

            Log.e("testtaskid","--------"+newTaskId+"-----"+downTaskInfo.getTitle());

            if (taskInfos!=null&&taskInfos.size()>0){
                for (int i = 0; i < taskInfos.size(); i++) {
                    if (taskInfos.get(i).getUrlMd5().equals(downTaskInfo.getUrlMd5())){
                        //已在任务列表，查看状态，如果是正在下载，不添加
                        //更新表数据,如果是暂停，或者停止
                        if (taskInfos.get(i).getStatu()==0||taskInfos.get(i).getStatu()==4){
                            taskInfos.get(i).setTaskId(newTaskId);
                            taskDao.update(taskInfos.get(i));
                            Intent intent = new Intent();
                            intent.putExtra(Params.TASK_ID,newTaskId);
                            BroadCastUtils.sendIntentBroadCask(context,intent, Params.UPDATE_PROGERSS);
                            return;
                        }
                    }
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
