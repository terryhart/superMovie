package dev.baofeng.com.supermovie.domain;

import java.util.List;

/**
 * creator huangyong
 * createTime 2019/1/6 下午7:00
 * path com.zmovie.app.domain
 * description:
 */
public class DescBean {


    /**
     * header_key : ["影片名称：","影片备注：","影片演员：","影片导演：","影片类型：","影片地区：","更新时间：","影片状态：","影片语言：","上映日期："]
     * header_value : ["绝望的主妇第二季","第24集大结局","玛西亚·克劳斯,,菲丽西提·霍夫曼,,泰瑞·海切尔","","欧美剧","美国","2018/12/27 19:02:46","连载第24集","","2005"]
     * desc :
     */

    private String desc;
    private List<String> header_key;
    private List<String> header_value;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getHeader_key() {
        return header_key;
    }

    public void setHeader_key(List<String> header_key) {
        this.header_key = header_key;
    }

    public List<String> getHeader_value() {
        return header_value;
    }

    public void setHeader_value(List<String> header_value) {
        this.header_value = header_value;
    }
}
