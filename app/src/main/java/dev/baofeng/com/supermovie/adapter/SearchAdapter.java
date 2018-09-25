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
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.FormatInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.SearchHolder;
import dev.baofeng.com.supermovie.view.BtDownActivity;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SearchAdapter extends RecyclerView.Adapter {
    private Context context;
    private MovieInfo info;

    public SearchAdapter(Context context, MovieInfo info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search,parent,false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String URLImg= info.getData().get(position).getDownimgurl().split(",")[0];
        Log.e("slldldldld",URLImg);
        String name = info.getData().get(position).getDownLoadName();
        if (name.contains("片名")){
            name=name.replace("片名","");
        }
        if (name.contains("：")){
            name= name.replace("：","");
        }
        if (name.contains(":")){
            name= name.replace(":","");
            name= name.trim();
        }
       String names=  name.substring(name.indexOf("《")+1,name.indexOf("》"));

        Glide.with(context).load(URLImg).into(((SearchHolder)holder).itemimg);

        ((SearchHolder)holder).itemtitle.setText(info.getData().get(position).getDownLoadName());

        ((SearchHolder) holder).root.setOnClickListener(view -> {
           try {
               Intent intent = new Intent(context, MovieDetailActivity.class);
               intent.putExtra(GlobalMsg.KEY_POST_IMG, URLImg);
               intent.putExtra(GlobalMsg.KEY_DOWN_URL,info.getData().get(position).getDownLoadUrl());
               intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, names);
               intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,info.getData().get(position).getMvdesc());
               context.startActivity(intent);

           }catch (Exception e){
               e.printStackTrace();
           }
        });
        String mvdesc = info.getData().get(position).getMvdesc();
        ((SearchHolder) holder).desc.setText(mvdesc);
    }

    @Override
    public int getItemCount() {
        return info.getData().size();
    }


    public void clear() {
        if (info.getData().size()>0)
        info.getData().clear();
    }
}
