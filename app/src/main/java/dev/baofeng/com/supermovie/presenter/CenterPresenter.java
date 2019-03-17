package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.IAllView;

public class CenterPresenter extends BasePresenter<IAllView> {


    public CenterPresenter(Context context, IAllView iview) {
        super(context, iview);

    }

    @Override
    public void release() {
        unSubcription();
    }


    public void getLibraryDdata(String type,int page,int pagesize){

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getLibraryDatas(type,page,pagesize), new BaseApi.IResponseListener<RecentUpdate>() {
                    @Override
                    public void onSuccess(RecentUpdate data) {
                        iview.loadSuccess(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }
    public void getLibraryMoreDdata(String type,int page,int pagesize){


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getLibraryDatas(type,page,pagesize), new BaseApi.IResponseListener<RecentUpdate>() {
                    @Override
                    public void onSuccess(RecentUpdate data) {
                        iview.loadMore(data);
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );
    }

}
