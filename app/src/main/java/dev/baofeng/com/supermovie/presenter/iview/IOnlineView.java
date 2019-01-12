package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.OnlinePlayInfo;

public interface IOnlineView {
    void loadData(OnlinePlayInfo info);

    void loadError(String msg);

    void loadMore(OnlinePlayInfo result);
}
