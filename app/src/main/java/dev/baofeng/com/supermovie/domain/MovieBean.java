package dev.baofeng.com.supermovie.domain;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangyong on 2018/1/29.
 */

public class MovieBean {

    /**
     * msg : 请求成功
     * reg : -101
     * date : ["ed2k://|file|%E5%8A%A8%E7%89%A9%E5%9B%AD%E9%95%BF%E7%9A%84%E5%A4%AB%E4%BA%BA.720p.BD%E4%B8%AD%E5%AD%97[66%E5%BD%B1%E8%A7%86www.66ys.tv].mp4|2020216547|FE9F5A9AF7F8F2B86A7773598220D12B|h=PQU64QIX2CXHP3JKSWW72QAC7CEQHMHQ|/","http://pan.baidu.com/s/1dFKkmbR"]
     */

    private String msg;
    private String reg;
    private List<String> date;

    public static List<MovieBean> arrayMovieBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<MovieBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }
}
