package dev.baofeng.com.supermovie.http;

/**
 *
 * Created by Huangyong on 2017/9/12.
 */

public class UrlConfig {
    public static String UUIDA = null;
    public static String BASE_URL = "http://dy.songore.cn/";
    //版本更新
    public static final String CHECK_UPDATE  = "update/update2.php" ;
    public static final String DOWNLOADBASEURL  = "https://www.bttt.la/" ;
//    public static final String CHECK_UPDATE  = "update.json" ;

    //获取首页标签
    public static final String GET_HOME_INFO ="text/movie_title.php";

    public static final String GET_DETAIL = "new/";
    //搜索视频标签http://zhijue.baofeng.com/text/movie_1.php
    public static final String GET_SEARCH_VIDEO = "text/select_movie.php";

    //获取推荐视频
    public static final String GETRECOMEND = "getData.php";
    //获取详情
    public static final String GETDETAIL = "getMore.php";
    //获取BT推荐视频
    public static final String GETBTRECOMEND = "getbt.php";
//    public static final String GET_VIDEO_THREE = "home.json";


}
