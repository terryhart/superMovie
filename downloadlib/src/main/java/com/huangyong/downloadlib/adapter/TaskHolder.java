package com.huangyong.downloadlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.huangyong.downloadlib.R;

public class TaskHolder extends RecyclerView.ViewHolder {

    public TextView taskTitile;

    public TaskHolder(View itemView) {
        super(itemView);
        taskTitile = itemView.findViewById(R.id.task_title);
    }
}
