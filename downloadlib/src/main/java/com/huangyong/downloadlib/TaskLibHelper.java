package com.huangyong.downloadlib;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.service.DownLoadService;
import com.huangyong.downloadlib.utils.BroadCastUtils;
import com.xunlei.downloadlib.XLTaskHelper;

public class TaskLibHelper {



    public static void init(Context context){
        XLTaskHelper.init(context.getApplicationContext());
        Intent intent = new Intent(context, DownLoadService.class);
        context.startService(intent);
    }


    public static void addNewTask(DowningTaskInfo info, Context context) {
        Intent intent = new Intent();
        intent.putExtra(Params.TASK_URL_KEY,info.getTaskUrl());
        intent.putExtra(Params.POST_IMG_KEY,info.getPostImgUrl());
        Log.e("ksdjglkdsl","TASK_URL----"+info.getTaskUrl());
        BroadCastUtils.sendIntentBroadCask(context,intent, Params.TASK_START);
    }

    public static void pauseTask(String url,Context context){
        Intent intent = new Intent();
        intent.putExtra(Params.TASK_URL_KEY,url);
        BroadCastUtils.sendIntentBroadCask(context,intent, Params.TASK_PAUSE);
    }

    public static void deleteTask(String url,Context context){
        Intent intent = new Intent();
        intent.putExtra(Params.TASK_URL_KEY,url);
        BroadCastUtils.sendIntentBroadCask(context,intent, Params.TASK_DELETE);
    }


}
