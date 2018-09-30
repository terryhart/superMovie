package dev.baofeng.com.supermovie.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.BlurUtil;
import com.xiaosu.pulllayout.SimplePullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HomeAdapter;
import dev.baofeng.com.supermovie.adapter.LAdapter;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.ImgUtils;
import dev.baofeng.com.supermovie.utils.MyTransformation;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment implements IMoview,  BasePullLayout.OnPullCallBackListener, ViewPager.OnPageChangeListener {

    Unbinder unbinder;
    private static HomeFragment homeFragment;
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.toobar)
    Toolbar toobar;
    @BindView(R.id.square)
    ImageView square;
    @BindView(R.id.downtask)
    ImageView downtask;
    @BindView(R.id.pulllayout)
    SimplePullLayout pulllayout;
    private GetRecpresenter getRecpresenter;
    private RecentUpdate info;
    private int index;
    private HomeAdapter homeAdapter;
    private RecentUpdate bannerInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.channel_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        pulllayout.postRefresh();

        appbar.addOnOffsetChangedListener(new MyOffsetChangedListener());
        toobar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            getContext().startActivity(intent);
        });
        downtask.setOnClickListener(v -> {
           Intent intent =new Intent(getContext(), DownLoadMainActivity.class);
           startActivity(intent);
        });
    }
    private OnDownPageListener listener;
    public void setOnDownPageListener(OnDownPageListener onDownPageListener) {
        this.listener = onDownPageListener;
    }

    public void autoRefresh(){
        if (pulllayout!=null){
            pulllayout.postRefresh();
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRecpresenter.getRecentUpdate(1,20);
                pulllayout.finishPull("加载完成",true);
            }
        },2000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRecpresenter.getMoreData(++index,20);
                pulllayout.finishPull("加载完成",true);
            }
        },2000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int i) {
        if (bannerInfo!=null&&bannerInfo.getData().size()>0){
            int currentItem = i;
            String poster = bannerInfo.getData().get(currentItem).getDownimgurl().split(",")[0];
            Glide.with(getContext()).load(poster).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Bitmap reverseBitmapById = BlurUtil.getBlurBitmap(4,4,resource);
                    imgBg.setImageBitmap(reverseBitmapById);
                }
            });
        }

        int pageIndex = i;

        if (i == 0) {
            // 当视图在第一个时，将页面号设置为图片的最后一张。
            pageIndex = 10;
        } else if (i == 10 + 1) {
            // 当视图在最后一个是,将页面号设置为图片的第一张。
            pageIndex = 1;
        }
        if (i != pageIndex) {
            vp.setCurrentItem(pageIndex, false);
            return;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    private class MyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            float progress = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
            if (progress >= 0.4) {
                toobar.setVisibility(View.VISIBLE);
                toobar.setAlpha(progress);
            } else {
                toobar.setVisibility(View.VISIBLE);
                toobar.setAlpha(0.4f);
            }
        }
    }

    private void initData() {
        pulllayout.setOnPullListener(this);
        index = 1;
        getRecpresenter = new GetRecpresenter(getContext(), this);
        getRecpresenter.getRecentUpdate( index, 22);
        getRecpresenter.getBtRecommend(1,10);
    }

    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        } else {
            return homeFragment;
        }
        return homeFragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }





    @Override
    public void loadData(RecentUpdate info) {
        this.info = info;
        rvlist.setLayoutManager(new GridLayoutManager(getContext(), 3));
        homeAdapter = new HomeAdapter(getContext(), info);
        rvlist.setAdapter(homeAdapter);
    }

    @Override
    public void loadError(String msg) {

    }


    @Override
    public void loadMore(RecentUpdate result) {
        info.getData().addAll(result.getData());
        homeAdapter.notifyDataSetChanged();
    }
    @Override
    public void loadBtData(RecentUpdate result) {
        this.bannerInfo = result;

        ArrayList<RecentUpdate.DataBean> resultData = (ArrayList<RecentUpdate.DataBean>) result.getData();
        LAdapter lAdapter = new LAdapter(getContext(),resultData,vp);

        int pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 5.0f);
        ViewGroup.LayoutParams lp=vp.getLayoutParams();
        if (lp==null){
            lp=new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
            lp.width= pagerWidth;
        }
        vp.setLayoutParams(lp);
        vp.setPageTransformer(true,new MyTransformation());
        vp.setOffscreenPageLimit(3);
        vp.setPageMargin(-250);
        vp.setOnPageChangeListener(this);
        vp.setAdapter(lAdapter);
        vp.setCurrentItem(5);

    }
    @Override
    public void loadDetail(BtInfo result) {

    }

}
