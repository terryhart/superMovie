package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.CommonHolder;

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
        Glide.with(context).load(info.getData().get(position).getDownimgurl()).into(((CommonHolder)holder).itemimg);
        Log.d("HHAHHDHHDH",info.getData().get(position).getDownimgurl());
        ((CommonHolder)holder).itemtitle.setText(info.getData().get(position).getDownLoadName());
    }

    @Override
    public int getItemCount() {
        return info.getData().size();
    }


}
