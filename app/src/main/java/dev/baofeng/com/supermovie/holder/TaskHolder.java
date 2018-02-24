package dev.baofeng.com.supermovie.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.view.FlikerProgressBar;

/**
 * Created by huangyong on 2018/1/26.
 */

public class TaskHolder extends RecyclerView.ViewHolder {


    public   TextView taskName;
    public  FlikerProgressBar progress;
    public  TextView root;
    public  TextView percent;

    public TaskHolder(View itemView) {
        super(itemView);
        progress = (FlikerProgressBar) itemView.findViewById(R.id.progress);

        root = (TextView) itemView.findViewById(R.id.speed);
        percent = (TextView) itemView.findViewById(R.id.percent);
        taskName = (TextView) itemView.findViewById(R.id.taskname);


    }
}
