package dev.baofeng.com.supermovie.http;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Completable;
import rx.Observable;

/**
 * Created by Huangyong on 2017/10/24.
 */

public interface ApiService {

  @GET(UrlConfig.GETRECOMEND)
  Observable<RecentUpdate> getRecomend(@Query("page") int page, @Query("pagesize") int pagesize);//获取推荐

  @GET(UrlConfig.GETBTRECOMEND)
  Observable<RecentUpdate> getBtRecomend(@Query("page") int page,@Query("pagesize") int pagesize);//获取推荐

  @GET(UrlConfig.GETSEARCH)
  Observable<MovieInfo> getSearch(@Query("key") String key);//获取搜索

  //获取详情
  @GET(UrlConfig.GETDETAIL)
  Observable<BtInfo> getBtDetail(@Query("title") String title);

  @FormUrlEncoded
  @POST(UrlConfig.DOWNLOADBASEURL+"download3.php")
  Observable<Object> getTorrentFile(@Field("id") int id,@Field("action") String action,@Field("uhash") String uhush);

}
