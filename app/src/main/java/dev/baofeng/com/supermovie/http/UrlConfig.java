package dev.baofeng.com.supermovie.http;

/**
 *
 * Created by Huangyong on 2017/9/12.
 */

public class UrlConfig {
    public static String UUIDA = null;
    public static String BASE_URL = "http://dy.songore.cn/";
    //版本更新
    public static final String CHECK_UPDATE  = "ygcms/app/update.json" ;

    public static final String DOWNLOADBASEURL  = "https://www.bttt.la/" ;
//    public static final String CHECK_UPDATE  = "update.json" ;

    //获取首页标签
    public static final String GET_HOME_INFO ="text/movie_title.php";

    public static final String GET_DETAIL = "new/";

    public static final String GET_SEARCH_VIDEO = "ygcms/getSearch.php";

    //获取最新更新的视频
    public static final String GETRECOMEND = "ygcms/getUpdate.php";
    //获取详情
    public static final String GETDETAIL = "getMore.php";
    //获取轮播推荐视频
    public static final String GETBTRECOMEND = "ygcms/getClassVideo.php";

    //获取搜索结果
    public static final String GETSEARCH = "ygcms/getSearch.php";
//    public static final String GET_VIDEO_THREE = "home.json";


}
