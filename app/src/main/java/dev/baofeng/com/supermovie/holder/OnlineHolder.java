package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/1/29.
 */

public class OnlineHolder extends RecyclerView.ViewHolder {

    public RecyclerView rvOnlineView;

    public OnlineHolder(View itemView) {
        super(itemView);
        rvOnlineView =  itemView.findViewById(R.id.rvOnline);
    }
}
