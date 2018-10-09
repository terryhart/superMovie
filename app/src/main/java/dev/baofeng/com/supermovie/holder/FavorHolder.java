package dev.baofeng.com.supermovie.holder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/1/26.
 */

public class FavorHolder extends RecyclerView.ViewHolder {


    public ImageView itemimg;
    public TextView itemtitle;
    public ConstraintLayout root;

    public FavorHolder(View itemView) {
        super(itemView);
        itemimg = (ImageView) itemView.findViewById(R.id.sc_post_img);
        root = (ConstraintLayout) itemView.findViewById(R.id.root);
        itemtitle = (TextView) itemView.findViewById(R.id.sc_post_title);
    }
}
