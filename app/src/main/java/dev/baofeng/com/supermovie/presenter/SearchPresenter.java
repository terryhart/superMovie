package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.presenter.iview.ISview;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangyong on 2018/2/23.
 */

public class SearchPresenter extends BasePresenter<ISview> {


    public SearchPresenter(Context context, ISview iview) {
        super(context, iview);
    }

    @Override
    public void release() {
        unSubcription();
    }

    public void search(String keywords){
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getSearch(keywords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iview.onNoData();
                    }
                    @Override
                    public void onNext(MovieInfo result) {
                        if (result.getCode()==200){
                            iview.onResult(result);
                        }else {

                        }

                    }
                });
        addSubscription(subscription);
    }
}
