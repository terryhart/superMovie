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

import java.util.List;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.DoubanTop250;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.holder.SearchHolder;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;

/**
 * Created by huangyong on 2018/1/26.
 */

public class DoubanAdapter extends RecyclerView.Adapter {
    private Context context;
    private DoubanTop250 info;

    public DoubanAdapter(Context context, DoubanTop250 info) {
        this.context = context;
        this.info = info;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String posterImg = info.getSubjects().get(position).getImages().getMedium();
        String name = info.getSubjects().get(position).getTitle();
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_dl_magnet_place_holder);
        Glide.with(context).load(posterImg).apply(requestOptions).into(((SearchHolder) holder).itemimg);

        ((SearchHolder) holder).itemtitle.setText(name);

        ((SearchHolder) holder).root.setOnClickListener(view -> {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        List<DoubanTop250.SubjectsBean.DirectorsBean> directors = info.getSubjects().get(position).getDirectors();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < directors.size(); i++) {
            builder.append(directors.get(i).getName()).append(" / ");
        }

        String mvdesc = builder.toString() + "\n" + info.getSubjects().get(position).getRating().getAverage();
        ((SearchHolder) holder).desc.setText(mvdesc);
    }

    @Override
    public int getItemCount() {
        return info.getSubjects().size();
    }


    public void clear() {
        if (info.getSubjects().size() > 0)
            info.getSubjects().clear();
    }

    public interface onLongClickedListener {
        void onLongClick(String id);
    }
}
