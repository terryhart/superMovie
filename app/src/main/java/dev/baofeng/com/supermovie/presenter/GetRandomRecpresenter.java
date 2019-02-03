package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.text.TextUtils;


import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.presenter.iview.IRandom;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getBtRandomRecomend(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OnlinePlayInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OnlinePlayInfo result) {
                        iview.loadRandomData(result);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void release() {
        unSubcription();
    }


    public void getSeriRecommend(String type) {
        if (TextUtils.isEmpty(type)) {
            return;
        }
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getSeriRandomRecomend(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OnlinePlayInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OnlinePlayInfo result) {
                        iview.loadRandomData(result);
                    }
                });
        addSubscription(subscription);
    }
}
