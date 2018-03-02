package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;

import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/1/26.
 */

public class TaskHolder extends RecyclerView.ViewHolder {


    public   TextView taskName;
    public  TextView size;
    public  TextView percent;
    public DonutProgress progress;
    public TextView showtext;

    public TaskHolder(View itemView) {
        super(itemView);
        progress = (DonutProgress) itemView.findViewById(R.id.donut_progress);
        showtext = (TextView) itemView.findViewById(R.id.showtext);
        size = (TextView) itemView.findViewById(R.id.size);
        percent = (TextView) itemView.findViewById(R.id.percent);
        taskName = (TextView) itemView.findViewById(R.id.taskname);
    }
}
