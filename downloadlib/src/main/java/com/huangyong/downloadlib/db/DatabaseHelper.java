package com.huangyong.downloadlib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * ormlite 的数据库初始化创建类，需要几个表，直接在oncreate和onUpgrade里更新即可。
 * Created by HuangYong on 2018/6/27.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "db_task";
    private static final int DB_VERSION =12;

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public static DatabaseHelper getInstance(Context context){
        if(instance == null){
            synchronized (DatabaseHelper.class){
                if(instance == null){
                    instance = new DatabaseHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DowningTaskInfo.class);
            TableUtils.createTable(connectionSource, DoneTaskInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource,DowningTaskInfo.class,true);
            TableUtils.dropTable(connectionSource,DoneTaskInfo.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //释放资源
    @Override
    public void close()
    {
        super.close();
        instance.close();
        instance = null;
    }
}
