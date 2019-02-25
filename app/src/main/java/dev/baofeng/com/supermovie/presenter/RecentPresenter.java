package dev.baofeng.com.supermovie.presenter;

import android.content.Context;


import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.IRecentView;

/**
 * Created by huangyong on 2018/1/26.
 */

public class RecentPresenter extends BasePresenter<IRecentView>{

    public RecentPresenter(Context context, IRecentView iview) {
        super(context, iview);
    }


    public void getSerisUpdate(int page, int pagesize){


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getSerisUpdate(page, pagesize), new BaseApi.IResponseListener<RecentUpdate>() {
                    @Override
                    public void onSuccess(RecentUpdate data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }


        );

    }
    public void getSerisMore(int page,int pagesize) {

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getSerisUpdate(page, pagesize), new BaseApi.IResponseListener<RecentUpdate>() {
                    @Override
                    public void onSuccess(RecentUpdate data) {
                        iview.loadMore(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }


        );

    }

    public void getMovieUpdate(int page, int pagesize){

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getRecomend(page, pagesize), new BaseApi.IResponseListener<RecentUpdate>() {
                    @Override
                    public void onSuccess(RecentUpdate data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }


        );


    }

    public void getMovieMore(int page, int pagesize) {


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getRecomend(page, pagesize), new BaseApi.IResponseListener<RecentUpdate>() {
                    @Override
                    public void onSuccess(RecentUpdate data) {
                        iview.loadMore(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }


        );

    }

    @Override
    public void release() {
    }

}
