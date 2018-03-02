package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.holder.DownHolder;

/**
 * Created by huangyong on 2018/1/29.
 */

public class BtDownAdapter extends RecyclerView.Adapter<DownHolder> {

    private Context context;
    private BtInfo info;

    public BtDownAdapter(Context context, BtInfo movieBean) {
        this.context = context;
        this.info = movieBean;
    }

    @Override
    public DownHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.down_item,null);
        return new DownHolder(view);
    }

    @Override
    public void onBindViewHolder(DownHolder holder, int position) {

        holder.tvdown.setText("BT下载"+(position+1));
        holder.tvdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null){
                    listener.onItemclicks(position,info.getData().get(position).getDownParam());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return info.getData().size();
    }

    public interface onItemClick{
        void onItemclicks(int position, String url);
    }
    private onItemClick listener;
    public void setOnItemClickListener(onItemClick listener){
        this.listener = listener;
    }
}
