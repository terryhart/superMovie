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
    private List<XunleiBean> xunlei;

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

    public List<XunleiBean> getXunlei() {
        return xunlei;
    }

    public void setXunlei(List<XunleiBean> xunlei) {
        this.xunlei = xunlei;
    }

    public static class M3u8Bean {
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

    public static class NormalBean {
        /**
         * title : HD1280高清中英双字版$http://cn2.zuidadianying.com/share/ZpCdzq78LF3xQpA6
         * url : http://cn2.zuidadianying.com/share/ZpCdzq78LF3xQpA6
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

    public static class XunleiBean {
        /**
         * title : http://xunleib.zuida360.com/1811/胆小勿入.HD1280高清中英双字版.mp4
         * url : http://xunleib.zuida360.com/1811/胆小勿入.HD1280高清中英双字版.mp4
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
