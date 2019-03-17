package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import com.example.aop.Function;
import com.example.aop.Statistics;

import java.util.ArrayList;
import java.util.List;

import dev.baofeng.com.supermovie.db.dao.DbHelper;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.ISview;
import dev.baofeng.com.supermovie.presenter.iview.Isearch;

/**
 * Created by huangyong on 2018/2/23.
 */

public class SearchPresenter extends BasePresenter<Isearch> {


    public SearchPresenter(Context context, Isearch iview) {
        super(context, iview);
    }

    @Override
    public void release() {
        unSubcription();
    }
    /**
     * 打点，搜索计数
     */
    @Statistics(function = Function.SEARCH)
    public void search(String keywords, int page, int size) {

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getSearch(keywords,page, size), new BaseApi.IResponseListener<MovieInfo>() {
                    @Override
                    public void onSuccess(MovieInfo data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }


        );

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
        return DbHelper.getAllHistory();
    }

    public void addKeyWordsTodb(String keyword) {
        DbHelper.addKeywords(keyword);
    }

    public void clearSearchHistory() {
        DbHelper.clearKeywords();
    }
}
