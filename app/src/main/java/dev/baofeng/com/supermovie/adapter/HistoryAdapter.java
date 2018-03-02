package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.FormatInfo;
import dev.baofeng.com.supermovie.domain.TaskHistory;
import dev.baofeng.com.supermovie.domain.TaskInfo;
import dev.baofeng.com.supermovie.holder.HistoryHolder;
import dev.baofeng.com.supermovie.holder.TaskHolder;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryHolder> {
    private Context context;
    private List<TaskHistory> info;
    private boolean isRunning = false;

    public HistoryAdapter(Context context, ArrayList<TaskHistory> info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_history_item,parent,false);
        return new HistoryHolder(view);
    }
    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        holder.hisname.setText(info.get(position).getName());
        holder.hissize.setText(info.get(position).getSize()+"");

        holder.play.setOnClickListener(v -> Toast.makeText(context, "准备播放，请稍后", Toast.LENGTH_SHORT).show());

    }



    @Override
    public int getItemCount() {
        return info.size();
    }



    public String getNum(String num){
        String regEx = "[^0-9]";//匹配指定范围内的数字

        //Pattern是一个正则表达式经编译后的表现模式
        Pattern p = Pattern.compile(regEx);

        // 一个Matcher对象是一个状态机器，它依据Pattern对象做为匹配模式对字符串展开匹配检查。
        Matcher m = p.matcher(num);

        //将输入的字符串中非数字部分用空格取代并存入一个字符串
        String string = m.replaceAll(" ").trim();

        //以空格为分割符在讲数字存入一个字符串数组中
        String[] strArr = string.split(" ");
        String a ="";
        //遍历数组转换数据类型输出
        for(String s:strArr){
            a +=s;
        }
       String b=a.substring(1);
        String c = a.substring(0,1);
        return c+"."+b;
    }
    public String getDesc(String desc){
        Gson gson = new Gson();
        String json = "{\"list\": "+desc+"}";
        FormatInfo info = gson.fromJson(json,FormatInfo.class);
        String de = "";
        for (int i = 0; i < info.getList().size(); i++) {
            de += info.getList().get(i)+" ";
        }
        return de;
    }
}
