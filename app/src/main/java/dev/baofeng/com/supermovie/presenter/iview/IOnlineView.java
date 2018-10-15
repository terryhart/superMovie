package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.OnlineInfo;

public interface IOnlineView {
    void loadData(OnlineInfo info);

    void loadError(String msg);

    void loadMore(OnlineInfo result);
}
