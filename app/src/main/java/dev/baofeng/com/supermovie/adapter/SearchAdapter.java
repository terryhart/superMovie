package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.SearchHolder;
import dev.baofeng.com.supermovie.view.FavorActivity;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

/**
 * Created by huangyong on 2018/1/26.
 */

public class SearchAdapter extends RecyclerView.Adapter {
    private Context context;
    private MovieInfo info;
    private onLongClickedListener longClickListener;

    public SearchAdapter(Context context, MovieInfo info, onLongClickedListener onLongClickListener) {
        this.context = context;
        this.info = info;
        this.longClickListener = onLongClickListener;
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
        String downItemTitle = info.getData().get(position).getDowndtitle();

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_dl_magnet_place_holder);
        Glide.with(context).load(URLImg).apply(requestOptions).into(((SearchHolder) holder).itemimg);

        ((SearchHolder)holder).itemtitle.setText(info.getData().get(position).getDownLoadName());

        ((SearchHolder) holder).root.setOnClickListener(view -> {
           try {
               Intent intent = new Intent(context, MovieDetailActivity.class);
               intent.putExtra(GlobalMsg.KEY_POST_IMG, URLImg);
               intent.putExtra(GlobalMsg.KEY_DOWN_URL,info.getData().get(position).getDownLoadUrl());
               intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, name);
               intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, downItemTitle);
               intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,info.getData().get(position).getMvdesc());
               intent.putExtra(GlobalMsg.KEY_MV_ID, info.getData().get(position).getMv_md5_id());
               context.startActivity(intent);

           }catch (Exception e){
               e.printStackTrace();
           }
        });
        ((SearchHolder) holder).root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onLongClick(info.getData().get(position).getId());
                }
                return true;
            }
        });
        String mvdesc = info.getData().get(position).getMvdesc();
        ((SearchHolder) holder).desc.setText(mvdesc);
    }

    @Override
    public int getItemCount() {
        return info == null ? 0 : info.getData().size();
    }


    public void clear() {
        if (info.getData().size()>0)
        info.getData().clear();
    }

    public void setData(MovieInfo info) {
        this.info.getData().addAll(info.getData());
        notifyDataSetChanged();
    }

    public interface onLongClickedListener {
        void onLongClick(String id);
    }
}
