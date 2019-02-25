package dev.baofeng.com.supermovie.presenter;
/**
 * Created by HuangYong on 2018/9/27.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;



import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.http.ApiManager;
import dev.baofeng.com.supermovie.http.ApiService;
import dev.baofeng.com.supermovie.http.BaseApi;
import dev.baofeng.com.supermovie.presenter.iview.IupdateView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/27
 * @changeRecord [修改记录] <br/>
 * 2018/9/27 ：created
 */
public class UpdateAppPresenter extends BasePresenter<IupdateView> {


    public UpdateAppPresenter(Context context, IupdateView iview) {
        super(context, iview);
    }

    @Override
    public void release() {
        unSubcription();
    }

    public void getAppUpdate(Context context){


        BaseApi.request(BaseApi.createApi(ApiService.class)
                        .getAppUpdate(), new BaseApi.IResponseListener<AppUpdateInfo>() {
                    @Override
                    public void onSuccess(AppUpdateInfo result) {
                        if (result.getData().getVersionCode()> getVersionCode(context,"dev.baofeng.com.supermovie")){
                            //如果服务端的版本号大于本地，即更新
                            iview.updateYes(result);
                        }else {
                            iview.noUpdate(result.getData().getDownloadUrl());
                        }
                    }

                    @Override
                    public void onFail() {
                    }
                }
        );

    }

    public static int getVersionCode(Context context, String packageName){
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
