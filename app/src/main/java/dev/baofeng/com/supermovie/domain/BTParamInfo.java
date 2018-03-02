package dev.baofeng.com.supermovie.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyong on 2018/3/1.
 */

public class BTParamInfo {

    /**
     * msg :
     * code : 200
     * data : ["download","30828","9d79090fb74d335e00c51113","/download1.php"]
     */

    private String msg;
    private String code;
    private List<String> data;

    public static List<BTParamInfo> arrayBTParamInfoFromData(String str) {

        Type listType = new TypeToken<ArrayList<BTParamInfo>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
