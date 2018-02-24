package dev.baofeng.com.supermovie.bt;

/*
 * 创建重点关注组的bean
 * <p>
 * created by song on 2017/5/17.14:26
 */

import java.io.Serializable;
import java.util.List;

public class FocusGroupBean implements Serializable {

    private String groupName;
    private List<VeDetailBean> groupList;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    private int isDefault;

    public FocusGroupBean(String groupName, List<VeDetailBean> groupList) {
        this.groupName = groupName;
        this.groupList = groupList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<VeDetailBean> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<VeDetailBean> groupList) {
        this.groupList = groupList;
    }

}
