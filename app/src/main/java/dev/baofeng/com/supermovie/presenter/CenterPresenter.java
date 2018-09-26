package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.util.Log;


import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.presenter.iview.IAllView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CenterPresenter extends BasePresenter<IAllView> {

    public CenterPresenter(Context context, IAllView iview) {
        super(context, iview);
    }

    @Override
    public void release() {
        unSubcription();
    }


    public void getLibraryDdata(String type,int page,int pagesize){

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getLibraryDatas(type,page,pagesize)
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
                        iview.loadSuccess(result);
                    }
                });
        addSubscription(subscription);
    }
    public void getLibraryMoreDdata(String type,int page,int pagesize){

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getLibraryDatas(type,page,pagesize)
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

}
