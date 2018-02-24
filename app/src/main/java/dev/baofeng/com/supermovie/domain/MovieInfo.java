package dev.baofeng.com.supermovie.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyong on 2018/1/26.
 */

public class MovieInfo {


    /**
     * code : 200
     * message : 数据获取成功
     * data : [{"movClass":"2017","downLoadName":"蝙蝠侠与哈莉·奎恩","downLoadUrl":null,"mvdesc":"['2017(美国)', '凯文·康瑞', '罗伦·莱斯特', '梅丽莎·劳奇', '帕姬·布鲁斯特', '约翰·迪·玛吉欧', '罗宾·阿特金·唐斯', '明迪·斯特林', '特雷弗·德瓦', '罗伯·鲍森', '艾瑞克·鲍扎', '凯文·迈克尔·理查德森', '刘山姆']","downimgurl":"https://image.bttt.la/litpic/2014/15Aug2017221804.jpg","downdtitle":null,"id":"83554","btlevel":"['豆瓣评分：', ' \\xa0']['0'].['.', '0']","btYear":"2017"},{"movClass":"2017","downLoadName":"哈桑·明哈杰：返校之王","downLoadUrl":null,"mvdesc":"['2017(美国)', '哈桑·明哈杰', 'Christopher Storer']","downimgurl":"https://image.bttt.la/litpic/2014/18Jun2017230834.jpg","downdtitle":null,"id":"83611","btlevel":"['豆瓣评分：', ' \\xa0']['8'].['.', '1']","btYear":"2017"},{"movClass":"2016","downLoadName":"我在曼哈顿卖大麻","downLoadUrl":null,"mvdesc":"['2016(美国)', '亚历珊德拉·达达里奥', '乔什·布雷纳', 'Lindsey Broad', '托芙·菲尔德舒', 'Rory Rooney']","downimgurl":"https://image.bttt.la/litpic/2014/06Jan2017224936.jpg","downdtitle":null,"id":"83672","btlevel":"['豆瓣评分：', ' \\xa0']['5'].['.', '5']","btYear":"2016"},{"movClass":"惊悚","downLoadName":"曼哈顿夜曲","downLoadUrl":null,"mvdesc":"['2016(美国)', '伊冯娜·斯特拉霍夫斯基', '艾德里安·布洛迪', '坎贝尔·斯科特', '詹妮弗·比尔斯', '史蒂文·伯克夫', '琳达·拉文', '凯文·布雷斯纳汉', '弗兰克·迪尔', '维尔·贝布林克', '斯坦·卡普', 'Michael G. Chin', 'Brian DeCubellis']","downimgurl":"https://image.bttt.la/litpic/2014/24May2016232052.jpg","downdtitle":null,"id":"86336","btlevel":"['豆瓣评分：', ' \\xa0']['0'].['.', '0']","btYear":"2016"},{"movClass":"美国","downLoadName":"哈罗德和莉莉安：好莱坞爱情故事","downLoadUrl":null,"mvdesc":"['2015(美国)', 'Lillian Michelson', '哈罗德·迈克尔逊', '丹尼·德维托', '梅尔·布鲁克斯', '弗朗西斯·福特·科波拉', 'Daniel Raim']","downimgurl":"https://image.bttt.la/litpic/2014/20Nov2017195328.jpg","downdtitle":null,"id":"85314","btlevel":"['豆瓣评分：', ' \\xa0']['0'].['.', '0']","btYear":"2015"},{"movClass":"爱情","downLoadName":"阿罗哈电影","downLoadUrl":null,"mvdesc":"['2015(美国)', '布莱德利·库珀', '艾玛·斯通', '瑞秋·麦克亚当斯', '比尔·默瑞', '约翰·卡拉辛斯基', '丹尼·麦克布耐德', '亚历克·鲍德温', 'Bill Camp', '杰顿·李博赫', '丹妮尔·罗丝·拉塞尔', '迈克尔·切鲁斯', '艾迪·盖瑟吉', '伊丽莎白·玛维尔', '伊万娜·米利塞维奇', '卡梅伦·克罗']","downimgurl":"https://image.bttt.la/litpic/2014/09Aug2015223550.jpg","downdtitle":null,"id":"89472","btlevel":"['豆瓣评分：', ' \\xa0']['5'].['.', '8']","btYear":"2015"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public static List<MovieInfo> arrayMovieInfoFromData(String str) {

        Type listType = new TypeToken<ArrayList<MovieInfo>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * movClass : 2017
         * downLoadName : 蝙蝠侠与哈莉·奎恩
         * downLoadUrl : null
         * mvdesc : ['2017(美国)', '凯文·康瑞', '罗伦·莱斯特', '梅丽莎·劳奇', '帕姬·布鲁斯特', '约翰·迪·玛吉欧', '罗宾·阿特金·唐斯', '明迪·斯特林', '特雷弗·德瓦', '罗伯·鲍森', '艾瑞克·鲍扎', '凯文·迈克尔·理查德森', '刘山姆']
         * downimgurl : https://image.bttt.la/litpic/2014/15Aug2017221804.jpg
         * downdtitle : null
         * id : 83554
         * btlevel : ['豆瓣评分：', ' \xa0']['0'].['.', '0']
         * btYear : 2017
         */

        private String movClass;
        private String downLoadName;
        private String downLoadUrl;
        private String mvdesc;
        private String downimgurl;
        private String downdtitle;
        private String id;
        private String btlevel;
        private String btYear;

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getMovClass() {
            return movClass;
        }

        public void setMovClass(String movClass) {
            this.movClass = movClass;
        }

        public String getDownLoadName() {
            return downLoadName;
        }

        public void setDownLoadName(String downLoadName) {
            this.downLoadName = downLoadName;
        }

        public String getDownLoadUrl() {
            return downLoadUrl;
        }

        public void setDownLoadUrl(String downLoadUrl) {
            this.downLoadUrl = downLoadUrl;
        }

        public String getMvdesc() {
            return mvdesc;
        }

        public void setMvdesc(String mvdesc) {
            this.mvdesc = mvdesc;
        }

        public String getDownimgurl() {
            return downimgurl;
        }

        public void setDownimgurl(String downimgurl) {
            this.downimgurl = downimgurl;
        }

        public Object getDowndtitle() {
            return downdtitle;
        }

        public void setDowndtitle(String downdtitle) {
            this.downdtitle = downdtitle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBtlevel() {
            return btlevel;
        }

        public void setBtlevel(String btlevel) {
            this.btlevel = btlevel;
        }

        public String getBtYear() {
            return btYear;
        }

        public void setBtYear(String btYear) {
            this.btYear = btYear;
        }
    }
}
