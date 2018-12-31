package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.RecentUpdate;

/**
 * creator huangyong
 * createTime 2018/12/31 上午8:11
 * path dev.baofeng.com.supermovie.presenter.iview
 * description:
 */
public interface IShare {

    void loadData(RecentUpdate data);

    void loadFail(String e);
}
