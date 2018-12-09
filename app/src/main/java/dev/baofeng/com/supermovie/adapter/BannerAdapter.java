package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.holder.BannerHolder;
import dev.baofeng.com.supermovie.holder.FavorHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

/**
 * 观看历史，点击是继续观看，不用进详情页
 * Created by huangyong on 2018/1/26.
 */

public class BannerAdapter extends RecyclerView.Adapter<BannerHolder> {
    private Context context;
    private ArrayList<RecentUpdate.DataBean> info;

    public BannerAdapter(Context context, ArrayList<RecentUpdate.DataBean> recentUpdate) {
        this.context = context;
        this.info = recentUpdate;
    }

    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewpager_item,parent,false);
        return new BannerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerHolder holder, int position) {
        if (info==null){
            return;
        }
        String url=info.get(position).getDownimgurl().split(",")[0];
        Glide.with(context).load(url).placeholder(R.drawable.ic_dl_magnet_place_holder).into(holder.iv);
        holder.title.setText(info.get(position).getDownLoadName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra(GlobalMsg.KEY_POST_IMG, url);
                    intent.putExtra(GlobalMsg.KEY_DOWN_URL,info.get(position).getDownLoadUrl());
                    intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, info.get(position).getDownLoadName());
                    intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, info.get(position).getDowndtitle());
                    intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,info.get(position).getMvdesc());
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (info==null){
            return 0;
        }
        return info.size();
    }



}
