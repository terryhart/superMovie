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

    @DatabaseField(columnName = "downItemTitle")
    private String downItemTitle;

    @DatabaseField(columnName = "content_type")
    private String content_type;



    public String getMovie_type() {
        return movie_type;
    }

    public void setMovie_type(String movie_type) {
        this.movie_type = movie_type;
    }

    public String getIs_movie() {
        return is_movie;
    }

    public void setIs_movie(String is_movie) {
        this.is_movie = is_movie;
    }

    @DatabaseField(columnName = "is_movie")
    private String is_movie;

    @DatabaseField(columnName = "movie_type")
    private String movie_type;

    public String getDownload_url() {
        return download_url;
    }



    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    @DatabaseField(columnName = "download_url")
    private String download_url;

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public String getDownItemTitle() {
        return downItemTitle;
    }

    public void setDownItemTitle(String downItemTitle) {
        this.downItemTitle = downItemTitle;
    }

    @DatabaseField(columnName = "movieDesc")
    private String movieDesc;

    public String getMv_md5_id() {
        return mv_md5_id;
    }

    public void setMv_md5_id(String mv_md5_id) {
        this.mv_md5_id = mv_md5_id;
    }

    @DatabaseField(columnName = "mv_md5_id")
    private String mv_md5_id;

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
