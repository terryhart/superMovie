package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.FavorHolder;
import dev.baofeng.com.supermovie.holder.SubjectHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SujectAdapter extends RecyclerView.Adapter {
    private Context context;
    private MovieInfo info;

    public SujectAdapter(Context context, MovieInfo info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_suject,parent,false);
        return new SubjectHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Glide.with(context).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539401332876&di=efa79d6f4894a6a033a35864ee00ed2d&imgtype=0&src=http%3A%2F%2Fpic36.photophoto.cn%2F20150729%2F0008020929291143_b.jpg").into(((SubjectHolder)holder).itemimg);
        ((SubjectHolder) holder).itemtitle.setText("奥斯卡获奖电影合集");
       /* String URLImg= info.getData().get(position).getDownimgurl();
        String name = info.getData().get(position).getDownLoadName();


        ((FavorHolder)holder).itemtitle.setText(name);
        ((FavorHolder) holder).root.setOnClickListener(view -> {
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

        });*/
    }

    @Override
    public int getItemCount() {
        return 10;
    }


}
