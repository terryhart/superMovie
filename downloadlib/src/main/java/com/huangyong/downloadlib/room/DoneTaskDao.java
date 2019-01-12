package com.huangyong.downloadlib.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.huangyong.downloadlib.room.data.DoneTaskInfo;

import java.util.List;

/**
 * Created by HuangYong on 2018/6/27.
 */
@Dao
public interface DoneTaskDao {

    @Insert
    public void insert(DoneTaskInfo trackData);

    @Delete
    public void delete(DoneTaskInfo trackData);

    @Query("SELECT * FROM T_HAVEEDTASK")
    List<DoneTaskInfo> getAll();

    @Query("SELECT * FROM T_HAVEEDTASK WHERE id=:id ")
    List<DoneTaskInfo> getById(String id);
}
