package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.DoubanTop250;

/**
 * creator huangyong
 * createTime 2019/2/21 上午9:25
 * path dev.baofeng.com.supermovie.presenter.iview
 * description:
 */
public interface IDBTop250 {

    void loadData(DoubanTop250 data);

    void loadError(String s);

    void loadMore(DoubanTop250 data);
}
