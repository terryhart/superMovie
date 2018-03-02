package dev.baofeng.com.supermovie.domain;

import org.litepal.crud.DataSupport;

/**
 * Created by huangyong on 2018/3/1.
 */

public class TaskQuene extends DataSupport {

    private int id;
    private String name;
    private int progress;
    private String path;
    private String action;
    private long taskid;
    private long downSize;
    private long fileSize;

    public long getDownSize() {
        return downSize;
    }

    public void setDownSize(long downSize) {
        this.downSize = downSize;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
