package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

public class OnlinePlayHolder extends RecyclerView.ViewHolder {


    public TextView btPlayText;

    public OnlinePlayHolder(View itemView) {
        super(itemView);
        btPlayText = itemView.findViewById(R.id.btPlayText);
    }
}
