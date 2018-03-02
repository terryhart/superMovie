package dev.baofeng.com.supermovie.domain;

import dev.baofeng.com.supermovie.bt.ThreadUtils;

/**
 * Created by huangyong on 2018/3/2.
 */

public class RunTaskInfo {
    private long taskId;
    private ThreadUtils.Task task;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public ThreadUtils.Task getTask() {
        return task;
    }

    public void setTask(ThreadUtils.Task task) {
        this.task = task;
    }
}
