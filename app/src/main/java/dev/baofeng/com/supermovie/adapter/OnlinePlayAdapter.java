package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.huangyong.playerlib.PlayerActivity;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.BtPlayInfo;
import dev.baofeng.com.supermovie.domain.DetailInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.PlayListHolder;

public class OnlinePlayAdapter extends RecyclerView.Adapter<PlayListHolder> {

    private ArrayList<BtPlayInfo> playList;

    private Context context;

    public OnlinePlayAdapter(ArrayList<BtPlayInfo> playList) {
        this.playList = playList;
    }

    @NonNull
    @Override
    public PlayListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context= parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_play_item,null);
        return new PlayListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayListHolder holder, int position) {
        holder.btPlayText.setText(playList.get(position).getMovName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
                Toast.makeText(context, "即将跳转", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, PlayerActivity.class);
                intent.putExtra(Params.PROXY_PALY_URL,playList.get(position).getMovPlayUrl());
                intent.putExtra(Params.URL_MD5_KEY, MD5Utils.stringToMD5(playList.get(position).getMovPlayUrl()));
                intent.putExtra(Params.POST_IMG_KEY,playList.get(position).getMovPoster());
                intent.putExtra(Params.TASK_TITLE_KEY,playList.get(position).getMovTitle());
                intent.putExtra(Params.MOVIE_PROGRESS,"0");
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        if (playList!=null){
            return playList.size();
        }else {
            return 0;
        }
    }
}
