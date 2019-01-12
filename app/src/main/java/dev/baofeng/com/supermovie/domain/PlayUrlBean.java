package dev.baofeng.com.supermovie.domain;

import java.util.List;

/**
 * creator huangyong
 * createTime 2018/12/26 下午1:26
 * path dev.baofeng.com.supermovie.domain
 * description:
 */
public class PlayUrlBean {

    private List<M3u8Bean> m3u8;
    private List<NormalBean> normal;

    public List<M3u8Bean> getM3u8() {
        return m3u8;
    }

    public void setM3u8(List<M3u8Bean> m3u8) {
        this.m3u8 = m3u8;
    }

    public List<NormalBean> getNormal() {
        return normal;
    }

    public void setNormal(List<NormalBean> normal) {
        this.normal = normal;
    }

    public static class M3u8Bean {
        /**
         * title : 第01集$https://bobo.kukucdn.com/20181214/2385_e22c25ef/index.m3u8
         * url : https://bobo.kukucdn.com/20181214/2385_e22c25ef/index.m3u8
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

    public static class NormalBean {
        /**
         * title : 第01集$https://bobo.kukucdn.com/share/1a68e5f4ade56ed1d4bf273e55510750
         * url : https://bobo.kukucdn.com/share/1a68e5f4ade56ed1d4bf273e55510750
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
}
