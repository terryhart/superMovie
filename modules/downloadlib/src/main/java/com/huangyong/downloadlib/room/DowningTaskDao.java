package com.huangyong.downloadlib.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.huangyong.downloadlib.room.data.DowningTaskInfo;

import java.util.List;

/**
 * Created by HuangYong on 2018/6/27.
 */
@Dao
public interface DowningTaskDao {

    @Insert
    void insert(DowningTaskInfo trackData);

    @Delete
    void delete(DowningTaskInfo downingTaskInfo);

    @Query("SELECT * FROM T_TASK")
    List<DowningTaskInfo> getAll();

    @Query("SELECT * FROM T_TASK WHERE id=:id ")
    List<DowningTaskInfo> getById(String id);

    @Update
    void update(DowningTaskInfo downingTaskInfo);
}
