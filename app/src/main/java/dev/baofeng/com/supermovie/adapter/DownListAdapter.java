package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.DetailInfo;
import dev.baofeng.com.supermovie.holder.DownHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.ListHeadHolder;
import dev.baofeng.com.supermovie.view.OnItemClickListenr;

public class DownListAdapter extends RecyclerView.Adapter {

    private OnItemClickListenr listenr;
    private ArrayList<DetailInfo> infos;
    private Context context;
    private String[] downItemList;
    public DownListAdapter(String[] downItemList, ArrayList<DetailInfo> infos, OnItemClickListenr listener) {
        this.infos = infos;
        this.listenr = listener;
        this.downItemList = downItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType==1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.down_list_head_item,null);
            return new ListHeadHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.down_item,null);
            return new DownHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (infos!=null&&infos.size()>0){
            if (holder instanceof ListHeadHolder){

            }
            if (holder instanceof DownHolder){
                String downUrl = infos.get(0).getDownUrl().get(position-1);
                if (downUrl.contains("ed2k")){
                    ((DownHolder) holder).tvdown.setText("电驴下载"+"\n"+downItemList[position-1]);
                    Glide.with(context).load(R.drawable.share_ic_task_file_mp4).into(((DownHolder) holder).donwIconPic);
                }else if (downUrl.contains("magnet")){
                    ((DownHolder) holder).tvdown.setText("磁力下载"+"\n"+downItemList[position-1]);
                    Glide.with(context).load(R.drawable.ic_dl_torrent).into(((DownHolder) holder).donwIconPic);
                }else if (downUrl.contains("thunder")){
                    ((DownHolder) holder).tvdown.setText("迅雷下载"+"\n"+downItemList[position-1]);
                    Glide.with(context).load(R.drawable.share_ic_task_file_mp4).into(((DownHolder) holder).donwIconPic);
                }

                ((DownHolder) holder).downIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listenr !=null){
                            listenr.clicked(infos.get(0).getDownUrl().get(position-1),infos.get(0).getImgUrl());
                        }
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return 1+infos.get(0).getDownUrl().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 1;
        }else {
            return 2;
        }
    }
}
