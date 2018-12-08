package dev.baofeng.com.supermovie.utils;

import android.support.annotation.NonNull;

import com.vector.update_app.HttpManager;

import java.util.Map;

/**
 * creator huangyong
 * createTime 2018/12/8 上午8:35
 * path dev.baofeng.com.supermovie.utils
 * description:
 */
public class UpdateAppHttpUtil implements HttpManager {

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {
        callBack.onResponse("");
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull Callback callBack) {

    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull FileCallback callback) {

    }
}
