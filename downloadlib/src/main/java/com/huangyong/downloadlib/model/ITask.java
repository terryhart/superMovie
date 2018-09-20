package com.huangyong.downloadlib.model;

import java.util.ArrayList;
import java.util.LinkedList;

public interface ITask {

    void TaskStart(LinkedList<String> taskId, String url);

   /* void TaskPause(String taskId);

    void restartTask(String taskId);

    void deleteTask(String taskId);*/
}
