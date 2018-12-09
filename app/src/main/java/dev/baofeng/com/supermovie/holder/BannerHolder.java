package dev.baofeng.com.supermovie.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.view.PosterItemView;

/**
 * creator huangyong
 * createTime 2018/12/8 上午6:07
 * path dev.baofeng.com.supermovie.holder
 * description:
 */
public class BannerHolder extends RecyclerView.ViewHolder {


    public PosterItemView iv;
    public TextView title;

    public BannerHolder(View view) {
        super(view);
        iv = view.findViewById(R.id.banner);
        title = view.findViewById(R.id.movtitle);
    }
}
