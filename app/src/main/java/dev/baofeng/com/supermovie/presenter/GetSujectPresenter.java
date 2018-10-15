package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
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

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getSubject(type,page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubjectInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(SubjectInfo result) {
                        iview.loadData(result);
                    }
                });
        addSubscription(subscription);
    }
    public void getSubjectTitle(int page, int pagesize){

        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getSubjectTitle(page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubjectTitleInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(SubjectTitleInfo result) {
                        iview.loadData(result);
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void release() {
        unSubcription();
    }

    public void getMoreData(int page,int pagesize,String type) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getSubject(type,page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubjectInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(SubjectInfo result) {
                        iview.loadMore(result);
                    }
                });
        addSubscription(subscription);
    }
    public void getMoreTitleData(int page,int pagesize) {
        Subscription subscription = ApiManager
                .getRetrofitInstance()
                .getSubjectTitle(page,pagesize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SubjectTitleInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                    @Override
                    public void onNext(SubjectTitleInfo result) {
                        iview.loadMore(result);
                    }
                });
        addSubscription(subscription);
    }

}
