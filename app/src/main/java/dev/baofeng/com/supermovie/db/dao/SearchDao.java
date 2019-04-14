package dev.baofeng.com.supermovie.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

import dev.baofeng.com.supermovie.db.data.OnlineSearchHistory;
import dev.baofeng.com.supermovie.db.data.SearchHistory;

@Dao
public interface SearchDao {

    @Insert
    void insert(SearchHistory trackData);

    @Delete
    void delete(SearchHistory trackData);

    @Delete
    void delete(OnlineSearchHistory trackData);

    @Query("SELECT * FROM T_SEARCH")
    List<SearchHistory> getAll();

    @Query("SELECT * FROM T_ONLINE_SEARCH")
    List<OnlineSearchHistory> getOnlineAll();

    @Query("SELECT * FROM T_SEARCH WHERE  searchKeyWords=:keyword ")
    List<SearchHistory> getByKeywords(String keyword);

    @Query("SELECT * FROM T_ONLINE_SEARCH WHERE  searchKeyWords=:keyword ")
    List<OnlineSearchHistory> getOnlineByKeywords(String keyword);



    @Insert
    void insertOnline(OnlineSearchHistory searchHistory);
}
