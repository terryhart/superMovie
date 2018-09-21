package com.huangyong.downloadlib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
            Glide.with(context).load(taskInfo.get(position).getPostImgUrl()).into(holder.posterImg);
            long received=0,total=0;
            if (!taskInfo.get(position).getReceiveSize().equals("0")){
               received = Long.parseLong(taskInfo.get(position).getReceiveSize());
            }
            if (taskInfo.get(position).getTotalSize()!=null &&!taskInfo.get(position).getTotalSize().equals("0")){
                total = Long.parseLong(taskInfo.get(position).getTotalSize());
            }
            holder.posterImg.setProgress((int) (received*1.0f/total*1.0f*100));
            String fileSize = FileUtils.convertFileSize(total);
            String receivedSize = FileUtils.convertFileSize(received);
            holder.task_msg.setText(fileSize+"  已下载"+receivedSize+"\n正在下载 2.3M/s");

            if (fileSize==receivedSize){
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
                taskInfo.remove(position);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
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
    }
}
