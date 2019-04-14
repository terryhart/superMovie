package com.huangyong.playerlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.huangyong.playerlib.R;

/**
 * creator huangyong
 * createTime 2019/4/3 下午1:26
 * path com.huangyong.playerlib.adapter
 * description:
 */
public class PlayHolder extends RecyclerView.ViewHolder {

    final TextView index;

    public PlayHolder(View itemView) {
        super(itemView);
        index = itemView.findViewById(R.id.play_index);
    }
}
