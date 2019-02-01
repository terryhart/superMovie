package dev.baofeng.com.supermovie.presenter.online;

import android.content.Context;


import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.presenter.BasePresenter;
import dev.baofeng.com.supermovie.presenter.online.iview.IOnlineView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangyong on 2018/1/26.
 */

public class GetOnlinePresenter extends BasePresenter<IOnlineView> {

    public GetOnlinePresenter(Context context, IOnlineView iview) {
        super(context, iview);
    }

    /**
     * 获取电影列表
     *
     * @param pageType
     * @param page
     * @param pagesize
     */
    public void getOnlineMvData(String pageType, int page, int pagesize) {

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getOnlineMovie(pageType, page, pagesize)
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

    public void getMovieMoreData(String pageType, int page, int pagesize) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getOnlineMovie(pageType, page, pagesize)
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

    /**
     * 获取电视剧列表
     *
     * @param pageType
     * @param page
     * @param pagesize
     */
    public void getOnlineSerisData(String pageType, int page, int pagesize) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getOnlineSeris(pageType, page, pagesize)
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

    public void getSerisMoreData(String pageType, int page, int pagesize) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getOnlineSeris(pageType, page, pagesize)
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
