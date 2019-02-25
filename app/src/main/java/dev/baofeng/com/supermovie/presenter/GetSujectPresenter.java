package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.presenter.iview.ISubjectView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by huangyong on 2018/1/26.
 */

public class GetSujectPresenter extends BasePresenter<ISubjectView>{
    private Context context;
    private IMoview iMoview;

    public GetSujectPresenter(Context context, ISubjectView iview) {
        super(context, iview);
    }

    public void getSubject(int page, int pagesize, String type){


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getSubject(type,page,pagesize), new BaseApi.IResponseListener<SubjectInfo>() {
                    @Override
                    public void onSuccess(SubjectInfo data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                        iview.loadError("请求失败");
                    }
                }


        );

    }
    public void getSubjectTitle(int page, int pagesize){


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getSubjectTitle(page,pagesize), new BaseApi.IResponseListener<SubjectTitleInfo>() {
                    @Override
                    public void onSuccess(SubjectTitleInfo data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                        iview.loadError("请求失败");
                    }
                }
        );

    }

    @Override
    public void release() {
        unSubcription();
    }

    public void getMoreData(int page,int pagesize,String type) {

        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getSubject(type,page,pagesize), new BaseApi.IResponseListener<SubjectInfo>() {
                    @Override
                    public void onSuccess(SubjectInfo data) {
                        iview.loadData(data);
                    }

                    @Override
                    public void onFail() {
                        iview.loadError("请求失败");
                    }
                }
        );

    }
    public void getMoreTitleData(int page,int pagesize) {


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getSubjectTitle(page,pagesize), new BaseApi.IResponseListener<SubjectTitleInfo>() {
                    @Override
                    public void onSuccess(SubjectTitleInfo data) {
                        iview.loadMore(data);
                    }

                    @Override
                    public void onFail() {
                        iview.loadError("请求失败");
                    }
                }
        );
    }

}
