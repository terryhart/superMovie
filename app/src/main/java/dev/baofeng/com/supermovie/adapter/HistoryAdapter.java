package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huangyong.downloadlib.domain.HistoryInfo;
import com.huangyong.playerlib.OnlinePlayerActivity;
import com.huangyong.playerlib.Params;
import com.huangyong.playerlib.PlayKey;
import com.huangyong.playerlib.PlayerActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.HistoryHolder;
import dev.baofeng.com.supermovie.holder.SearchHolder;
import dev.baofeng.com.supermovie.utils.DateTools;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

/**
 * 观看历史，点击是继续观看，不用进详情页
 * Created by huangyong on 2018/1/26.
 */

public class HistoryAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<HistoryInfo> info;

    public HistoryAdapter(Context context, List<HistoryInfo> info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String URLImg = info.get(position).getPostImgUrl();
        String name = info.get(position).getTitle();

        Glide.with(context).load(URLImg).into(((HistoryHolder) holder).itemimg);
        ((HistoryHolder) holder).itemtitle.setText(name);
        String progress = info.get(position).getProgress();

        ((HistoryHolder) holder).itemView.setOnClickListener(view -> {
            try {

                //在线资源，地址是网站，跳转在线播放
                if (Integer.parseInt(info.get(position).getContent_type()) == Params.CONTENT_M3U8) {

                    Intent intent = new Intent(context, OnlinePlayerActivity.class);
                    intent.putExtra(PlayKey.POSTER_IMG_KEY, URLImg);
                    intent.putExtra(PlayKey.PLAY_TITLE_KEY, name);
                    intent.putExtra(PlayKey.MOVIE_PROGRESS, progress);
                    intent.putExtra(PlayKey.PLAY_PATH_KEY, info.get(position).getLocalPath());
                    intent.putExtra(PlayKey.URL_MD5_KEY, info.get(position).getUrlMd5());
                    context.startActivity(intent);
                    Log.e("playurlmd5","urlmd5 key");
                }else {
                    //跳转本地播放

                    File file = new File(info.get(position).getLocalPath());
                    if (!file.exists()) {
                        Toast.makeText(context, "本地文件不存在，可能已删除", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.putExtra(GlobalMsg.KEY_POST_IMG, URLImg);
                    intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
                    intent.putExtra(Params.MOVIE_PROGRESS, progress);
                    intent.putExtra(Params.PROXY_PALY_URL, info.get(position).getLocalPath());
                    intent.putExtra(Params.URL_MD5_KEY, info.get(position).getUrlMd5());
                    context.startActivity(intent);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if (!TextUtils.isEmpty(progress)) {
            String formatDuring = DateTools.formatDuring(Long.parseLong(progress));
            ((HistoryHolder) holder).timeProgress.setText("已观看" + formatDuring);
        }

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
