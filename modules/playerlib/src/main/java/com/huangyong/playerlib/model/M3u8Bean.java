package com.huangyong.playerlib.model;

import java.io.Serializable;

/**
 * creator huangyong
 * createTime 2019/4/3 下午2:00
 * path com.huangyong.playerlib.model
 * description:
 */
public class M3u8Bean implements Serializable {
    /**
     * title : HD1280高清中英双字版$http://cn2.zuidadianying.com/20181118/UfalxqqL/index.m3u8
     * url : http://cn2.zuidadianying.com/20181118/UfalxqqL/index.m3u8
     */

    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
