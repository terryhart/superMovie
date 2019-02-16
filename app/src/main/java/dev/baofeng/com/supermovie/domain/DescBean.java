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
     * header : ["别名：Black Snow","导演：谢飞","主演：蔡鸿翔,程琳,姜文,李耕","类型：剧情片 剧情","地区：大陆","语言：国语","上映：1990","片长：108","更新：2019-02-06 17:59:01","总播放量：","今日播放量：0","总评分数：374","评分次数：45"]
     * desc : 经过劳动改造，犯人李慧泉（姜文 饰）刑满释放，他回到了从小生长的胡同。老街坊邻居罗大妈很照顾这个父母亡故、孤苦伶仃的小伙子。当年，哥们叉子因为女友爱上了别人，气愤不过，拉着讲义气的李慧泉，教训了那个男人，结果无意中弄出了人命，二人双双入狱。如今，叉子的家人对他们依旧抱有成见，只有叉子的弟弟觉得李慧泉是条汉子。李慧泉在民警小刘（刘 饰），后来选择了练摊谋生，期间遇到了三教九流令他尝遍人间冷暖。在歌厅里，李慧泉认识了驻唱的歌手赵雅秋（程琳 饰），此后他成为她的护花使者，但是在他决定重新开始新生活的时候，却陷入了始料未及的困境……本片根据刘恒的小说《黑的雪》改编，获得1990年柏林电影节银熊奖。
     */

    private String desc;
    private List<String> header;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getHeader() {
        return header;
    }

    public void setHeader(List<String> header) {
        this.header = header;
    }
}
