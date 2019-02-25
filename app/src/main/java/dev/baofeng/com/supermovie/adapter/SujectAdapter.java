package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.SubjectInfo;
import dev.baofeng.com.supermovie.domain.SubjectTitleInfo;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.SubjectHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SujectAdapter extends RecyclerView.Adapter {
    private Context context;
    private SubjectInfo info;

    private String[] imgArr = {

    };

    public SujectAdapter(Context context, SubjectInfo info) {
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

        ((CommonHolder) holder).itemtitle.setText(info.getData().get(position).getDownLoadName());
        String URLImg= info.getData().get(position).getDownimgurl();
        String name = info.getData().get(position).getDownLoadName();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_dl_magnet_place_holder);
        Glide.with(context).load(URLImg.split(",")[0]).apply(requestOptions).into(((CommonHolder) holder).itemimg);

        ((CommonHolder)holder).itemtitle.setText(name);
        ((CommonHolder) holder).itemView.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(GlobalMsg.KEY_POST_IMG, URLImg);
                intent.putExtra(GlobalMsg.KEY_DOWN_URL,info.getData().get(position).getDownLoadUrl());
                intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
                intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, info.getData().get(position).getDowndtitle());
                intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,info.getData().get(position).getMvdesc());
                context.startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }

        });

    }

    @Override
    public int getItemCount() {
        return info.getData().size();
    }


}
