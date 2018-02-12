package dev.baofeng.com.supermovie.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyong on 2018/2/12.
 */

public class BtInfo {

    /**
     * code : 200
     * message : 首页数据获取成功
     * data : [{"movClass":"战争","downLoadName":"感谢您的服役","downParam":"['download', '30828', '9d79090fb74d335e00c51113', '/download1.php']","mvdesc":"['2017(美国)', '海莉·贝内特', '迈尔斯·特勒', '凯莎·卡斯特-休伊斯', '艾米·舒默', '乔·科尔', '凯特·林恩·希尔', '比尤拉·寇尔', '艾琳·达克', '克里·卡希尔', '塞西尔·M·亨利', '斯科特·黑兹', '奥马尔·J·多尔西', '布拉德·比耶尔', '阿利森·金', '杰森·华纳·史密斯', '迈克尔·洛夫·托利弗', '唐娜·杜普兰提尔', '彼特·奥梅拉', '亨特·巴克', '兰德尔·泰勒', '丹尼斯·L·A·怀特', '亚历克斯·科克尔', '菲尔·阿米霍', '杰森·霍尔']","downimgurl":"https://image.bttt.la/litpic/2014/10Jan2018231900.jpg","downdtitle":"【720p高清】感谢您的服役 /Thank You for Your Service .2017.3.22GBBT种子下载","id":"160374","btlevel":"['豆瓣评分：', ' \\xa0']['0'].['.', '0']","btYear":"17"},{"movClass":"战争","downLoadName":"感谢您的服役","downParam":"['download', '30828', 'be4edbd12afc331891ddfbdd', '/download2.php']","mvdesc":"['2017(美国)', '海莉·贝内特', '迈尔斯·特勒', '凯莎·卡斯特-休伊斯', '艾米·舒默', '乔·科尔', '凯特·林恩·希尔', '比尤拉·寇尔', '艾琳·达克', '克里·卡希尔', '塞西尔·M·亨利', '斯科特·黑兹', '奥马尔·J·多尔西', '布拉德·比耶尔', '阿利森·金', '杰森·华纳·史密斯', '迈克尔·洛夫·托利弗', '唐娜·杜普兰提尔', '彼特·奥梅拉', '亨特·巴克', '兰德尔·泰勒', '丹尼斯·L·A·怀特', '亚历克斯·科克尔', '菲尔·阿米霍', '杰森·霍尔']","downimgurl":"https://image.bttt.la/litpic/2014/10Jan2018231900.jpg","downdtitle":"【720p高清】感谢您的服役 /Thank You for Your Service .2017.3.22GBBT种子下载","id":"160375","btlevel":"['豆瓣评分：', ' \\xa0']['0'].['.', '0']","btYear":"17"},{"movClass":"战争","downLoadName":"感谢您的服役","downParam":"['download', '30828', '54963371f36b6265f63a5b70', '/download2.php']","mvdesc":"['2017(美国)', '海莉·贝内特', '迈尔斯·特勒', '凯莎·卡斯特-休伊斯', '艾米·舒默', '乔·科尔', '凯特·林恩·希尔', '比尤拉·寇尔', '艾琳·达克', '克里·卡希尔', '塞西尔·M·亨利', '斯科特·黑兹', '奥马尔·J·多尔西', '布拉德·比耶尔', '阿利森·金', '杰森·华纳·史密斯', '迈克尔·洛夫·托利弗', '唐娜·杜普兰提尔', '彼特·奥梅拉', '亨特·巴克', '兰德尔·泰勒', '丹尼斯·L·A·怀特', '亚历克斯·科克尔', '菲尔·阿米霍', '杰森·霍尔']","downimgurl":"https://image.bttt.la/litpic/2014/10Jan2018231900.jpg","downdtitle":"【720p高清】感谢您的服役 /Thank You for Your Service .2017.3.22GBBT种子下载","id":"160378","btlevel":"['豆瓣评分：', ' \\xa0']['0'].['.', '0']","btYear":"17"},{"movClass":"战争","downLoadName":"感谢您的服役","downParam":"['download', '30828', 'fc1d8c9ea6717c245bb0ab8c', '/download2.php']","mvdesc":"['2017(美国)', '海莉·贝内特', '迈尔斯·特勒', '凯莎·卡斯特-休伊斯', '艾米·舒默', '乔·科尔', '凯特·林恩·希尔', '比尤拉·寇尔', '艾琳·达克', '克里·卡希尔', '塞西尔·M·亨利', '斯科特·黑兹', '奥马尔·J·多尔西', '布拉德·比耶尔', '阿利森·金', '杰森·华纳·史密斯', '迈克尔·洛夫·托利弗', '唐娜·杜普兰提尔', '彼特·奥梅拉', '亨特·巴克', '兰德尔·泰勒', '丹尼斯·L·A·怀特', '亚历克斯·科克尔', '菲尔·阿米霍', '杰森·霍尔']","downimgurl":"https://image.bttt.la/litpic/2014/10Jan2018231900.jpg","downdtitle":"【720p高清】感谢您的服役 /Thank You for Your Service .2017.3.22GBBT种子下载","id":"160381","btlevel":"['豆瓣评分：', ' \\xa0']['0'].['.', '0']","btYear":"17"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public static List<BtInfo> arrayBtInfoFromData(String str) {

        Type listType = new TypeToken<ArrayList<BtInfo>>() {
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
         * movClass : 战争
         * downLoadName : 感谢您的服役
         * downParam : ['download', '30828', '9d79090fb74d335e00c51113', '/download1.php']
         * mvdesc : ['2017(美国)', '海莉·贝内特', '迈尔斯·特勒', '凯莎·卡斯特-休伊斯', '艾米·舒默', '乔·科尔', '凯特·林恩·希尔', '比尤拉·寇尔', '艾琳·达克', '克里·卡希尔', '塞西尔·M·亨利', '斯科特·黑兹', '奥马尔·J·多尔西', '布拉德·比耶尔', '阿利森·金', '杰森·华纳·史密斯', '迈克尔·洛夫·托利弗', '唐娜·杜普兰提尔', '彼特·奥梅拉', '亨特·巴克', '兰德尔·泰勒', '丹尼斯·L·A·怀特', '亚历克斯·科克尔', '菲尔·阿米霍', '杰森·霍尔']
         * downimgurl : https://image.bttt.la/litpic/2014/10Jan2018231900.jpg
         * downdtitle : 【720p高清】感谢您的服役 /Thank You for Your Service .2017.3.22GBBT种子下载
         * id : 160374
         * btlevel : ['豆瓣评分：', ' \xa0']['0'].['.', '0']
         * btYear : 17
         */

        private String movClass;
        private String downLoadName;
        private String downParam;
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

        public String getDownParam() {
            return downParam;
        }

        public void setDownParam(String downParam) {
            this.downParam = downParam;
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

        public String getDowndtitle() {
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
