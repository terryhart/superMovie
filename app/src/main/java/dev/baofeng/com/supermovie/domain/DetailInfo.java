package dev.baofeng.com.supermovie.domain;

import java.util.ArrayList;

public class DetailInfo {

    private String mvDesc;

    private String imgUrl;

    private ArrayList<MovieInfo> movieInfos;

    public ArrayList<MovieInfo> getMovieInfos() {
        return movieInfos;
    }

    public void setMovieInfos(ArrayList<MovieInfo> movieInfos) {
        this.movieInfos = movieInfos;
    }

    public String getImgScreenShot() {
        return imgScreenShot;
    }

    public void setImgScreenShot(String imgScreenShot) {
        this.imgScreenShot = imgScreenShot;
    }

    private String imgScreenShot;


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private ArrayList<String> downUrl;

    private ArrayList<String> downUrlName;

    public ArrayList<String> getDownUrlName() {
        return downUrlName;
    }

    public void setDownUrlName(ArrayList<String> downUrlName) {
        this.downUrlName = downUrlName;
    }

    public String getMvDesc() {
        return mvDesc;
    }

    public void setMvDesc(String mvDesc) {
        this.mvDesc = mvDesc;
    }

    public ArrayList<String> getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(ArrayList<String> downUrl) {
        this.downUrl = downUrl;
    }
}
