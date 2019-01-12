package com.huangyong.downloadlib.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.huangyong.downloadlib.room.data.DoneTaskInfo;
import com.huangyong.downloadlib.room.data.DowningTaskInfo;


@Database(entities = {DoneTaskInfo.class, DowningTaskInfo.class}, version = 4, exportSchema = false)
public abstract class AppDatabaseManager extends RoomDatabase {

    private static final String TABLE_NAME = "com_bt_download.db";

    public abstract DowningTaskDao donwingDao();

    public abstract DoneTaskDao doneTaskDao();

    private static AppDatabaseManager instance;
    private static final Object s_Lock = new Object();

    public static synchronized AppDatabaseManager getInstance(Context context) {
        synchronized (s_Lock) {
            if (instance == null) {
                instance =
                        Room.databaseBuilder(context, AppDatabaseManager.class, AppDatabaseManager.TABLE_NAME)
                                .allowMainThreadQueries()
                                .build();
            }
            return instance;
        }

    }
}
