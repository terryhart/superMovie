package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.DetailInfo;
import dev.baofeng.com.supermovie.holder.DownHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.OnlineHolder;
import dev.baofeng.com.supermovie.view.OnItemClickListenr;

public class OnlineDetailAdapter extends RecyclerView.Adapter {

    private OnItemClickListenr listenr;
    private ArrayList<DetailInfo> infos;
    private Context context;
    private String[] downItemList;
    public OnlineDetailAdapter(String[] downItemList, ArrayList<DetailInfo> infos, OnItemClickListenr listener) {
        this.infos = infos;
        this.listenr = listener;
        this.downItemList = downItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType==1){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.head_item,null);
            return new HeadHolder(view);
        }else if(viewType==2){
            //在线播放
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_item,null);
            return new OnlineHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.down_item,null);
            return new DownHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (infos!=null&&infos.size()>0){
            Log.e("dkkddkdk",infos.size()+"");
            if (holder instanceof HeadHolder){
                ((HeadHolder) holder).mvdesc.setContent(infos.get(0).getMvDesc());
                String screenShotImagUrl = infos.get(0).getImgScreenShot();
                if (!TextUtils.isEmpty(screenShotImagUrl)){
                    ((HeadHolder) holder).screenShot.setVisibility(View.VISIBLE);
                    Glide.with(context).load(infos.get(0).getImgScreenShot()).into(((HeadHolder) holder).screenShot);
                }else {
                    ((HeadHolder) holder).screenShot.setVisibility(View.INVISIBLE);
                }

            }
            if (holder instanceof DownHolder){
                String downUrl = infos.get(0).getDownUrl().get(position-2);
                if (downUrl.contains("ed2k")){
                    ((DownHolder) holder).tvdown.setText("电驴下载"+"\n"+downItemList[position-2]);
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
            if (holder instanceof OnlineHolder){
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2+infos.get(0).getDownUrl().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 1;
        }else if(position==1) {
            return 2;
        }else {
            return 3;
        }
    }
}
