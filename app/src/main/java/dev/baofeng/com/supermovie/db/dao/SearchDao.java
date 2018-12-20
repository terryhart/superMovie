package dev.baofeng.com.supermovie.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

import dev.baofeng.com.supermovie.db.data.SearchHistory;

@Dao
public interface SearchDao {

    @Insert
    public void insert(SearchHistory trackData);

    @Delete
    public void delete(SearchHistory trackData);

    @Query("SELECT * FROM T_SEARCH")
    List<SearchHistory> getAll();

    @Query("SELECT * FROM T_SEARCH WHERE  searchKeyWords=:keyword ")
    List<SearchHistory> getByKeywords(String keyword);

}
