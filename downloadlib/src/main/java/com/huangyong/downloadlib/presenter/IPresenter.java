package com.huangyong.downloadlib.presenter;
/**
 * Created by HuangYong on 2018/9/20.
 */

import com.huangyong.downloadlib.domain.DowningTaskInfo;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/20
 * @changeRecord [修改记录] <br/>
 * 2018/9/20 ：created
 */
public interface IPresenter {

    void addTask(DowningTaskInfo downTaskInfo);

    void pauseTask(String url);

    void restartTask(String url);
}
