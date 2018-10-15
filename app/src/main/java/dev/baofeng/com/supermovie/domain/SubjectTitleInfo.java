package dev.baofeng.com.supermovie.domain;
/**
 * Created by HuangYong on 2018/10/15.
 */

import java.util.List;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/10/15
 * @changeRecord [修改记录] <br/>
 * 2018/10/15 ：created
 */
public class SubjectTitleInfo {

    /**
     * Code : 200
     * Msg : 首页数据获取成功
     * Data : [{"sujectTitle":"2016内地票房排行榜","id":"44"},{"sujectTitle":"经典僵尸电影20部","id":"43"},{"sujectTitle":"5月佳片推荐","id":"42"},{"sujectTitle":"6月佳片推荐","id":"41"},{"sujectTitle":"豆瓣2016榜单","id":"40"},{"sujectTitle":"女人必看10电影","id":"39"}]
     */

    private int Code;
    private String Msg;
    private List<DataBean> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * sujectTitle : 2016内地票房排行榜
         * id : 44
         */

        private String sujectTitle;
        private String id;

        public String getSujectTitle() {
            return sujectTitle;
        }

        public void setSujectTitle(String sujectTitle) {
            this.sujectTitle = sujectTitle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
