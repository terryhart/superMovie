package com.huangyong.downloadlib.domain;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = FavorInfo.TABLE_NAME)
public class FavorInfo {

    public static final String TABLE_NAME = "t_favor";


    @DatabaseField(columnName = "title")
    private String title;

    @DatabaseField(columnName = "urlMd5")
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

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "taskId")
    private String taskId;

    @DatabaseField(columnName = "taskUrl")
    private String taskUrl;

    @DatabaseField(columnName = "taskStatu")
    private boolean taskStatu;

    @DatabaseField(columnName = "postImgUrl")
    private String postImgUrl;


    @DatabaseField(columnName = "movieDesc")
    private String movieDesc;

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
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

    public boolean isTaskStatu() {
        return taskStatu;
    }

    public void setTaskStatu(boolean taskStatu) {
        this.taskStatu = taskStatu;
    }
}
