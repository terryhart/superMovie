package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.huangyong.downloadlib.domain.FavorInfo;

import java.util.List;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.holder.FavorHolder;
import dev.baofeng.com.supermovie.http.UrlConfig;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;
import dev.baofeng.com.supermovie.view.online.detail.OnlineDetailPageActivity;

/**
 * 观看历史，点击是继续观看，不用进详情页
 * Created by huangyong on 2018/1/26.
 */

public class FavorAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<FavorInfo> info;
    private onLongClickedListener listener;
    private String classType;

    public FavorAdapter(Context context, List<FavorInfo> info, onLongClickedListener listener) {
        this.context = context;
        this.info = info;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favor, parent, false);
        return new FavorHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String URLImg = info.get(position).getPostImgUrl();
        String name = info.get(position).getTitle();

        Glide.with(context).load(URLImg).into(((FavorHolder) holder).itemimg);
        ((FavorHolder) holder).itemtitle.setText(name);

        //这里区分两个类型，在线和离线，因为两个类型到详情页是不同的，而且还需要其他类型
        ((FavorHolder) holder).itemView.setOnClickListener(view -> {

            if (info.get(position).getContent_type() != null && info.get(position).getContent_type().equals(GlobalMsg.CONTENT_M3U8)) {


                Intent intent = new Intent(context, OnlineDetailPageActivity.class);
                intent.putExtra(GlobalMsg.KEY_POST_IMG, URLImg);
                intent.putExtra(GlobalMsg.KEY_DOWN_URL, info.get(position).getDownload_url());
                intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
                intent.putExtra(GlobalMsg.KEY_MV_TYPE, info.get(position).getMovie_type());
                intent.putExtra(GlobalMsg.KEY_IS_MOVIE, info.get(position).getIs_movie());
                //简介
                intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL, info.get(position).getMovieDesc());

                //地址类型 m3u8/kuyun
                intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, info.get(position).getDownItemTitle());
                //地址列表，title & url
                intent.putExtra(GlobalMsg.KEY_PLAY_URL, info.get(position).getTaskUrl());
                context.startActivity(intent);


            } else {
                try {
                    Intent intent = new Intent(context, MovieDetailActivity.class);
                    intent.putExtra(GlobalMsg.KEY_POST_IMG, URLImg);
                    intent.putExtra(GlobalMsg.KEY_DOWN_URL, info.get(position).getTaskUrl());
                    intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
                    intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, info.get(position).getDownItemTitle());
                    intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL, info.get(position).getMovieDesc());
                    intent.putExtra(GlobalMsg.KEY_MV_ID, info.get(position).getMv_md5_id());
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onLongClick(info.get(position).getId() + "");
                }
                return false;
            }
        });
    }

    public interface onLongClickedListener {
        void onLongClick(String id);
    }

    @Override
    public int getItemCount() {
        return info.size();
    }


    public void clear() {
        if (info.size() > 0)
            info.clear();
    }
}
