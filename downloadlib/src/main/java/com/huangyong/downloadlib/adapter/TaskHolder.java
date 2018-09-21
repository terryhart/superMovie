package com.huangyong.downloadlib.adapter;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.huangyong.downloadlib.R;
import com.huangyong.downloadlib.view.DeleteDialog;
import com.huangyong.downloadlib.view.ProgressImageView;

public class TaskHolder extends RecyclerView.ViewHolder {

    public TextView taskTitile;
    public TextView task_msg;
    public TextView playinloading;
    public ProgressImageView posterImg;

    public TaskHolder(View itemView) {
        super(itemView);
        taskTitile = itemView.findViewById(R.id.task_title);
        task_msg = itemView.findViewById(R.id.task_msg);
        playinloading = itemView.findViewById(R.id.playinloading);
        posterImg = itemView.findViewById(R.id.posterImg);

    }
}
