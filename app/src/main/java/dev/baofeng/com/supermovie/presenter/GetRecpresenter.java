package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.OnlinePlayInfo;
import dev.baofeng.com.supermovie.domain.PlayUrlBean;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
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

    public void getRecentUpdate(int page, int pagesize){

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getRecomend(page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecentUpdate>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iview.loadError("");
                    }

                    @Override
                    public void onNext(RecentUpdate result) {
                        iview.loadData(result);
                    }
                });
        addSubscription(subscription);
    }

    public void getBtRecommend(int page,int pagesize){

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getBtRecomend(page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecentUpdate>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iview.loadError("");
                    }
                    @Override
                    public void onNext(RecentUpdate result) {
                        iview.loadBtData(result);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void release() {
        unSubcription();
    }

    public void getMoreData(int page,int pagesize) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getRecomend(page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RecentUpdate>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(RecentUpdate result) {
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
