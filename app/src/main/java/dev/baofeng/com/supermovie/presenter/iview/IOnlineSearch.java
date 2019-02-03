package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;

/**
 * creator huangyong
 * createTime 2018/12/31 下午4:00
 * path dev.baofeng.com.supermovie.presenter.iview
 * description:
 */
public interface IOnlineSearch {
    void loadData(OnlinePlayInfo info);

    void loadFail();
}
