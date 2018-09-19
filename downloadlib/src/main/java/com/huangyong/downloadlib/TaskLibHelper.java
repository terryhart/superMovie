package com.huangyong.downloadlib;

import android.content.Context;
import android.util.Log;

import com.huangyong.downloadlib.domain.DownTaskInfo;
import com.xunlei.downloadlib.XLDownloadManager;
import com.xunlei.downloadlib.XLTaskHelper;

public class TaskLibHelper {

    private static String taskId;

    public static void init(Context context){
        XLTaskHelper.init(context.getApplicationContext());
    }

    public static void addNewTask(DownTaskInfo info,Context context) {



        String link = info.getTaskUrl();
        String path = info.getLocalPath();

        if (link.startsWith("magnet:?") || XLTaskHelper.instance().getFileName(link).endsWith("torrent")) {
            if (link.startsWith("magnet:?")) {
                taskId = addMagnetTask(link, path, null);
            } else {
                taskId = addMagnetTask(getRealUrl(link), path, null);
            }
        } else {
            taskId = addThunderTask(link, path, null, context);
        }
        Log.e("kaishixiazai-----",taskId);
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
