package com.huangyong.downloadlib.model;

public interface ITask {

    void TaskStart(String taskId, String url);

    void TaskPause(String taskId);

    void restartTask(String taskId);

    void deleteTask(String taskId);
}
