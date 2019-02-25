package dev.baofeng.com.supermovie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.SecondHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.online.detail.OnlineDetailPageActivity;

/**
 * Created by huangyong on 2018/2/11.
 */

public class OnlineCategoryAdapter extends RecyclerView.Adapter {
    private Activity context;
    private OnlinePlayInfo datas;
    private String type;
    private int isMV;
    private int color;

    public OnlineCategoryAdapter(Activity context, OnlinePlayInfo datas, String type, int isMovie) {
        this.context = context;
        this.datas = datas;
        this.type = type;
        this.isMV = isMovie;
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

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_dl_magnet_place_holder);
            Glide.with(context).load(imgUrl).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(((CommonHolder) holder).itemimg);

            ((CommonHolder)holder).itemtitle.setText(name);

            ((CommonHolder) holder).itemimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        toDetailPage(imgUrl, position, name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void toDetailPage(String imgUrl, int position, String name) {
        Intent intent = new Intent(context, OnlineDetailPageActivity.class);
        intent.putExtra(GlobalMsg.KEY_POST_IMG, imgUrl);
        intent.putExtra(GlobalMsg.KEY_DOWN_URL, datas.getData().get(position).getDownLoadUrl());
        intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
        intent.putExtra(GlobalMsg.KEY_MV_TYPE, type);
        intent.putExtra(GlobalMsg.KEY_IS_MOVIE, isMV);
        //简介
        intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL, datas.getData().get(position).getMvdesc());

        intent.putExtra(GlobalMsg.KEY_BLUR_COLOR, color);
        //地址类型 m3u8/kuyun
        intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, datas.getData().get(position).getDowndtitle());
        //地址列表，title & url
        intent.putExtra(GlobalMsg.KEY_PLAY_URL, datas.getData().get(position).getDownLoadUrl());
        context.startActivity(intent);
    }

    @Override
    public int getItemViewType(int position) {
        return GlobalMsg.ITEM_TYPE_3;
    }

    @Override
    public int getItemCount() {
        return datas.getData().size();
    }


}
