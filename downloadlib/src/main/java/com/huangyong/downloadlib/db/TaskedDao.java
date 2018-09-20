package com.huangyong.downloadlib.db;

import android.content.Context;

import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by HuangYong on 2018/6/27.
 */
public class TaskedDao {

    //两个泛型约束 一个是对应的实体类类型，一个是主键类型
    private static Dao<DoneTaskInfo,Integer> dao;

    private static TaskedDao daos;
    public static TaskedDao getInstance(Context context){
        if (daos==null){
            daos =  new TaskedDao(context);
        }
        return daos;
    }

    public TaskedDao(Context context) {
        try {
            dao = DatabaseHelper.getInstance(context).getDao(DoneTaskInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(DoneTaskInfo messageDb){
        try {
            return dao.create(messageDb);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public DoneTaskInfo query(int id){
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<DoneTaskInfo> queryAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<DoneTaskInfo> queryForFeilds(String s, Object obj){
        try {
            return dao.queryForEq(s,obj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int updata(DoneTaskInfo message){
        try {
            return dao.update(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int delete(int id){
        try {
            return dao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int deleteAll(){
        try {
            return dao.delete(queryAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
