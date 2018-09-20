package com.huangyong.downloadlib.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.domain.DowningTaskInfo;

import java.util.ArrayList;
import java.util.List;

public class DownedTaskAdapter extends RecyclerView.Adapter<TaskHolder> {

    private List<DowningTaskInfo> taskInfo = new ArrayList();
    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_layout,null);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
//        holder.taskTitile.setText("测试下载标题");
        holder.posterImg.setProgress(30);
        holder.posterImg.setImageResource(R.drawable.poster);
        holder.taskTitile.setText(taskInfo.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return taskInfo.size();
    }

    public void setTaskData(List<DowningTaskInfo> info) {
        this.taskInfo.clear();
        this.taskInfo.addAll(info);
    }
}
