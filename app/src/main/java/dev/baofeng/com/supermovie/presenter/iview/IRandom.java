package dev.baofeng.com.supermovie.presenter.iview;


import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;

/**
 * Created by huangyong on 2018/1/26.
 */

public interface IRandom {
    void loadRandomData(OnlinePlayInfo info);

    void loadRError(String msg);
}
