package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;
import dev.baofeng.com.supermovie.view.OnItemClickListenr;
import dev.baofeng.com.supermovie.view.PosterItemView;

/**
 * Created by huangyong on 2018/2/23.
 */

public class LAdapter extends LoopVPAdapter<RecentUpdate.DataBean> {
    private final Context context;
    private ViewGroup.LayoutParams layoutParams;
    private OnItemClickListenr onItemListener;

    public LAdapter(Context context, ArrayList<RecentUpdate.DataBean> datas, ViewPager viewPager) {
        super(context, datas, viewPager);
        this.context = context;
    }

    @Override
    protected View getItemView(RecentUpdate.DataBean data) {

        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        PosterItemView imageView = new PosterItemView(mContext);
        imageView.setLayoutParams(layoutParams);
        String name = data.getDownLoadName();
        String downItemTitle = data.getDowndtitle();
        imageView.setTitleText(name);
        String[] urlarr = data.getDownimgurl().split(",");
        Glide.with(mContext).load(urlarr[0]).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(GlobalMsg.KEY_POST_IMG, urlarr[0]);
                intent.putExtra(GlobalMsg.KEY_DOWN_URL,data.getDownLoadUrl());
                intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
                intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, downItemTitle);
                intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,data.getMvdesc());
                context.startActivity(intent);
            }
        });

        return imageView;
    }

    public void setOnItemClickLisener(OnItemClickListenr onItemClickListenr) {
        this.onItemListener = onItemClickListenr;
    }
}
