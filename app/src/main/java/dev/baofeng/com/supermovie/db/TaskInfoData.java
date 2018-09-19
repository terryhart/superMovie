package dev.baofeng.com.supermovie.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by huangyong on 2018/2/24.
 */
@DatabaseTable(tableName = "task")
public class TaskInfoData {

    @DatabaseField(useGetSet=true,generatedId=true,columnName="id")
    private int id;

    @DatabaseField(useGetSet=true, columnName = "is_wating")
    private int isWaiting;


    @DatabaseField(useGetSet=true, columnName = "name")
    private String name;

    @DatabaseField(useGetSet=true, columnName = "progress")
    private int progress;

    @DatabaseField(useGetSet=true, columnName = "path")
    private String path;

    @DatabaseField(useGetSet=true, columnName = "action")
    private String action;

    @DatabaseField(useGetSet=true, columnName = "taskid")
    private String taskid;

    @DatabaseField(useGetSet=true, columnName = "downSize")
    private String downSize;

    @DatabaseField(useGetSet=true, columnName = "fileSize")
    private String fileSize;

    @DatabaseField(useGetSet=true, columnName = "localpath")
    private String localpath;


    public int getIsWaiting() {
        return isWaiting;
    }

    public void setIsWaiting(int isWaiting) {
        this.isWaiting = isWaiting;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
