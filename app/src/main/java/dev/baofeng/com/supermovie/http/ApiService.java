package dev.baofeng.com.supermovie.http;

import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Completable;
import rx.Observable;

/**
 * Created by Huangyong on 2017/10/24.
 */

public interface ApiService {

  @GET(UrlConfig.GETRECOMEND)
  Observable<RecentUpdate> getRecomend(@Query("page") int page, @Query("pagesize") int pagesize);//获取推荐

  @GET(UrlConfig.GET_SUBJECT)
  Observable<SubjectInfo> getSubject(@Query("type") String typeKey,@Query("page") int page, @Query("pagesize") int pagesize);//获取专题列表

  @GET(UrlConfig.GET_ONLINE)
  Observable<OnlinePlayInfo> getOnlineMovie(@Query("type") String type, @Query("page") int page, @Query("pagesize") int pagesize);//获取专题列表

    @GET(UrlConfig.GET_ONLINE_Seris)
    Observable<OnlinePlayInfo> getOnlineSeris(@Query("type") String type, @Query("page") int page, @Query("pagesize") int pagesize);//获取专题列表

  @GET(UrlConfig.GET_SUBJECT_TITLE)
  Observable<SubjectTitleInfo> getSubjectTitle(@Query("page") int page, @Query("pagesize") int pagesize);//获取专题列表

  @GET(UrlConfig.GETBTRECOMEND)
  Observable<RecentUpdate> getBtRecomend(@Query("page") int page,@Query("pagesize") int pagesize);//获取推荐

  @GET(UrlConfig.GETBTRECOMEND)
  Observable<RecentUpdate> getLibraryDatas(@Query("type") String typeKey,@Query("page") int page,@Query("pagesize") int pagesize);//获取推荐

  @GET(UrlConfig.GETSEARCH)
  Observable<MovieInfo> getSearch(@Query("key") String key);//获取搜索

  @GET(UrlConfig.GET_ONLINE_SEARCH_MOVIE)
  Observable<OnlinePlayInfo> getOnlineSearch(@Query("key") String key);//获取搜索

  @GET(UrlConfig.GET_ONLINE_SEARCH_SERIS)
  Observable<OnlinePlayInfo> getOnlineSearchSeris(@Query("key") String key);//获取搜索
  //获取详情
  @GET(UrlConfig.GETDETAIL)
  Observable<BtInfo> getBtDetail(@Query("title") String title);


  @GET(UrlConfig.CHECK_UPDATE)
  Observable<AppUpdateInfo> getAppUpdate();


  @GET(UrlConfig.GET_SERIS)
  Observable<RecentUpdate> getSerisUpdate(@Query("page")int page, @Query("pagesize")int pagesize);


  @GET(UrlConfig.GET_SHARE)
  Observable<RecentUpdate> getShare(@Query("md5id") String mvId);

  @GET(UrlConfig.GET_RANDOM_REOMMEND)
  Observable<OnlinePlayInfo> getBtRandomRecomend(@Query("type") String type);//获取推荐

  @GET(UrlConfig.GET_RANDOM_SERI_RECMMEND)
  Observable<OnlinePlayInfo> getSeriRandomRecomend(@Query("type") String type);//获取推荐
}
