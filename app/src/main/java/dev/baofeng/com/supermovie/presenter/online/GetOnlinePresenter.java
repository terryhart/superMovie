package dev.baofeng.com.supermovie.presenter.online;

import android.content.Context;


import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.BasePresenter;
import dev.baofeng.com.supermovie.presenter.online.iview.IOnlineView;

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

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getOnlineMovie(pageType, page, pagesize), new BaseApi.IResponseListener<OnlinePlayInfo>() {
                    @Override
                    public void onSuccess(OnlinePlayInfo data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );

    }

    @Override
    public void release() {
        unSubcription();
    }

    public void getMovieMoreData(String pageType, int page, int pagesize) {


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getOnlineMovie(pageType, page, pagesize), new BaseApi.IResponseListener<OnlinePlayInfo>() {
                    @Override
                    public void onSuccess(OnlinePlayInfo data) {
                        iview.loadMore(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }

    /**
     * 获取电视剧列表
     *
     * @param pageType
     * @param page
     * @param pagesize
     */
    public void getOnlineSerisData(String pageType, int page, int pagesize) {
        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getOnlineSeris(pageType, page, pagesize), new BaseApi.IResponseListener<OnlinePlayInfo>() {
                    @Override
                    public void onSuccess(OnlinePlayInfo data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );

    }

    public void getSerisMoreData(String pageType, int page, int pagesize) {

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getOnlineSeris(pageType, page, pagesize), new BaseApi.IResponseListener<OnlinePlayInfo>() {
                    @Override
                    public void onSuccess(OnlinePlayInfo data) {
                        iview.loadMore(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }
}
