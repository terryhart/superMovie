package dev.baofeng.com.supermovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.SecondHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by huangyong on 2018/2/11.
 */

public class BTcategoryAdapter extends RecyclerView.Adapter {
    private Context context;
    private RecentUpdate datas;
    private static final String VIEW_NAME_HEADER_IMAGE = "image";
    private static final String VIEW_NAME_HEADER_TITLE = "title";
    public BTcategoryAdapter(Context context, RecentUpdate datas) {
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
            String downItemTitle = datas.getData().get(position).getDowndtitle();
            String md5Id = datas.getData().get(position).getMv_md5_id();
            String posterImgUrl= imgUrl.split(",")[0];
            Uri uri = Uri.parse(posterImgUrl);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.place_holder);
            Glide.with(context).load(uri).transition(DrawableTransitionOptions.withCrossFade(300)).apply(requestOptions).into(((CommonHolder) holder).itemimg);

            ((CommonHolder)holder).itemtitle.setText(name);

            String finalImgUrl = imgUrl;
            String finalName = name;
            ((CommonHolder) holder).itemimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent intent = new Intent();
                        intent.putExtra(GlobalMsg.KEY_POST_IMG, finalImgUrl);
                        intent.putExtra(GlobalMsg.KEY_DOWN_URL,datas.getData().get(position).getDownLoadUrl());
                        intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, finalName);
                        intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, downItemTitle);
                        intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,datas.getData().get(position).getMvdesc());
                        intent.putExtra(GlobalMsg.KEY_MV_ID, md5Id);
                        intent.setClass(context, MovieDetailActivity.class);
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
