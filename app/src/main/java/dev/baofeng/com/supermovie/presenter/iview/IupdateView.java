package dev.baofeng.com.supermovie.presenter.iview;
/**
 * Created by HuangYong on 2018/9/27.
 */

import dev.baofeng.com.supermovie.domain.AppUpdateInfo;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @created 2018/9/27
 * @changeRecord [修改记录] <br/>
 * 2018/9/27 ：created
 */
public interface IupdateView {
    void  noUpdate(String url);

    void updateYes(AppUpdateInfo result);
}
