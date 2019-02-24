package com.huangyong.downloadlib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.room.data.DoneTaskInfo;
import com.huangyong.downloadlib.utils.FileUtils;

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
        if (taskInfo.size()>0){


            holder.posterImg.setProgress(100);

            holder.taskTitile.setText(taskInfo.get(position).getTitle());
            String fileSize;
            try {
                fileSize = FileUtils.convertFileSize(Long.parseLong(taskInfo.get(position).getTotalSize()));
            } catch (Exception e) {
                fileSize = "0";
            }


            holder.task_size.setText(fileSize+"/"+fileSize);
            holder.task_msg.setText("已完成");

            holder.finish_icon.setVisibility(View.VISIBLE);

            holder.posterImg.setFinished(true);

            holder.posterImg.setTaskStatu(false);

            holder.playinloading.setVisibility(View.VISIBLE);
            if (taskInfo.get(position).getTitle().endsWith(".torrent")){
                holder.playinloading.setText("种子文件");
                holder.posterImg.setImageResource(R.drawable.ic_dl_torrent_bt);
                holder.posterImg.setPlayIconHide(true);
            }else {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_dl_magnet_place_holder);
                Glide.with(context).load(taskInfo.get(position).getPostImgUrl())
                        .apply(options)
                        .into(holder.posterImg);
                holder.playinloading.setVisibility(View.INVISIBLE);
            }

            holder.posterImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (longPressListener!=null){
                        longPressListener.clicked(taskInfo.get(position));
                    }
                }
            });
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

    private OnPressListener longPressListener;
    public void setOnLongPressListener(OnPressListener longPressListener){
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

   public interface OnPressListener {
        void longPressed(DoneTaskInfo taskInfo);

        void clicked(DoneTaskInfo taskInfo);
    }
}
