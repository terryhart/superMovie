package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import dev.baofeng.com.supermovie.domain.OnlineInfo;
import dev.baofeng.com.supermovie.domain.OnlinePlayInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.presenter.iview.IOnlineView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangyong on 2018/1/26.
 */

public class GetOnlinePresenter extends BasePresenter<IOnlineView>{

    public GetOnlinePresenter(Context context, IOnlineView iview) {
        super(context, iview);
    }

    public void getOnlineData(int page, int pagesize){

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getOnlineMovie("native", page, pagesize)
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
                        iview.loadData(result);
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
                .getOnlineMovie("native", page, pagesize)
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
                        iview.loadMore(result);
                    }
                });
        addSubscription(subscription);
    }

}
