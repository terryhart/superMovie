package dev.baofeng.com.supermovie.domain;

import org.litepal.crud.DataSupport;

/**
 * Created by huangyong on 2018/2/24.
 */

public class TaskHistory extends DataSupport {

    private int id;
    private String name;
    private int progress;
    private String path;//本地磁盘路径
    private String action;
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
