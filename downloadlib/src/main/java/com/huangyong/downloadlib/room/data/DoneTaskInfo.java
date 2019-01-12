package com.huangyong.downloadlib.room.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@Entity(tableName = DoneTaskInfo.TABLE_NAME, indices = {@Index("title")})
public class DoneTaskInfo {

    public static final String TABLE_NAME = "t_haveedtask";


    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "urlMd5")
    private String urlMd5;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "taskId")
    private String taskId;

    @ColumnInfo(name = "taskUrl")
    private String taskUrl;

    @ColumnInfo(name = "totalSize")
    private String totalSize;

    @ColumnInfo(name = "receiveSize")
    private String receiveSize;

    @ColumnInfo(name = "localPath")
    private String localPath;

    @ColumnInfo(name = "taskStatu")
    private boolean taskStatu;

    @ColumnInfo(name = "postImgUrl")
    private String postImgUrl;

    @ColumnInfo(name = "filePath")
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private String speed;


    public String getSpeed() {
        return speed;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getPostImgUrl() {
        return postImgUrl;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getReceiveSize() {
        return receiveSize;
    }

    public void setReceiveSize(String receiveSize) {
        this.receiveSize = receiveSize;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public boolean isTaskStatu() {
        return taskStatu;
    }

    public void setTaskStatu(boolean taskStatu) {
        this.taskStatu = taskStatu;
    }
}
