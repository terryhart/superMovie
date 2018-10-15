package dev.baofeng.com.supermovie.presenter.iview;
/**
 * Created by HuangYong on 2018/10/15.
 */

import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @created 2018/10/15
 * @changeRecord [修改记录] <br/>
 * 2018/10/15 ：created
 */
public interface ISubjectView {

    void loadData(SubjectInfo info);
    void loadData(SubjectTitleInfo info);

    void loadError(String msg);

    void loadMore(SubjectInfo result);
    void loadMore(SubjectTitleInfo result);

}
