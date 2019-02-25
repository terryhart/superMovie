package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.OnlinePlayInfo;
import dev.baofeng.com.supermovie.domain.PlayUrlBean;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by huangyong on 2018/1/26.
 */

public class GetRecpresenter extends BasePresenter<IMoview>{

    public GetRecpresenter(Context context, IMoview iview) {
        super(context, iview);
    }

    public void getRecentUpdate(int page, int pagesize){


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getRecomend(page, pagesize), new BaseApi.IResponseListener<RecentUpdate>() {
                    @Override
                    public void onSuccess(RecentUpdate data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                        iview.loadError("请求失败");
                    }
                }


        );


    }

    public void getBtRecommend(int page,int pagesize){

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getRecomend(page, pagesize), new BaseApi.IResponseListener<RecentUpdate>() {
                    @Override
                    public void onSuccess(RecentUpdate data) {
                        iview.loadBtData(data);
                    }

                    @Override
                    public void onFail() {
                        iview.loadError("请求失败");
                    }
                }
        );
    }

    @Override
    public void release() {
        unSubcription();
    }

}
