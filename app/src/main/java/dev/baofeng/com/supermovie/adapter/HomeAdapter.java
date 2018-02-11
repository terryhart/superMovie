package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.util.ArrayList;
import java.util.Collection;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieBean;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.SecondHolder;
import dev.baofeng.com.supermovie.view.DownActivity;
import dev.baofeng.com.supermovie.view.GlobalMsg;

/**
 * Created by huangyong on 2018/2/11.
 */

public class HomeAdapter extends RecyclerView.Adapter {
    private Context context;
    private MovieInfo datas;

    public HomeAdapter(Context context,MovieInfo datas) {
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
            Uri uri = Uri.parse(datas.getData().get(position).getDownimgurl());
            Glide.with(context).load(uri).asBitmap().override(180,240).priority(Priority.LOW).into(((CommonHolder)holder).itemimg);

            ((CommonHolder)holder).itemtitle.setText(datas.getData().get(position).getDownLoadName());

            ((CommonHolder) holder).itemimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(context, DownActivity.class);
                        intent.putExtra(GlobalMsg.KEY_POST_IMG,datas.getData().get(position).getDownimgurl());
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
