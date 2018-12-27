package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.OnlinePlayInfo;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.SecondHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;
import dev.baofeng.com.supermovie.view.OnLineMovieActivity;

/**
 * Created by huangyong on 2018/2/11.
 */

public class OnlineCategoryAdapter extends RecyclerView.Adapter {
    private Context context;
    private OnlinePlayInfo datas;

    public OnlineCategoryAdapter(Context context, OnlinePlayInfo datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType== GlobalMsg.ITEM_TYPE_1){
            View view = LayoutInflater.from(context).inflate(R.layout.head_item,parent,false);
            return new HeadHolder(view);
        }else if(viewType==GlobalMsg.ITEM_TYPE_2){
            View view = LayoutInflater.from(context).inflate(R.layout.second_item,parent,false);
            return new SecondHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main,parent,false);
            return new CommonHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadHolder){

        }else if (holder instanceof SecondHolder){

        }else {
            String imgUrl = datas.getData().get(position).getDownimgurl();
            String name = datas.getData().get(position).getDownLoadName();

            Glide.with(context).load(imgUrl).asBitmap().placeholder(R.drawable.ic_place_hoder).override(180, 240).into(((CommonHolder) holder).itemimg);

            ((CommonHolder)holder).itemtitle.setText(name);

            ((CommonHolder) holder).itemimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(context, OnLineMovieActivity.class);
                        intent.putExtra(GlobalMsg.KEY_POST_IMG, imgUrl);
                        intent.putExtra(GlobalMsg.KEY_DOWN_URL,datas.getData().get(position).getDownLoadUrl());
                        intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
                        //简介
                        intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,datas.getData().get(position).getMvdesc());

                        //地址类型 m3u8/kuyun
                        intent.putExtra(GlobalMsg.KEY_PLAY_TITLE, datas.getData().get(position).getDowndtitle());
                        //地址列表，title & url
                        intent.putExtra(GlobalMsg.KEY_PLAY_URL, datas.getData().get(position).getDownLoadUrl());
                        context.startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return datas.getData().size();
    }
}
