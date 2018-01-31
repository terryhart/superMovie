package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/1/29.
 */

public class DownHolder extends RecyclerView.ViewHolder {


    public TextView tvdown;

    public DownHolder(View itemView) {
        super(itemView);
        tvdown = (TextView) itemView.findViewById(R.id.tv_down);
    }
}
