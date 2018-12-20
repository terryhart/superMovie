package dev.baofeng.com.supermovie.db.dao;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.db.AppDatabase;
import dev.baofeng.com.supermovie.db.data.SearchHistory;

/**
 * creator huangyong
 * createTime 2018/12/17 下午6:39
 * path dev.baofeng.com.supermovie.db.dao
 * description:中介类
 */
public class DbHelper {

    public static ArrayList<SearchHistory> getAllHistory() {

        ArrayList<SearchHistory> searchHistories = (ArrayList<SearchHistory>) AppDatabase.getInstance(MyApp.getContext()).searchDao().getAll();
        if (searchHistories != null && searchHistories.size() > 0) {
            return searchHistories;
        } else {
            return new ArrayList<>();
        }
    }

    public static boolean checkKeyWords(String keyword) {

        ArrayList<SearchHistory> byKeywords = (ArrayList<SearchHistory>) AppDatabase.getInstance(MyApp.getContext()).searchDao().getByKeywords(keyword);
        if (byKeywords != null && byKeywords.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    public static void addKeywords(String keyword) {
        ArrayList<SearchHistory> allHistory = getAllHistory();
        if (allHistory.size() > 16) {
            AppDatabase.getInstance(MyApp.getContext()).searchDao().delete(allHistory.get(0));
        }

        SearchHistory searchHistory = new SearchHistory();
        searchHistory.searchKeyWords = keyword;
        AppDatabase.getInstance(MyApp.getContext()).searchDao().insert(searchHistory);
    }

    public static void clearKeywords() {
        ArrayList<SearchHistory> allHistory = getAllHistory();
        if (allHistory != null && allHistory.size() > 0) {
            for (SearchHistory history : allHistory) {
                AppDatabase.getInstance(MyApp.getContext()).searchDao().delete(history);
            }
        }
    }
}
