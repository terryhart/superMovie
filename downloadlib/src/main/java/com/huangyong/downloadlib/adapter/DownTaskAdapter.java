package com.huangyong.downloadlib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class DownTaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private Context context;
    private List<DowningTaskInfo> taskInfo;

    public DownTaskAdapter(List<DowningTaskInfo> infos) {
        this.taskInfo = infos;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_layout,null);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, final int position) {

        if (taskInfo.size()>0){
            holder.taskTitile.setText(taskInfo.get(position).getTitle());
            Glide.with(context).load(taskInfo.get(position).getPostImgUrl()).placeholder(R.drawable.ic_dl_magnet_place_holder).into(holder.posterImg);
            Log.e("dddddldlld",taskInfo.get(position).getPostImgUrl());
            long received=0,total=0;
            if (!taskInfo.get(position).getReceiveSize().equals("0")){
               received = Long.parseLong(taskInfo.get(position).getReceiveSize());
            }
            if (taskInfo.get(position).getTotalSize()!=null &&!taskInfo.get(position).getTotalSize().equals("0")){
                total = Long.parseLong(taskInfo.get(position).getTotalSize());
            }
            int progress = (int) (received*1.0f/total*1.0f*100);
            holder.posterImg.setProgress(progress);

            if (progress>5){
                holder.playinloading.setVisibility(View.VISIBLE);
                holder.playinloadingBg.setVisibility(View.VISIBLE);
            }else {
                holder.playinloading.setVisibility(View.INVISIBLE);
                holder.playinloadingBg.setVisibility(View.INVISIBLE);
            }
            String fileSize = FileUtils.convertFileSize(total);
            String receivedSize = FileUtils.convertFileSize(received);
            String speed;
            if (TextUtils.isEmpty(taskInfo.get(position).getSpeed())){
                speed= "0";
            }else {
                speed = taskInfo.get(position).getSpeed();
            }
            if (taskInfo.get(position).getTitle().endsWith(".torrent")){
                holder.playinloading.setText("BT种子");
            }else {
                holder.playinloading.setText("边下边播");
            }

            //边下边播点击事件
            holder.playinloading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null&&taskInfo.get(position).getStatu()==1){
                        listener.clicktoplay(taskInfo.get(position));
                    }
                }
            });


            if (taskInfo.get(position).getStatu()==1){
                holder.finish_icon.setImageResource(R.drawable.download_item_resume_icon);
                holder.task_size.setText(fileSize+"  已下载"+receivedSize);
                holder.task_msg.setText("\n正在下载  "+speed+" /s");
                holder.posterImg.setTaskStatu(false);
                holder.playinloading.setVisibility(View.VISIBLE);
            }else if (taskInfo.get(position).getStatu()==0||taskInfo.get(position).getStatu()==4){
                holder.finish_icon.setImageResource(R.drawable.download_item_pause_icon);
                holder.task_msg.setText("\n已暂停 ");
                holder.task_size.setText("");
                holder.posterImg.setTaskStatu(true);
                holder.playinloading.setVisibility(View.INVISIBLE);
            }else if (taskInfo.get(position).getStatu()==2){
                holder.finish_icon.setImageResource(R.drawable.ic_detail_download_finish);
                holder.task_msg.setText("\n下载完成  ");
                holder.task_size.setText("");
                holder.playinloading.setVisibility(View.INVISIBLE);
            }else if (taskInfo.get(position).getStatu()==3){
                holder.finish_icon.setImageResource(R.drawable.ic_detail_download_error);
                holder.task_msg.setText("！版权受限，下载失败\n请长按复制链接到百度网盘离线下载  ");
                holder.task_size.setText("");
                holder.posterImg.setTaskStatu(true);
                holder.playinloading.setVisibility(View.INVISIBLE);
            }
            holder.posterImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.clicked(taskInfo.get(position));
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener!=null){
                        listener.longclicked(taskInfo.get(position));
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return taskInfo.size();
    }

    public void setTaskData(List<DowningTaskInfo> info) {
        this.taskInfo.clear();
        this.taskInfo.addAll(info);
        notifyDataSetChanged();
    }
    public void setOnItemclickListenr(OnItemClickListener listenr){
        this.listener = listenr;
    }
    private OnItemClickListener listener;

    public void deleteItem(int id) {
        for (int i = 0; i < taskInfo.size(); i++) {
            if (taskInfo.get(i).getId()==id){
                taskInfo.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void clicked(DowningTaskInfo info);

        void longclicked(DowningTaskInfo info);

        void clicktoplay(DowningTaskInfo taskInfo);
    }
}
