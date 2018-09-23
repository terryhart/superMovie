package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/1/29.
 */

public class DownHolder extends RecyclerView.ViewHolder {

    public ImageView downIcon;
    public TextView tvdown;

    public DownHolder(View itemView) {
        super(itemView);
        tvdown =  itemView.findViewById(R.id.tv_down);
        downIcon = itemView.findViewById(R.id.down_icon);
    }
}
