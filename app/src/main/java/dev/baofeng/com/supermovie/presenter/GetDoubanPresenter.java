package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.DoubanTop250;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.presenter.iview.IDBTop250;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangyong on 2018/1/26.
 */

public class GetDoubanPresenter extends BasePresenter<IDBTop250> {
    private Context context;
    private IMoview iMoview;

    public GetDoubanPresenter(Context context, IDBTop250 iview) {
        super(context, iview);
    }

    public void getTop250(int page, int count) {

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getDoubanTop250(page, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoubanTop250>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        iview.loadError("");
                    }

                    @Override
                    public void onNext(DoubanTop250 result) {
                        iview.loadData(result);
                    }
                });
        addSubscription(subscription);
    }


    @Override
    public void release() {
        unSubcription();
    }

    public void getMoreData(int page, int count) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getDoubanTop250(page, count)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoubanTop250>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DoubanTop250 result) {
                        iview.loadMore(result);
                    }
                });
        addSubscription(subscription);
    }


}
