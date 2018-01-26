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
     * message : 首页数据获取成功
     * data : [{"movClass":"战争片","downLoadName":"钢琴师[1024]","downLoadUrl":"['ftp://hddy:hddy@lz.wofei.net:1222/[1024高清收藏www.wofei.net]/飞鸟娱乐(bbs.wofei.net).钢琴师1280x692版/飞鸟娱乐(bbs.wofei.net).钢琴师1280x692版.rmvb']","mvdesc":"['\\r\\n◎译 名 钢琴师', '\\r\\n◎片 名 The Pianist', '\\r\\n◎年 代 2002', '\\r\\n◎国 家 荷兰/英国/法国/德国', '\\r\\n◎类 别 剧情 / 战争 / 音乐', '\\r\\n◎语 言 英语', '\\r\\n◎字 幕 中英字幕', '\\r\\n', '\\r\\n◎IMDB链接 http://www.imdb.com/title/tt0253474/', '\\r\\n◎文件格式 Bluray-RMVB', '\\r\\n', '\\r\\n◎文件大小 1CD ', '\\r\\n◎片 长 150 Min', '\\r\\n◎导 演 罗曼·波兰斯基 Roman Polanski ', '\\r\\n◎主 演 艾德林 布罗迪 (Adrien Brody) ...... Wladyslaw Szpilman', '\\r\\n 托马斯 克莱彻曼 (Thomas Kretschmann) ...... Captain Wilm Hosenfeld', '\\r\\n 弗兰克 芬利 (Frank Finlay) ...... Father', '\\r\\n Maureen Lipman ...... Mother', '\\r\\n Emilia Fox ...... Dorota', '\\r\\n Ed Stoppard ...... Henryk', '\\r\\n Julia Rayner ...... Regina', '\\r\\n Jessica Kate Meyer ...... Halina', '\\r\\n Michal Zebrowski ...... Jurek', '\\r\\n', '\\r\\n', '\\r\\n', '\\r\\n◎简 介 ', '\\r\\n', '\\r\\n 二战期间，一位天才的波兰犹太钢琴家，四处躲藏以免落入纳粹的魔爪。他在华沙的犹太区里饱受着饥饿的折磨和各种羞辱，整日处在死亡的威胁下。他躲过了地毯式的搜查，藏身于城市的废墟中。幸运的是他的音乐才华感动了一名德国军官，在军官的冒死保护","downimgurl":"http://img.album.pchome.net/54/7","downdtitle":"['迅雷下载地址']","id":"15546"},{"movClass":"战争片","downLoadName":"东线情报战[1024]星星BD中字","downLoadUrl":"['ftp://6:6@6.66ys.org:2144/【更多电影请去www.6vdy.net】东线情报战BD中字.rmvb']","mvdesc":"['\\r\\n', '\\r\\n', '\\r\\n', '\\r\\n', '\\r\\n', '\\r\\n'][]","downimgurl":"http://photo4.zxip.com/albums4/f","downdtitle":"['ftp://6:6@6.66ys.org:2144/【更多电","id":"15547"},{"movClass":"战争片","downLoadName":"巴顿将军[高清]","downLoadUrl":"['http://www.6vhao.com/diany/juqingpian/4219/', 'ftp://dy131.com:6vdy.com@ftp1.66e.cc:2618/【6v电影www.dy131.com】巴顿将军BD国英双语中英双字1024高清.mkv', 'http://urlxf.qq.com/?3EFbI3b', 'http://pan.baidu.com/s/1xORgQ', 'ftp://6:6@ftp.kan66.com:4127/【6v电影www.dy131.com】巴顿将军BD中英双字1280高清CD1.rmvb', 'ftp://6:6@ftp.kan66.c","mvdesc":"['\\r\\n【译 名】 巴顿将军/铁血将军巴顿', '\\r\\n【片 名】 Patton', '\\r\\n【年 代】 1970', '\\r\\n【国 家】 美国', '\\r\\n【类 别】 传记/剧情/历史/战争', '\\r\\n【语 言】 英语', '\\r\\n【字幕语言】 中文/英文', '\\r\\n【视频尺寸】 1280×720', '\\r\\n【文件格式】 RMVB+AAC', '\\r\\n【IMDB评分】 8.1/10 (23,650 votes)', '\\r\\n【片 长】 171 MiN', '\\r\\n【导 演】 弗兰克林·斯凡那 Franklin J. Schaffner', '\\r\\n【下载地址】 ', 'http://www.6vhao.net/', '\\r\\n【在线观看】 ', 'http://www.6vhao.com/', '\\r\\n【主 演】 乔治 C·斯科特 George C. Scott .....Gen. George S. Patton Jr.', '\\r\\n 卡尔·莫尔登 Karl Malden .....Gen. Omar N. Bradley', '\\r\\n Stephen Young .....Capt. Chester B. Hansen', '\\r\\n Michael Strong .....Brig. Gen. Hobart Carver', \"\\r\\n Carey Loftin .....Gen. Bradley's driver (as Cary Loftin)\", '\\r\\n Albert Dumortier .....Moroccan Minister', '\\r\\n Frank Latimore .....Lt. Col. Henry Davenport', '\\r\\n Morgan Paull .....Capt. Richard N. Jenson', '\\r\\n Karl Michael Vogler .....Field Marshal Erwin Rommel',","downimgurl":"http://tu2.66vod.net/tupian/2013","downdtitle":"['http://www.6vhao.com/diany/juq","id":"15548"},{"movClass":"战争片","downLoadName":"斯大林格勒[高清]","downLoadUrl":"['http://www.6vhao.com/diany/zhanzhengpian/63833/', 'ftp://dy131.com:6vdy.com@ftp2.66e.cc:6527/【6v电影www.dy131.com】斯大林格勒.720p.国俄双语.BD中字.mkv', 'ftp://dy131.com:6vdy.com@ftp2.66e.cc:6527/【6v电影www.dy131.com】斯大林格勒BD国俄双语中字1024高清.mkv', 'http://urlxf.qq.com/?IJjABze', 'http://pan.baidu.com/share/link?sharei","mvdesc":"['◎译 名 斯大林格勒/斯大林格勒保卫战 ', '\\r\\n◎片 名 Stalingrad\\xa0 ', '\\r\\n◎年 代 2013 ', '\\r\\n◎国 家 美国/俄罗斯/德国\\xa0\\xa0\\xa0 ', '\\r\\n◎类 别 动作/战争\\xa0\\xa0 ', '\\r\\n◎语 言 俄语/德语\\xa0 ', '\\r\\n◎字 幕 中字', '\\r\\n◎IMDB评分 4.9/10 from 2,154 users', '\\r\\n◎文件格式 X264 + DTS', '\\r\\n◎视频尺寸 1280 x 528', '\\r\\n◎文件大小 1CD', '\\r\\n◎片 长 2h 10mn', '\\r\\n◎导 演 费多尔·邦达尔丘克 Fyodor Bondarchuk', '\\r\\n◎下载地址 ', 'http://www.6vhao.net/', '\\r\\n◎在线观看 ', 'http://www.6vhao.com/', '\\r\\n◎主 演 皮欧特·费奥多罗夫 Pyotr Fyodorov\\xa0 ....Kapitan Gromov', '\\r\\n 玛丽娅·斯莫尼科娃 Maria Smolnikova ', '\\r\\n 托马斯·克莱舒曼 Thomas Kretschmann', '\\r\\n 娅尼娜·斯图迪利纳 Yanina Studilina\\xa0 ....Masha', '\\r\\n 菲利普·莱因哈特 Philippe Reinhardt\\xa0 ....Gottfried', '\\r\\n Heiner Lauterbach ', '\\r\\n 安德烈·斯莫利亚科夫 Andrei Smolyakov ', '\\r\\n 阿列克谢·巴拉瓦什 Aleksei Barabash', '\\r\\n Georges Devdariani\\xa0 ....Klose', '\\r\\n Dmitri Lysenkov\\xa0 ....Serzhant Chvanov', '\\r\\n Ol","downimgurl":"http://tu2.66vod.net/tupian/2013","downdtitle":"['http://www.6vhao.com/diany/zha","id":"15549"},{"movClass":"战争片","downLoadName":"不列颠之战[高清]","downLoadUrl":"['ftp://dy131.com:6vdy.com@ftp1.66e.cc:2618/【6v电影www.dy131.com】不列颠之战BD国英双语中英双字1024高清.mkv', 'http://urlxf.qq.com/?M3E3yqe', 'http://pan.baidu.com/share/link?shareid=711847664&uk=2889667432']","mvdesc":"['\\r\\n◎译 名 不列颠之战 / 大不列颠之战 / 空军大战略', '\\r\\n◎片 名 Battle of Britain', '\\r\\n◎年 代 1969', '\\r\\n◎国 家 英国', '\\r\\n◎类 别 动作/战争/剧情/历史', '\\r\\n◎语 言 英语', '\\r\\n◎字 幕 中英双语字幕 国英音轨', '\\r\\n◎IMDB评分 (6.8/10 from 12,190 users)', '\\r\\n◎文件格式 X264 + AC3', '\\r\\n◎视频尺寸 1024X576', '\\r\\n◎文件大小 2.14G', '\\r\\n◎片 长\\xa0 133 min', '\\r\\n◎导 演 盖伊·汉弥尔顿', '\\r\\n◎下载地址 ', 'http://www.6vhao.net/', '\\r\\n◎在线观看 ', 'http://www.6vhao.com/', '\\r\\n◎主 演 迈克尔·凯恩 Michael Caine\\xa0 ....Squadron Leader Canfield', '\\r\\n 特瑞沃·霍华德 Trevor Howard\\xa0 ....Air Vice Marshal Keith Park', '\\r\\n 尤尔根斯·库尔特 Curd Jürgens\\xa0 ....Baron von Richter (as Curt Jurgens)', '\\r\\n 伊恩·麦柯肖恩 Ian McShane\\xa0 ....Sgt. Pilot Andy', '\\r\\n 肯尼斯·摩尔 Kenneth More\\xa0 ....Group Capt. Baker', '\\r\\n 劳伦斯·奥利弗 Laurence Olivier\\xa0 ....Air Chief Marshal Sir Hugh Dowding', '\\r\\n 奈吉尔·帕特里克 Nigel Patrick\\xa0 ....Group Capt. Hope', '\\r\\n 克里斯托弗·普卢默 Christopher Plummer\\xa0 ....S","downimgurl":"http://tu2.66vod.net/tupian/2013","downdtitle":"['ftp://dy131.com:6vdy.com@ftp1.","id":"15550"},{"movClass":"战争片","downLoadName":"护送钱斯[高清]","downLoadUrl":"['http://www.6vhao.com/diany/juqingpian/3725/', 'ftp://6v:6v@ftp1.kan66.com:1269/【6v电影www.dy131.com】护送钱斯BD中英双字1024高清.mkv']","mvdesc":"['◎译 名 护送钱斯', '\\r\\n◎片 名 Taking Chance', '\\r\\n◎年 代 2009', '\\r\\n◎国 家 美国', '\\r\\n◎类 别 剧情/战争', '\\r\\n◎语 言 英语', '\\r\\n◎字 幕 中英双字', '\\r\\n◎下载地址 ', 'http://www.6vhao.net/', '\\r\\n◎在线观看 ', 'http://www.6vhao.com/', '\\r\\n◎IMDB评分\\xa0 7.5/10\\xa0 1,795 votes', '\\r\\n◎文件格式 RMVB', '\\r\\n◎视频尺寸 1024 x 576', '\\r\\n◎文件大小 1CD', '\\r\\n◎片 长 77 min', '\\r\\n◎导 演 罗斯·卡兹 Ross Katz', '\\r\\n◎主 演 凯文·贝肯 Kevin Bacon ...\\xa0 LtCol Mike Strobl ', '\\r\\n 汤姆·阿尔德雷吉 Tom Aldredge ...\\xa0 Charlie Fitts ', '\\r\\n Nicholas Art ...\\xa0 Nate Strobl (as Nicholas Reese Art) ', '\\r\\n Blanche Baker ...\\xa0 Chris Phelps ', '\\r\\n Tom Bloom ...\\xa0 Navy Chaplain ', '\\r\\n Guy Boyd ...\\xa0 Gary Hargrove ', '\\r\\n James Castanien ...\\xa0 Robert Orndoff ', '\\r\\n Gordon Clapp ...\\xa0 Tom Garrett ', '\\r\\n Mike Colter ...\\xa0 MGySgt Demetry ', '\\r\\n Henry Coy ...\\xa0 Marine Driver (as GySgt Henry Coy) ', '\\r\\n ","downimgurl":"http://tu2.66vod.net/kan66net/up","downdtitle":"['http://www.6vhao.com/diany/juq","id":"15551"}]
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
         * movClass : 战争片
         * downLoadName : 钢琴师[1024]
         * downLoadUrl : ['ftp://hddy:hddy@lz.wofei.net:1222/[1024高清收藏www.wofei.net]/飞鸟娱乐(bbs.wofei.net).钢琴师1280x692版/飞鸟娱乐(bbs.wofei.net).钢琴师1280x692版.rmvb']
         * mvdesc : ['\r\n◎译 名 钢琴师', '\r\n◎片 名 The Pianist', '\r\n◎年 代 2002', '\r\n◎国 家 荷兰/英国/法国/德国', '\r\n◎类 别 剧情 / 战争 / 音乐', '\r\n◎语 言 英语', '\r\n◎字 幕 中英字幕', '\r\n', '\r\n◎IMDB链接 http://www.imdb.com/title/tt0253474/', '\r\n◎文件格式 Bluray-RMVB', '\r\n', '\r\n◎文件大小 1CD ', '\r\n◎片 长 150 Min', '\r\n◎导 演 罗曼·波兰斯基 Roman Polanski ', '\r\n◎主 演 艾德林 布罗迪 (Adrien Brody) ...... Wladyslaw Szpilman', '\r\n 托马斯 克莱彻曼 (Thomas Kretschmann) ...... Captain Wilm Hosenfeld', '\r\n 弗兰克 芬利 (Frank Finlay) ...... Father', '\r\n Maureen Lipman ...... Mother', '\r\n Emilia Fox ...... Dorota', '\r\n Ed Stoppard ...... Henryk', '\r\n Julia Rayner ...... Regina', '\r\n Jessica Kate Meyer ...... Halina', '\r\n Michal Zebrowski ...... Jurek', '\r\n', '\r\n', '\r\n', '\r\n◎简 介 ', '\r\n', '\r\n 二战期间，一位天才的波兰犹太钢琴家，四处躲藏以免落入纳粹的魔爪。他在华沙的犹太区里饱受着饥饿的折磨和各种羞辱，整日处在死亡的威胁下。他躲过了地毯式的搜查，藏身于城市的废墟中。幸运的是他的音乐才华感动了一名德国军官，在军官的冒死保护
         * downimgurl : http://img.album.pchome.net/54/7
         * downdtitle : ['迅雷下载地址']
         * id : 15546
         */

        private String movClass;
        private String downLoadName;
        private String downLoadUrl;
        private String mvdesc;
        private String downimgurl;
        private String downdtitle;
        private String id;

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
    }
}
