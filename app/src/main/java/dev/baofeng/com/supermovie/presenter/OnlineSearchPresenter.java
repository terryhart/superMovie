package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Callable;

import dev.baofeng.com.supermovie.db.dao.DbHelper;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.presenter.iview.IOnlineSearch;
import dev.baofeng.com.supermovie.presenter.iview.Isearch;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huangyong on 2018/2/23.
 */

public class OnlineSearchPresenter extends BasePresenter<IOnlineSearch> {


    public OnlineSearchPresenter(Context context, IOnlineSearch iview) {
        super(context, iview);
    }

    @Override
    public void release() {
        unSubcription();
    }

    /**
     * 搜索电影
     */
    public void searchOnline(String keywords) {

        rx.Observable<OnlinePlayInfo> serisObservable = ApiManager.getRetrofitInstance().getOnlineSearchSeris(keywords);
        rx.Observable<OnlinePlayInfo> movieObservable = ApiManager.getRetrofitInstance().getOnlineSearch(keywords);

        rx.Observable<OnlinePlayInfo> resultObservalble = rx.Observable.merge(serisObservable, movieObservable);
        OnlinePlayInfo info = new OnlinePlayInfo();
        ArrayList<OnlinePlayInfo.DataBean> dataBeans = new ArrayList<>();
        info.setData(dataBeans);
        Subscription subscription = resultObservalble.
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OnlinePlayInfo>() {
                    @Override
                    public void onCompleted() {
                        iview.loadData(info);
                    }

                    @Override
                    public void onError(Throwable e) {
                        iview.loadFail();
                    }

                    @Override
                    public void onNext(OnlinePlayInfo result) {
                        dataBeans.addAll(result.getData());
                    }
                });

        addSubscription(subscription);
    }


    public boolean keywordsExist(String keyword) {
        return DbHelper.checkKeyWords(keyword);
    }

    /**
     * 查询数据库，保存的搜索记录
     *
     * @return
     */
    public ArrayList getSearchHistory() {
        return DbHelper.getAllOnlineHistory();
    }

    public void addKeyWordsTodb(String keyword) {
        DbHelper.addOnlineKeywords(keyword);
    }

    public void clearSearchHistory() {
        DbHelper.clearOnlineKeywords();
    }
}
