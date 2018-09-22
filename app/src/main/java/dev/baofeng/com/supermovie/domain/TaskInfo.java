package dev.baofeng.com.supermovie.domain;


/**
 * Created by huangyong on 2018/2/24.
 */

public class TaskInfo {
    private int isWaiting;

    public int getIsWaiting() {
        return isWaiting;
    }

    public void setIsWaiting(int isWaiting) {
        this.isWaiting = isWaiting;
    }

    private int id;
    private String name;
    private int progress;
    private String path;
    private String action;
    private String taskid;
    private String downSize;
    private String fileSize;
    private String localpath;

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    public String getDownSize() {
        return downSize;
    }

    public void setDownSize(String downSize) {
        this.downSize = downSize;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
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
