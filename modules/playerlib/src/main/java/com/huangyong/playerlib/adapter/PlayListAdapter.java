package com.huangyong.playerlib.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huangyong.playerlib.CustomControler;
import com.huangyong.playerlib.R;
import com.huangyong.playerlib.model.M3u8Bean;

import java.util.List;

/**
 * creator huangyong
 * createTime 2019/4/3 下午1:26
 * path com.huangyong.playerlib.adapter
 * description:
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayHolder> {


    private List<M3u8Bean> infos;
    private Context context;
    private CustomControler.OnItemClickedListener clickedListener;

    public PlayListAdapter(List<M3u8Bean> mPlayList, Context activity, CustomControler.OnItemClickedListener clickedListener) {
        this.infos = mPlayList;
        this.context = activity;
        this.clickedListener = clickedListener;
    }


    @NonNull
    @Override
    public PlayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_list_item, null);
        return new PlayHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayHolder holder, final int position) {
        holder.index.setText((position+1)+"");
        holder.index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedListener.clicked(infos.get(position).getUrl());
                holder.index.setSelected(true);
                CustomControler.CurrentIndex = position;
                for (int i = 0; i < infos.size(); i++) {
                    if (i==position){
                    }else {
                        holder.index.setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });

        if (CustomControler.CurrentIndex ==position){
            holder.index.setSelected(true);
        }else {
            holder.index.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return infos.size() > 0 ? infos.size() : 0;
    }
}
