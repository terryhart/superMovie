package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.text.TextUtils;


import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.IRandom;

/**
 * Created by huangyong on 2018/1/26.
 */

public class GetRandomRecpresenter extends BasePresenter<IRandom> {

    public GetRandomRecpresenter(Context context, IRandom iview) {
        super(context, iview);
    }


    public void getMovieRecommend(String type) {

        if (TextUtils.isEmpty(type)) {
            return;
        }
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getBtRandomRecomend(type), new BaseApi.IResponseListener<OnlinePlayInfo>() {
                    @Override
                    public void onSuccess(OnlinePlayInfo data) {
                        iview.loadRandomData(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );

    }

    @Override
    public void release() {
        unSubcription();
    }


    public void getSeriRecommend(String type) {
        if (TextUtils.isEmpty(type)) {
            return;
        }

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getSeriRandomRecomend(type), new BaseApi.IResponseListener<OnlinePlayInfo>() {
                    @Override
                    public void onSuccess(OnlinePlayInfo data) {
                        iview.loadRandomData(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );

    }
}
