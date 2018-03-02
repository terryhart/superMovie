package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.bt.ThreadUtils;
import dev.baofeng.com.supermovie.domain.FormatInfo;
import dev.baofeng.com.supermovie.domain.RunTaskInfo;
import dev.baofeng.com.supermovie.domain.TaskHistory;
import dev.baofeng.com.supermovie.domain.TaskInfo;
import dev.baofeng.com.supermovie.holder.TaskHolder;
import dev.baofeng.com.supermovie.utils.SizeUtils;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.onAddListener;

/**
 * Created by huangyong on 2018/1/26.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
    private Context context;
    private List<TaskInfo> info;
    private static boolean isRunning = false;

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                for (int i = 0; i < info.size(); i++) {
                    try {
                        /*if (TextUtils.isEmpty(info.get(i).getPath())){
                            Log.d("下载进度：","URL获取失败");
                         return;
                        }else {

                        }*/
                        XLTaskInfo task = XLTaskHelper.instance(MyApp.appInstance()).getTaskInfo(Long.parseLong(info.get(i).getTaskid() + ""));
                        if (task.mDownloadSize > 0) {
                            int progress = (int) ((task.mDownloadSize)*1.0/(task.mFileSize )*1.0 * 100);

                            info.get(i).setProgress(progress);
                            info.get(i).setFileSize(SizeUtils.convertFileSize(task.mFileSize)+"");
                            info.get(i).setDownSize(SizeUtils.convertFileSize(task.mDownloadSize)+"");
                            info.get(i).setLocalpath("/sdcard/" + XLTaskHelper.instance(MyApp.appInstance()).getFileName(info.get(i).getPath()));
                            Log.d("下载进度：","/sdcard/" + XLTaskHelper.instance(MyApp.appInstance()).getFileName(info.get(i).getPath()));
//                            info.get(i).setName(task.mFileName);
                            if (task.mDownloadSize==task.mFileSize){
                                //下载完成，存入已完成的表，从当前表删除
                                TaskInfo info = TaskAdapter.this.info.get(i);
                                TaskHistory history = new TaskHistory();
                                history.setName(info.getName());
                                history.setSize(info.getFileSize());
                                history.setPath(info.getLocalpath());
                                history.save();
                                TaskAdapter.this.info.remove(i);
                                info.delete();
                                info.save();
                                //发送广播，让已完成表更新
                                //发送广播，通知下载任务列表更新数据，注意，任务列表数据也由数据库获取，保证源唯一，避免数据不统一。
                                Intent intent = new Intent();
                                intent.setAction(GlobalMsg.REFRESH);
                                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
                                localBroadcastManager.sendBroadcast(intent);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.d("下载进度：","获取失败");
                    }
                    Log.d("下载进度：","更新中");
                }
                notifyDataSetChanged();
                handler.sendMessageDelayed(handler.obtainMessage(0), 1000);
            }
        }
    };
    public TaskAdapter(Context context, List<TaskInfo> info) {
        this.context = context;
        this.info = info;
        handler.sendMessage(handler.obtainMessage(0));
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item,parent,false);
        return new TaskHolder(view);
    }
    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {

        holder.taskName.setText(info.get(position).getName());
        holder.size.setText(info.get(position).getDownSize()+"/"+info.get(position).getFileSize()+"   ");
        holder.progress.setProgress(info.get(position).getProgress());

        Log.d("下载的进度：",info.get(position).getProgress()+"");


        //如果应用重启，之前的下载线程池其实已经不在了，队列也为空了，只是列表是读取的数据库的，所以还在显示
        //所以要实现继续下载，必须重新初始化线程池，将数据库所有任务添加进队列
        if (info.get(position).getIsWaiting()==0){
            holder.showtext.setText(info.get(position).getProgress()+"%");
            holder.progress.setOnClickListener(v -> {
                holder.showtext.setText("暂停");
                info.get(position).setIsWaiting(1);
                info.get(position).save();
                GlobalMsg.service.stopTheTask(info.get(position).getTaskid());
            });
        }else {
            holder.showtext.setText("暂停");
            holder.progress.setOnClickListener(v -> {

                holder.showtext.setText("开始");
                info.get(position).setIsWaiting(0);
                info.get(position).save();
                GlobalMsg.service.reStartTheTask(info.get(position).getPath());
                GlobalMsg.service.setAddListener(taskId-> {
                    info.get(position).setTaskid(taskId);
                    info.get(position).save();
                });


            });
        }
    }
    public void saveData(){
        for (int i = 0; i < info.size(); i++) {
            info.get(i).setIsWaiting(1);
            info.get(i).save();
        }
    }
    private int caculate(int position) {
        if (info.size()==0){
            return 0;
        }else {
            TaskInfo fileInfo = info.get(position);
            float pro = (float) (Long.parseLong(fileInfo.getDownSize())*1.0/ Long.parseLong(fileInfo.getFileSize()));
            int progress = (int)(pro*100);
            float downSize = Long.parseLong(fileInfo.getDownSize()) / 1024.0f / 1024;
            float totalSize =Long.parseLong(fileInfo.getFileSize() ) / 1024.0f / 1024;
            return progress;
        }
    }


    @Override
    public int getItemCount() {
        return info.size();
    }


    public void clear() {
        if (info.size()>0)
        info.clear();
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
