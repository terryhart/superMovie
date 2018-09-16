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
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.SecondHolder;
import dev.baofeng.com.supermovie.view.DetailActivity;
import dev.baofeng.com.supermovie.view.GlobalMsg;

/**
 * Created by huangyong on 2018/2/11.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private Context context;
    private RecentUpdate datas;

    public HomeAdapter(Context context,RecentUpdate datas) {
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
            name.substring(name.indexOf("《")+1,name.indexOf("》"));
            imgUrl= imgUrl.substring(0,imgUrl.indexOf("jpg")+3);
            Uri uri = Uri.parse(imgUrl);
            Glide.with(context).load(uri).asBitmap().override(180,240).into(((CommonHolder)holder).itemimg);

            ((CommonHolder)holder).itemtitle.setText(name);

            String finalImgUrl = imgUrl;
            ((CommonHolder) holder).itemimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra(GlobalMsg.KEY_POST_IMG, finalImgUrl);
                        intent.putExtra(GlobalMsg.KEY_DOWN_URL,datas.getData().get(position).getDownLoadUrl());
                        intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE,datas.getData().get(position).getDownLoadName());
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
