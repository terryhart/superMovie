package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;

/**
 * Created by huangyong on 2018/1/26.
 */

public interface IMoview {
    void loadData(RecentUpdate info);
    void loadData(MovieInfo info);
    void loadError(String msg);

    void loadMore(MovieInfo result);
    void loadMore(RecentUpdate result);

    void loadBtData(MovieInfo result);

    void loadDetail(BtInfo result);
}
