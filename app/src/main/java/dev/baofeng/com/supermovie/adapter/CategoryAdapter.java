package dev.baofeng.com.supermovie.adapter;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.holder.CommonHolder;
import dev.baofeng.com.supermovie.holder.HeadHolder;
import dev.baofeng.com.supermovie.holder.HomeHeadHolder;
import dev.baofeng.com.supermovie.holder.SecondHolder;
import dev.baofeng.com.supermovie.view.BaseAnimation;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.MovieDetailActivity;
import dev.baofeng.com.supermovie.view.widget.SlideInRightAnimation;

/**
 * Created by huangyong on 2018/2/11.
 */

public class CategoryAdapter extends RecyclerView.Adapter {
    private static final String VIEW_NAME_HEADER_IMAGE = "image";
    private static final String VIEW_NAME_HEADER_TITLE = "title";
    private Context context;
    private RecentUpdate datas;

    private int mLastPosition = -1;
    private int mPosition = 0;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 500;
    private BaseAnimation mSelectAnimation = new SlideInRightAnimation();
    private boolean mOpenAnimationEnable = true;
    private final String time;


    public CategoryAdapter(Context context, RecentUpdate datas) {
        this.context = context;
        this.datas = datas;
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
        Date ss = new Date();
        time = format0.format(ss.getTime());
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

            String imgUrl = datas.getData().get(position).getDownimgurl();
            String name = datas.getData().get(position).getDownLoadName();
            String downItemTitle = datas.getData().get(position).getDowndtitle();
            String updateTime = datas.getData().get(position).getMv_update_time();
            if (time.equals(updateTime)){
                ((CommonHolder) holder).itemTag.setVisibility(View.VISIBLE);
            }else {
                ((CommonHolder) holder).itemTag.setVisibility(View.INVISIBLE);
            }
            Log.e("updateTime",updateTime);

            String posterImgUrl= imgUrl.split(",")[0];
            Uri uri = Uri.parse(posterImgUrl);
            Glide.with(context).load(uri).placeholder(R.drawable.ic_place_hoder).into(((CommonHolder) holder).itemimg);

            ((CommonHolder)holder).itemtitle.setText(name);

            String finalImgUrl = imgUrl;
            String finalName = name;
            ((CommonHolder) holder).itemimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent();
                        intent.putExtra(GlobalMsg.KEY_POST_IMG, finalImgUrl);
                        intent.putExtra(GlobalMsg.KEY_DOWN_URL,datas.getData().get(position).getDownLoadUrl());
                        intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE, finalName);
                        intent.putExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE, downItemTitle);
                        intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,datas.getData().get(position).getMvdesc());
                        intent.putExtra(GlobalMsg.KEY_MV_ID, datas.getData().get(position).getMv_md5_id());
                        intent.setClass(context, MovieDetailActivity.class);

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

        return GlobalMsg.ITEM_TYPE_3;
    }

    @Override
    public int getItemCount() {
        return datas.getData().size();
    }


    /**
     * 开启动画
     * @param animator
     */
    private void startAnim(Animator animator) {
        animator.setDuration(mDuration).start();
        animator.setInterpolator(mInterpolator);
    }


    /**
     * 设置动画效果
     * @param animation
     */
    public void setAnimation(BaseAnimation animation){
        this.mOpenAnimationEnable = true;
        this.mSelectAnimation = animation;
    }

    /**
     * 添加动画
     * @param holder
     */
    public void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mSelectAnimation != null) {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim);

                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }


}
