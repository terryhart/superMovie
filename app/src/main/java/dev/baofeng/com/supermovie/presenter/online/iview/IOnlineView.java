package dev.baofeng.com.supermovie.presenter.online.iview;


import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;

public interface IOnlineView {
    void loadData(OnlinePlayInfo info);

    void loadError(String msg);

    void loadMore(OnlinePlayInfo result);
}
