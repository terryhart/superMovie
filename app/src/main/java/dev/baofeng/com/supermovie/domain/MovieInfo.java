package dev.baofeng.com.supermovie.domain;

import java.util.List;

/**
 * Created by huangyong on 2018/1/26.
 */

public class MovieInfo {


    /**
     * Code : 200
     * Msg : 首页数据获取成功
     * Data : [{"movClass":"经典高清","downLoadName":"与神同行","downLoadUrl":"magnet:?xt=urn:btih:IXDCVQPXI6UEWI5JQXI4OFFGK5CKSCVR&dn=%e4%b8%8e%e7%a5%9e%e5%90%8c%e8%a1%8c%ef%bc%9a%e7%bd%aa%e4%b8%8e%e7%bd%9a%2e720p%2e%e9%9f%a9%e7%b2%a4%e5%8f%8c%e8%af%ad%2eBD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e66ys%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337,magnet:?xt=urn:btih:OK522SCMBTYRR3CBXCYUVL2ZLITPHWLY&dn=YSTX%2e1080p%2eBD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e6vhao%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337,magnet:?xt=urn:btih:GNXN4SGYBSYACXNCUUGNR6IMBWPPYDR5&dn=YSTX%2e720p%2eBD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e6vhao%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337,magnet:?xt=urn:btih:LYV44BD7QO6KTF7HZ23ZPAUOVXIXHCJR&dn=%e4%b8%8e%e7%a5%9e%e5%90%8c%e8%a1%8c%2e1080p%2eHD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e6vhao%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337,ed2k://|file|%E4%B8%8E%E7%A5%9E%E5%90%8C%E8%A1%8C%EF%BC%9A%E7%BD%AA%E4%B8%8E%E7%BD%9A.720p.BD%E9%9F%A9%E7%B2%A4%E5%8F%8C%E8%AF%AD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mkv|1955919934|5ADCC5D2BBC53B4C3B90C192C21103C8|h=RK72PNOT44EV2I7DZEPMXOOYWHFHWDLC|/,ed2k://|file|%E4%B8%8E%E7%A5%9E%E5%90%8C%E8%A1%8C%EF%BC%9A%E7%BD%AA%E4%B8%8E%E7%BD%9A.720p.%E9%9F%A9%E7%B2%A4%E5%8F%8C%E8%AF%AD.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mp4|1858046878|AFCBDD107B58A2A6EDE6081B69EB9C67|h=UERHET57HSUM63SC3OAHRLXF4RD5X7G4|/,ed2k://|file|YSTX.1080p.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.6vhao.tv].mp4|3267957369|EB4245BEBD4DCE5F410FA03E09FA44E1|h=LMP72TD2KEQCOHK4NG2U6LWVGHCVUE3E|/,ed2k://|file|YSTX.720p.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.6vhao.tv].mp4|1764729200|3539C302EAC31BC88D46500DF29D1B25|h=QFQBTB7IDWCQD7OGMMEPUM2JKR4EXZDO|/,ed2k://|file|%E4%B8%8E%E7%A5%9E%E5%90%8C%E8%A1%8C.1080p.HD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.6vhao.tv].mp4|2199758616|8721B76073C4271D472988EF775A4862|h=H2LHPY3VTAKI3J3U6OSKL66OFNZYGN2C|/","mvdesc":"◎译　　名　与神同行/与神一起/与神同在/和神一起/与神同行：罪与罚\r\n◎片　　名　신과 함께\r\n◎年　　代　2017\r\n◎产　　地　\r\n◎类　　别　/\r\n◎语　　言　韩语\r\n◎字　　幕　中字\r\n◎上映日期　2017-12-20()\r\n◎IMDb评分  7.7/10 from 1,772 users\r\n◎豆瓣评分　8.1/10 from 3,287 users\r\n◎片　　长　139分钟\r\n◎导　　演　金容华 Yong-hwa Kim\r\n◎主　　演　河正宇 Jung-woo Ha\r\n　　　　　　车太贤 Tae-hyun Cha\r\n　　　　　　朱智勋 Ji-hun Ju\r\n　　　　　　金香奇 Hyang-ki Kim\r\n　　　　　　李政宰 Jung-Jae Lee\r\n　　　　　　金东旭 Dong-wook Kim\r\n　　　　　　马东锡 Tong-Seok Ma\r\n　　　　　　都暻秀 Kyung-soo Doh\r\n　　　　　　吴达洙 Dal-su Oh\r\n　　　　　　林元熙 Won-hie Lim\r\n　　　　　　张光 Gwang Jang\r\n　　　　　　郑海钧 Hae-kyun Jung\r\n　　　　　　金荷娜 Ha-Neul Kim\r\n　　　　　　金海淑 Hae-suk Kim◎简　　介　　影片根据漫画改编，讲述人死之后，在地狱使者的辩护下，接受49天审判的故事，以阴间影射人间丑态和人的命运前程。影片由金容华执导，上下两集。◎获奖情况　　第12届亚洲电影大奖  (2018)\r\n　　最佳美术指导(提名) 李莫元\r\n　　最佳视觉效果(提名) Jonghyun Jin","downimgurl":"https://tu.66vod.net/2017/8093.jpg,https://tu.66vod.net/2018/0228.jpg","mv_update_time":"2018-11-05","downdtitle":"与神同行.720p.韩粤双语.BD中字.mp4,与神同行.1080p.BD中字.mp4,与神同行.720p.BD中字.mp4,与神同行.1080p.HD中字.mp4,与神同行.720p.韩粤双语.BD中字.mkv,与神同行.720p.韩粤双语.BD中字.mp4,与神同行.1080p.BD中字.mp4,与神同行.720p.BD中字.mp4,与神同行.1080p.HD中字.mp4","id":"12283","mv_md5_id":"6262f8c36ea75492c38980dac83ecb87"}]
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
         * movClass : 经典高清
         * downLoadName : 与神同行
         * downLoadUrl : magnet:?xt=urn:btih:IXDCVQPXI6UEWI5JQXI4OFFGK5CKSCVR&dn=%e4%b8%8e%e7%a5%9e%e5%90%8c%e8%a1%8c%ef%bc%9a%e7%bd%aa%e4%b8%8e%e7%bd%9a%2e720p%2e%e9%9f%a9%e7%b2%a4%e5%8f%8c%e8%af%ad%2eBD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e66ys%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337,magnet:?xt=urn:btih:OK522SCMBTYRR3CBXCYUVL2ZLITPHWLY&dn=YSTX%2e1080p%2eBD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e6vhao%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337,magnet:?xt=urn:btih:GNXN4SGYBSYACXNCUUGNR6IMBWPPYDR5&dn=YSTX%2e720p%2eBD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e6vhao%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337,magnet:?xt=urn:btih:LYV44BD7QO6KTF7HZ23ZPAUOVXIXHCJR&dn=%e4%b8%8e%e7%a5%9e%e5%90%8c%e8%a1%8c%2e1080p%2eHD%e4%b8%ad%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e6vhao%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337,ed2k://|file|%E4%B8%8E%E7%A5%9E%E5%90%8C%E8%A1%8C%EF%BC%9A%E7%BD%AA%E4%B8%8E%E7%BD%9A.720p.BD%E9%9F%A9%E7%B2%A4%E5%8F%8C%E8%AF%AD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mkv|1955919934|5ADCC5D2BBC53B4C3B90C192C21103C8|h=RK72PNOT44EV2I7DZEPMXOOYWHFHWDLC|/,ed2k://|file|%E4%B8%8E%E7%A5%9E%E5%90%8C%E8%A1%8C%EF%BC%9A%E7%BD%AA%E4%B8%8E%E7%BD%9A.720p.%E9%9F%A9%E7%B2%A4%E5%8F%8C%E8%AF%AD.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mp4|1858046878|AFCBDD107B58A2A6EDE6081B69EB9C67|h=UERHET57HSUM63SC3OAHRLXF4RD5X7G4|/,ed2k://|file|YSTX.1080p.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.6vhao.tv].mp4|3267957369|EB4245BEBD4DCE5F410FA03E09FA44E1|h=LMP72TD2KEQCOHK4NG2U6LWVGHCVUE3E|/,ed2k://|file|YSTX.720p.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.6vhao.tv].mp4|1764729200|3539C302EAC31BC88D46500DF29D1B25|h=QFQBTB7IDWCQD7OGMMEPUM2JKR4EXZDO|/,ed2k://|file|%E4%B8%8E%E7%A5%9E%E5%90%8C%E8%A1%8C.1080p.HD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.6vhao.tv].mp4|2199758616|8721B76073C4271D472988EF775A4862|h=H2LHPY3VTAKI3J3U6OSKL66OFNZYGN2C|/
         * mvdesc : ◎译　　名　与神同行/与神一起/与神同在/和神一起/与神同行：罪与罚
         ◎片　　名　신과 함께
         ◎年　　代　2017
         ◎产　　地　
         ◎类　　别　/
         ◎语　　言　韩语
         ◎字　　幕　中字
         ◎上映日期　2017-12-20()
         ◎IMDb评分  7.7/10 from 1,772 users
         ◎豆瓣评分　8.1/10 from 3,287 users
         ◎片　　长　139分钟
         ◎导　　演　金容华 Yong-hwa Kim
         ◎主　　演　河正宇 Jung-woo Ha
         　　　　　　车太贤 Tae-hyun Cha
         　　　　　　朱智勋 Ji-hun Ju
         　　　　　　金香奇 Hyang-ki Kim
         　　　　　　李政宰 Jung-Jae Lee
         　　　　　　金东旭 Dong-wook Kim
         　　　　　　马东锡 Tong-Seok Ma
         　　　　　　都暻秀 Kyung-soo Doh
         　　　　　　吴达洙 Dal-su Oh
         　　　　　　林元熙 Won-hie Lim
         　　　　　　张光 Gwang Jang
         　　　　　　郑海钧 Hae-kyun Jung
         　　　　　　金荷娜 Ha-Neul Kim
         　　　　　　金海淑 Hae-suk Kim◎简　　介　　影片根据漫画改编，讲述人死之后，在地狱使者的辩护下，接受49天审判的故事，以阴间影射人间丑态和人的命运前程。影片由金容华执导，上下两集。◎获奖情况　　第12届亚洲电影大奖  (2018)
         　　最佳美术指导(提名) 李莫元
         　　最佳视觉效果(提名) Jonghyun Jin
         * downimgurl : https://tu.66vod.net/2017/8093.jpg,https://tu.66vod.net/2018/0228.jpg
         * mv_update_time : 2018-11-05
         * downdtitle : 与神同行.720p.韩粤双语.BD中字.mp4,与神同行.1080p.BD中字.mp4,与神同行.720p.BD中字.mp4,与神同行.1080p.HD中字.mp4,与神同行.720p.韩粤双语.BD中字.mkv,与神同行.720p.韩粤双语.BD中字.mp4,与神同行.1080p.BD中字.mp4,与神同行.720p.BD中字.mp4,与神同行.1080p.HD中字.mp4
         * id : 12283
         * mv_md5_id : 6262f8c36ea75492c38980dac83ecb87
         */

        private String movClass;
        private String downLoadName;
        private String downLoadUrl;
        private String mvdesc;
        private String downimgurl;
        private String mv_update_time;
        private String downdtitle;
        private String id;
        private String mv_md5_id;
        private String urlMd5;
        private String progress;

        public String getProgress() {
            return progress;
        }

        public void setProgress(String progress) {
            this.progress = progress;
        }

        public String getUrlMd5() {
            return urlMd5;
        }

        public void setUrlMd5(String urlMd5) {
            this.urlMd5 = urlMd5;
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

        public String getMv_update_time() {
            return mv_update_time;
        }

        public void setMv_update_time(String mv_update_time) {
            this.mv_update_time = mv_update_time;
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

        public String getMv_md5_id() {
            return mv_md5_id;
        }

        public void setMv_md5_id(String mv_md5_id) {
            this.mv_md5_id = mv_md5_id;
        }
    }
}
