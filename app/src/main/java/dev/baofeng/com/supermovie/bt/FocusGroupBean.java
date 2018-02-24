package dev.baofeng.com.supermovie.bt;

/*
 * 创建重点关注组的bean
 * <p>
 * created by song on 2017/5/17.14:26
 */

import java.io.Serializable;
import java.util.List;

import dev.baofeng.com.supermovie.domain.TaskInfo;

public class FocusGroupBean implements Serializable {

    private String groupName;
    private List<TaskInfo> groupList;

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    private int isDefault;

    public FocusGroupBean(String groupName, List<TaskInfo> groupList) {
        this.groupName = groupName;
        this.groupList = groupList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<TaskInfo> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<TaskInfo> groupList) {
        this.groupList = groupList;
    }

}
