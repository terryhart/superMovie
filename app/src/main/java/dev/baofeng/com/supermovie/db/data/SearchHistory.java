package dev.baofeng.com.supermovie.db.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


/**
 * creator huangyong
 * creator huangyong
 * createTime 2018/11/13 下午5:21
 * path com.xiachufang.tracklib.db
 * description:
 */

@Entity(tableName = SearchHistory.TABLE_NAME, indices = {@Index("searchKeyWords")})
public class SearchHistory {

    public static final String TABLE_NAME = "t_search";

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "searchKeyWords")
    public String searchKeyWords;
}
