package dev.baofeng.com.supermovie.domain;
/**
 * Created by HuangYong on 2018/9/26.
 */

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description  简单的下载更新接口
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/26
 * @changeRecord [修改记录] <br/>
 * 2018/9/26 ：created
 */
public class AppUpdateInfo {


    /**
     * code : 200
     * data : {"version":"1.0.2","versionCode":103,"isForce":false,"downloadUrl":"http://dy.songore.cn/ygcms/app/app-release.apk","updateMsg":"1.修改了部分样式\n 2.修改了排序模式 \n 3.界面改版"}
     * msg :
     */

    private String code;
    private DataBean data;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * version : 1.0.2
         * versionCode : 103
         * isForce : false
         * downloadUrl : http://dy.songore.cn/ygcms/app/app-release.apk
         * updateMsg : 1.修改了部分样式
         2.修改了排序模式
         3.界面改版
         */

        private String version;
        private int versionCode;
        private boolean isForce;
        private String downloadUrl;
        private String updateMsg;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public boolean isIsForce() {
            return isForce;
        }

        public void setIsForce(boolean isForce) {
            this.isForce = isForce;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getUpdateMsg() {
            return updateMsg;
        }

        public void setUpdateMsg(String updateMsg) {
            this.updateMsg = updateMsg;
        }
    }
}
