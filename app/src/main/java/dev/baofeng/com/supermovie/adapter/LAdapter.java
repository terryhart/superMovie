package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.domain.MovieInfo;

/**
 * Created by huangyong on 2018/2/23.
 */

public class LAdapter extends LoopVPAdapter<MovieInfo.DataBean> {
    private ViewGroup.LayoutParams layoutParams;
    public LAdapter(Context context, ArrayList<MovieInfo.DataBean> datas, ViewPager viewPager) {
        super(context, datas, viewPager);
    }

    @Override
    protected View getItemView(MovieInfo.DataBean data) {

        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(data.getDownimgurl()).into(imageView);
        return imageView;
    }
}
