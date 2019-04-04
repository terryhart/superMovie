package dev.baofeng.com.supermovie.presenter;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Callable;

import dev.baofeng.com.supermovie.db.dao.DbHelper;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.IOnlineSearch;
import dev.baofeng.com.supermovie.presenter.iview.Isearch;
import io.reactivex.disposables.Disposable;

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

        OnlinePlayInfo info = new OnlinePlayInfo();
        ArrayList<OnlinePlayInfo.DataBean> dataBeans = new ArrayList<>();
        info.setData(dataBeans);

        io.reactivex.Observable<OnlinePlayInfo> onlineSearch = BaseApi.createApi(ApiService.class)
                .getOnlineSearch(keywords);

        io.reactivex.Observable<OnlinePlayInfo> onlineSearchSeris = BaseApi.createApi(ApiService.class)
                .getOnlineSearchSeris(keywords);

        io.reactivex.Observable<OnlinePlayInfo> infoObservable = io.reactivex.Observable.merge(onlineSearch, onlineSearchSeris);

        BaseApi.request(infoObservable, new BaseApi.IResponseListener<OnlinePlayInfo>() {
                    @Override
                    public void onSuccess(OnlinePlayInfo result) {
                        dataBeans.addAll(result.getData());
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );

        infoObservable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<OnlinePlayInfo>() {

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   iview.loadFail();
                               }

                               @Override
                               public void onComplete() {
                                   iview.loadData(info);
                               }

                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(OnlinePlayInfo result) {
                                   dataBeans.addAll(result.getData());
                               }
                           }
                );
    }


    public boolean keywordsExist(String keyword) {
        return DbHelper.checkOnlineKeyWords(keyword);
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
