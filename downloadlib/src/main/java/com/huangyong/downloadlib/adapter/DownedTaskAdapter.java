package com.huangyong.downloadlib.adapter;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.FileUtils;
import com.huangyong.downloadlib.view.DeleteDialog;

import java.util.ArrayList;
import java.util.List;

public class DownedTaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private List<DoneTaskInfo> taskInfo;
    private Context context;

    public DownedTaskAdapter(List<DoneTaskInfo> taskInfos) {
        this.taskInfo = taskInfos;
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
//        holder.taskTitile.setText("测试下载标题");
        if (taskInfo.size()>0){
            holder.posterImg.setProgress(0);
            Glide.with(context).load(taskInfo.get(position).getPostImgUrl()).into(holder.posterImg);
            holder.taskTitile.setText(taskInfo.get(position).getTitle());
            String fileSize = FileUtils.convertFileSize(Long.parseLong(taskInfo.get(position).getTotalSize()));
            holder.task_msg.setText("下载完成 "+fileSize+"\n未观看");

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (longPressListener!=null){
                        longPressListener.longPressed(taskInfo.get(position));
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

    public void setTaskData(List<DoneTaskInfo> info) {
        this.taskInfo.clear();
        this.taskInfo.addAll(info);
        notifyDataSetChanged();
    }

    private OnLongPressListener longPressListener;
    public void setOnLongPressListener(OnLongPressListener longPressListener){
        this.longPressListener = longPressListener;
    }

    public void deleteItem(int id) {
        for (int i = 0; i < taskInfo.size(); i++) {
            if (taskInfo.get(i).getId()==id){
                taskInfo.remove(i);
            }
        }
        notifyDataSetChanged();
    }

   public interface OnLongPressListener{
        void longPressed(DoneTaskInfo taskInfo);
    }
}
