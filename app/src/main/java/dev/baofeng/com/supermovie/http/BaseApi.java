package dev.baofeng.com.supermovie.http;


import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.utils.NetUtil;
import dev.baofeng.com.supermovie.utils.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * creator huangyong
 * createTime 2019/2/24 下午10:24
 * path dev.baofeng.com.supermovie.http
 * description:
 */
public class BaseApi {

    // 创建网络接口请求实例
    public static <T> T createApi(Class<T> service) {
        final String url = UrlConfig.BASE_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                // 注意这里使用了刚才在application里提供创建okhttp的方法
                .client(MyApp.genericClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(service);
    }

    // 执行网络请求
    public static <T> void request(Observable<T> observable,
                                   final IResponseListener<T> listener) {

        if (!NetUtil.isNetworkAvailable(MyApp.getContext())) {
            ToastUtil.showMessage("网络不可用,请检查网络");
            if (listener != null) {
                listener.onFail();
            }
            return;
        }
        observable.subscribeOn(Schedulers.io())
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {

                               @Override
                               public void onError(Throwable e) {
                                   e.printStackTrace();
                                   if (listener != null) {
                                       listener.onFail();
                                   }
                               }

                               @Override
                               public void onComplete() {

                               }

                               @Override
                               public void onSubscribe(Disposable disposable) {

                               }

                               @Override
                               public void onNext(T data) {
                                   if (listener != null) {
                                       listener.onSuccess(data);
                                   }
                               }
                           }
                );



    }

    public interface IResponseListener<T> {

        void onSuccess(T data);

        void onFail();
    }
}