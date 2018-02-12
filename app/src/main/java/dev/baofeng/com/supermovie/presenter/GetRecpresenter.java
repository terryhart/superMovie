package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.util.Log;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangyong on 2018/1/26.
 */

public class GetRecpresenter extends BasePresenter<IMoview>{
    private Context context;
    private IMoview iMoview;

    public GetRecpresenter(Context context, IMoview iview) {
        super(context, iview);
    }

    public void getRecommend(String type ,int page,int pagesize){

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getRecomend(type,page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(MovieInfo result) {
                        iview.loadData(result);
                    }
                });
        addSubscription(subscription);
    }
    public void getBtRecommend(String type ,int page,int pagesize){

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getBtRecomend(type,page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(MovieInfo result) {
                        iview.loadBtData(result);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void release() {
        unSubcription();
    }

    public void getMoreData(String type,int page,int pagesize) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getRecomend(type,page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(MovieInfo result) {
                        iview.loadMore(result);
                    }
                });
        addSubscription(subscription);
    }
    public void getBtMoreData(String type,int page,int pagesize) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getBtRecomend(type,page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(MovieInfo result) {
                        iview.loadMore(result);
                    }
                });
        addSubscription(subscription);
    }

    public void getBtDetail(String title) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getBtDetail(title)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BtInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(BtInfo result) {
                        iview.loadDetail(result);
                    }
                });
        addSubscription(subscription);
    }
}
