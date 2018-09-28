package dev.baofeng.com.supermovie.http;
/**
 * Created by HuangYong on 2018/9/28.
 */

import android.content.Context;
import android.support.annotation.NonNull;

import com.vector.update_app.HttpManager;

import java.util.Map;

import dev.baofeng.com.supermovie.SplashActivity;
import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.presenter.UpdateAppPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IupdateView;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @created 2018/9/28
 * @changeRecord [修改记录] <br/>
 * 2018/9/28 ：created
 */
public class UpdateAppHttpUtil implements HttpManager{

    private Context context;

    public UpdateAppHttpUtil( Context context) {
        this.context = context;
    }

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {
        UpdateAppPresenter presenter = new UpdateAppPresenter(context, new IupdateView() {
            @Override
            public void noUpdate() {
            }

            @Override
            public void updateYes(AppUpdateInfo result) {

            }
        });
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {

    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull FileCallback callback) {

    }
}
