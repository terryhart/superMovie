package dev.baofeng.com.supermovie.domain;

public class BtPlayInfo {

    private String movName;

    private String movPlayUrl;

    private String movPoster;

    private String progress;

    public String getMovPoster() {
        return movPoster;
    }

    public void setMovPoster(String movPoster) {
        this.movPoster = movPoster;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getMovTitle() {
        return movTitle;
    }

    public void setMovTitle(String movTitle) {
        this.movTitle = movTitle;
    }

    private String movTitle;

    public String getMovName() {
        return movName;
    }

    public void setMovName(String movName) {
        this.movName = movName;
    }

    public String getMovPlayUrl() {
        return movPlayUrl;
    }

    public void setMovPlayUrl(String movPlayUrl) {
        this.movPlayUrl = movPlayUrl;
    }
}
