package dev.baofeng.com.supermovie.http;

/**
 *
 * Created by Huangyong on 2017/9/12.
 */

public class UrlConfig {

    public static String UUIDA = null;
    public static String BASE_URL = "http://123.207.150.253/";
    //版本更新
    public static final String CHECK_UPDATE  = "ygcms/app/update.json" ;
    //关于我们
    public static final String ABOUT_US = BASE_URL+"ygcms/app/about.html";

    public static final String ONLINE_MV = BASE_URL+"ygcms/app/online.html";
    public static final String TV_CHINNALE = BASE_URL+"ygcms/app/tv_online.html";

    public static final String GET_SHARE = "ygcms/getKeyShare.php";
    //获取专题
    public static final String GET_SUBJECT  = "ygcms/getSubject.php" ;

    public static final String GET_ONLINE = "ygcms/getLineMovie.php";

    public static final String GET_ONLINE_Seris = "ygcms/getLineSeris.php";
    //获取电视剧
    public static final String GET_SERIS = "ygcms/getSeris.php";

    //获取专题
    public static final String GET_SUBJECT_TITLE  = "ygcms/getSubjectTitle.php" ;

    public static final String DOWNLOADBASEURL  = "https://www.bttt.la/" ;
//    public static final String CHECK_UPDATE  = "update.json" ;

    //获取最新更新的视频
    public static final String GETRECOMEND = "ygcms/getUpdate.php";
    //获取详情
    public static final String GETDETAIL = "getMore.php";
    //获取轮播推荐视频
    public static final String GETBTRECOMEND = "ygcms/getClassVideo.php";

    //获取搜索结果
     public static final String GETSEARCH = "ygcms/getSearch.php";
//    public static final String GET_VIDEO_THREE = "home.json";

    public static final String GET_ONLINE_SEARCH_MOVIE = "ygcms/getOnlineMvSearch.php";

    public static final String GET_ONLINE_SEARCH_SERIS = "ygcms/getOnlineSerisSearch.php";
    //猜你喜欢,随机12个 需要跟参数type
    public static final String GET_RANDOM_REOMMEND = "ygcms/getLineMovieRandom.php";

    public static final String GET_RANDOM_SERI_RECMMEND = "ygcms/getLineSeriRandom.php";


    public static String[] mvArr = {
            "科幻",
            "喜剧",
            "爱情",
            "战争",
            "剧情",
            "恐怖",
            "综艺",

            "美剧",
            "动漫",
            "国产剧",
            "港剧",
            "台湾剧",
            "日剧",
            "韩剧",
            "微电影",
            "海外剧"
    };
    public static String[] mvType = {"science", "comedy", "love", "war", "story", "terror", "show"
            , "america", "curtoon", "native", "hongkong", "taiwan", "japanise",
            "koria", "shortmv", "ocean"
    };

}
