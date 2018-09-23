package dev.baofeng.com.supermovie.holder;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/2/11.
 */

public class HeadHolder extends ViewHolder {

    public TextView mvdesc;
    public ImageView screenShot;

    public HeadHolder(View itemView) {
        super(itemView);
        mvdesc = itemView.findViewById(R.id.mvdesc);
        screenShot = itemView.findViewById(R.id.screen_cut);
    }
}
