package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.DoubanTop250;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.IDBTop250;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;

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


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getDoubanTop250(page, count), new BaseApi.IResponseListener<DoubanTop250>() {
                    @Override
                    public void onSuccess(DoubanTop250 data) {
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



}
