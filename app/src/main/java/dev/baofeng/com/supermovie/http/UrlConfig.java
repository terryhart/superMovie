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
//    public static final String CHECK_UPDATE  = "update.json" ;

    //获取首页标签
    public static final String GET_HOME_INFO ="text/movie_title.php";

    public static final String GET_DETAIL = "new/";
    //搜索视频标签http://zhijue.baofeng.com/text/movie_1.php
    public static final String GET_SEARCH_VIDEO = "text/select_movie.php";

    //获取推荐视频
    public static final String GETRECOMEND = "getData.php";
//    public static final String GET_VIDEO_THREE = "home.json";

    //获取消息
    public static final String GET_MESSAGE = "text/notice.php";

    //提交用户反馈
    public static final String GET_FEEDBACK ="text/feedback.php";

    public static final String GET_NEWS = "text/news.php";

    public static final String GET_DETAILS = "text/";

    public static final String PAGEURL="PAGEURL";
    //https://zhijue.baofeng.com/new/ios_yes.php
    public static final String QUERYLIST_COLLECT="new/select_user.php";
//    public static final String QUERYLIST_COLLECT="home.json";

    public static final String UPDATE_COLLECT="new/update_user.php";
//    public static final String UPDATE_COLLECT="check.php";

    public static final String QUERY_COLLECT="new/ios_yes.php";
}
