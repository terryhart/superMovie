package dev.baofeng.com.supermovie.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Huangyong on 2017/9/13.
 */

public class ApiManager {
    private static ApiService api;
    protected static final Object monitor = new Object();
    private static Retrofit retrofit;
    private static OkHttpClient client;

    private ApiManager() {
    }
    static {
        //添加应用拦截器
        client = new OkHttpClient.Builder()
                //添加应用拦截器
                .connectTimeout(35, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HttpInterceptor())
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .build();


        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(UrlConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }
    public static ApiService getRetrofitInstance(){

        if (api==null){
            synchronized (monitor){
                api = retrofit.create(ApiService.class);
            }
        }
        return api;
    }
    public static OkHttpClient getClientInstance(){
        return client;
    }
}
