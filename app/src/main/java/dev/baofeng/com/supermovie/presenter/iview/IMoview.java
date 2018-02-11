package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.MovieInfo;

/**
 * Created by huangyong on 2018/1/26.
 */

public interface IMoview {
    void loadData(MovieInfo info);
    void loadError(String msg);

    void loadMore(MovieInfo result);

    void loadBtData(MovieInfo result);
}
