package dev.baofeng.com.supermovie.http;

import dev.baofeng.com.supermovie.domain.MovieInfo;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Huangyong on 2017/10/24.
 */

public interface ApiService {

  @GET(UrlConfig.GETRECOMEND)
  Observable<MovieInfo> getRecomend(@Query("type") String type,@Query("page") int page,@Query("pagesize") int pagesize);//获取推荐

  //获取分类
  @GET(UrlConfig.GETRECOMEND)
  Observable<MovieInfo> getCategory(@Query("type") String type);

 /* @FormUrlEncoded
  @POST(UrlConfig.QUERY_COLLECT)
  Observable<MovieInfo> QueryPageCollect(@Field("uid") String uid, @Field("num[]") List<String> num);*/

}
