package com.huangyong.downloadlib.domain;
/**
 * Created by HuangYong on 2018/9/20.
 */

import java.util.List;

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
public class DataList {

    private List<DownTaskInfo> infoList;

    public List<DownTaskInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<DownTaskInfo> infoList) {
        this.infoList = infoList;
    }
}
