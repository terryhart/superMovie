package com.huangyong.downloadlib.room.data;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 下载数据封装的数据库文件
 */

@Entity(tableName = DowningTaskInfo.TABLE_NAME, indices = {@Index("title")})
public class DowningTaskInfo {

    public static final String TABLE_NAME = "t_task";
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

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

    @ColumnInfo(name = "urlMd5")
    private String urlMd5;

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

    @ColumnInfo(name = "speed")
    private String speed;

    @ColumnInfo(name = "proxyPlayUrl")
    private String proxyPlayUrl;

    @ColumnInfo(name = "index")
    private String index;

    public String getTorrentPath() {
        return torrentPath;
    }

    public void setTorrentPath(String torrentPath) {
        this.torrentPath = torrentPath;
    }

    @ColumnInfo(name = "torrentPath")
    private String torrentPath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @ColumnInfo(name = "filePath")
    private String filePath ;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    //由于重启的任务走的是添加新任务的流程，这个用来区分来自哪里的添加动作，是否新添加，默认true,不需要数据库存储
    public boolean getTaskFrom() {
        return taskFrom;
    }
    public void setTaskFrom(boolean taskFrom) {
        this.taskFrom = taskFrom;
    }


    @ColumnInfo(name = "taskFrom")
    private boolean taskFrom;

    public String getProxyPlayUrl() {
        return proxyPlayUrl;
    }

    public void setProxyPlayUrl(String proxyPlayUrl) {
        this.proxyPlayUrl = proxyPlayUrl;
    }


    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    @ColumnInfo(name = "statu")
    private int statu;


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
