package dev.baofeng.com.supermovie.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyong on 2018/2/23.
 */

public class FormatInfo {

    private List<String> list;

    public static List<FormatInfo> arrayFormatInfoFromData(String str) {

        Type listType = new TypeToken<ArrayList<FormatInfo>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
