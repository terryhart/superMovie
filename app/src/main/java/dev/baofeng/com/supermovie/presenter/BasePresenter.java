package dev.baofeng.com.supermovie.presenter;

import android.content.Context;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by huangyong on 2017/11/13.
 */

public abstract class BasePresenter<T> {
    protected Context context;
    protected T iview;
    public BasePresenter(Context context, T iview) {
        this.context = context;
        this.iview = iview;
    }
    public abstract void release();
    //将所有正在处理的Subcription都添加到CompositeSubscription中，统一退出的时候注销观察者
    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(io.reactivex.Observable subscription){
        if (mCompositeSubscription == null){
            mCompositeSubscription = new CompositeSubscription();
        }
    }
    //在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止RX造成的内存泄露
    public void unSubcription(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
