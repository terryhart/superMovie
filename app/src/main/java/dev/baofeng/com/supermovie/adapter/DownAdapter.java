package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieBean;
import dev.baofeng.com.supermovie.holder.DownHolder;
import retrofit2.http.PUT;

/**
 * Created by huangyong on 2018/1/29.
 */

public class DownAdapter extends RecyclerView.Adapter<DownHolder> {

    private Context context;
    private MovieBean movieBean;

    public DownAdapter(Context context, MovieBean movieBean) {
        this.context = context;
        this.movieBean = movieBean;
    }

    @Override
    public DownHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.down_item,null);
        return new DownHolder(view);
    }

    @Override
    public void onBindViewHolder(DownHolder holder, int position) {

        if (movieBean.getDate().get(position).contains("ed2k")){
            holder.tvdown.setText("电驴链接"+(position+1));
            holder.tvdown.setOnClickListener(view -> {
                if (listener!=null){
                    listener.onItemclicks(movieBean.getDate().get(position));
                }
            });
        }else if (movieBean.getDate().get(position).contains("thunder")||movieBean.getDate().get(position).contains("magnet")){
            holder.tvdown.setText("磁力链接"+(position+1));
            holder.tvdown.setOnClickListener(view -> {
                if (listener!=null){
                    listener.onItemclicks(movieBean.getDate().get(position));
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return movieBean.getDate().size();
    }

    public interface onItemClick{
        void onItemclicks(String url);
        void onBaiduPanClick(String url);
    }
    private onItemClick listener;
    public void setOnItemClickListener(onItemClick listener){
        this.listener = listener;
    }
}
