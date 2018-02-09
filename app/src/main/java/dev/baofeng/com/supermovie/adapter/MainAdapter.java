package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.view.DownActivity;
import dev.baofeng.com.supermovie.view.GlobalMsg;

/**
 * Created by huangyong on 2018/1/26.
 */

public class MainAdapter extends RecyclerView.Adapter {
    private Context context;
    private MovieInfo info;

    public MainAdapter(Context context, MovieInfo info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main,parent,false);
        return new CommonHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Uri uri = Uri.parse(info.getData().get(position).getDownimgurl());
        Glide.with(context).load(uri).asBitmap().override(180,240).priority(Priority.LOW).into(((CommonHolder)holder).itemimg);

        ((CommonHolder)holder).itemtitle.setText(info.getData().get(position).getDownLoadName());

        ((CommonHolder) holder).itemimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                   Intent intent = new Intent(context, DownActivity.class);
                   intent.putExtra(GlobalMsg.KEY_POST_IMG,info.getData().get(position).getDownimgurl());
                   intent.putExtra(GlobalMsg.KEY_DOWN_URL,info.getData().get(position).getDownLoadUrl());
                   intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE,info.getData().get(position).getDownLoadName());
                   context.startActivity(intent);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.getData().size();
    }


}
