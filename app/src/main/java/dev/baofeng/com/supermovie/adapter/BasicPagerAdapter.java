package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;
import dev.baofeng.com.supermovie.view.PosterItemView;

/**
 * <p>
 *
 * @author cpacm 2018/5/18
 */
public class BasicPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<RecentUpdate.DataBean> dataBeans;
    public BasicPagerAdapter(ArrayList<RecentUpdate.DataBean> dataBeans, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.dataBeans = dataBeans;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);
        View view = layoutInflater.inflate(R.layout.viewpager_item, container, false);
        PosterItemView iv =view.findViewById(R.id.banner);
        TextView title =view.findViewById(R.id.movtitle);
        iv.setImageResource(R.drawable.poster);
        String url=dataBeans.get(position).getDownimgurl().split(",")[0];
        Glide.with(container.getContext()).load(url).placeholder(R.drawable.ic_dl_magnet_place_holder).into(iv);
        title.setText(dataBeans.get(position).getDownLoadName());
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(container.getContext(), MovieDetailActivity.class);
                    intent.putExtra(GlobalMsg.KEY_POST_IMG, url);
                    intent.putExtra(GlobalMsg.KEY_DOWN_URL,dataBeans.get(position).getDownLoadUrl());
                    intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, dataBeans.get(position).getDownLoadName());
                    intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, dataBeans.get(position).getDowndtitle());
                    intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,dataBeans.get(position).getMvdesc());
                    container.getContext().startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        if (dataBeans==null){
            return 6;
        }else return dataBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    public void setData(ArrayList<RecentUpdate.DataBean> resultData) {
        this.dataBeans.clear();
        this.dataBeans.addAll(resultData);
    }
}
