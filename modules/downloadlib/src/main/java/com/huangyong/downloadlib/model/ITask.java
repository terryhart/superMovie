package com.huangyong.downloadlib.model;


import com.huangyong.downloadlib.room.data.DoneTaskInfo;
import com.huangyong.downloadlib.room.data.DowningTaskInfo;

import java.util.List;

public interface ITask {


    void repeatAdd(String s);

    void updateIngTask(List<DowningTaskInfo> taskInfo);

    void updateDoneTask(List<DoneTaskInfo> taskInfo);

}
