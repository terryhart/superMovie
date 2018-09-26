package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;

public interface IAllView {

    void loadSuccess(RecentUpdate movieBean);

    void loadMore(RecentUpdate movieBean);
}
