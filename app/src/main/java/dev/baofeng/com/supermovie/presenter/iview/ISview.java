package dev.baofeng.com.supermovie.presenter.iview;

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;

/**
 * Created by huangyong on 2018/2/23.
 */

public interface ISview {
    void onResult(RecentUpdate info);

    void onNoData();
}
