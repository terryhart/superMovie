package dev.baofeng.com.supermovie.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

public class PlayListHolder extends RecyclerView.ViewHolder {


    public TextView btPlayText;

    public PlayListHolder(View itemView) {
        super(itemView);
        btPlayText = itemView.findViewById(R.id.btPlayText);
    }
}
