package com.huangyong.downloadlib.presenter;

import android.content.Context;

import com.huangyong.downloadlib.domain.DownTaskInfo;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.BroadCastUtils;

import java.util.ArrayList;
import java.util.List;

public class DownLoadPresenter {


    private Context context;

    private ITask iTask;
    private final List taskList;

    public DownLoadPresenter(Context context, ITask iTask) {
        this.context = context;
        this.iTask = iTask;
        taskList = new ArrayList();
    }


    public void addTask(DownTaskInfo downTaskInfo) {

        taskList.add(downTaskInfo);

        //TODO 获取到taskId,localPath，存入数据库,发送广播到任务列表，查询数据库，查询并显示更新进度

        BroadCastUtils.sendIntentBroadCask(context, Params.UPDATE_PROGERSS);

    }
}
