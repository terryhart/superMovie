package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.HomeHeadHolder;
import dev.baofeng.com.supermovie.holder.SecondHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

/**
 * Created by huangyong on 2018/2/11.
 */

public class CategoryAdapter extends RecyclerView.Adapter {
    private Context context;
    private RecentUpdate datas;

    public CategoryAdapter(Context context, RecentUpdate datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType== GlobalMsg.ITEM_TYPE_1){
            View view = LayoutInflater.from(context).inflate(R.layout.home_head_layout,parent,false);
            return new HomeHeadHolder(view);
        }else if(viewType==GlobalMsg.ITEM_TYPE_2){
            View view = LayoutInflater.from(context).inflate(R.layout.second_item,parent,false);
            return new SecondHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_main,parent,false);
            return new CommonHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadHolder){

        }
        if (holder instanceof CommonHolder){

            String imgUrl = datas.getData().get(position-1).getDownimgurl();
            String name = datas.getData().get(position-1).getDownLoadName();
            String downItemTitle = datas.getData().get(position-1).getDowndtitle();

            String posterImgUrl= imgUrl.split(",")[0];
            Uri uri = Uri.parse(posterImgUrl);
            Glide.with(context).load(uri).asBitmap().placeholder(R.drawable.ic_place_hoder).override(180,240).into(((CommonHolder)holder).itemimg);

            ((CommonHolder)holder).itemtitle.setText(name);

            String finalImgUrl = imgUrl;
            String finalName = name;
            ((CommonHolder) holder).itemimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
//                        Intent intent = new Intent(context, DetailActivity.class);
                        Intent intent = new Intent(context, MovieDetailActivity.class);
                        intent.putExtra(GlobalMsg.KEY_POST_IMG, finalImgUrl);
                        intent.putExtra(GlobalMsg.KEY_DOWN_URL,datas.getData().get(position-1).getDownLoadUrl());
                        intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, finalName);
                        intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, downItemTitle);
                        intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,datas.getData().get(position-1).getMvdesc());
                        context.startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (position==0){
            return GlobalMsg.ITEM_TYPE_1;
        }else {
            return GlobalMsg.ITEM_TYPE_3;
        }
    }

    @Override
    public int getItemCount() {
        return datas.getData().size()+1;
    }
}
