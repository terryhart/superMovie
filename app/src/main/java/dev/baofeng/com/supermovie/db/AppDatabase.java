package dev.baofeng.com.supermovie.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import dev.baofeng.com.supermovie.db.dao.SearchDao;
import dev.baofeng.com.supermovie.db.data.SearchHistory;


@Database(entities = {SearchHistory.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TABLE_NAME = "com_bt_search.db";

    public abstract SearchDao searchDao();

    private static AppDatabase instance;
    private static final Object sLock = new Object();
    public static synchronized AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance =
                        Room.databaseBuilder(context, AppDatabase.class, AppDatabase.TABLE_NAME)
                                .allowMainThreadQueries()
                                .build();
            }
            return instance;
        }

    }
}
