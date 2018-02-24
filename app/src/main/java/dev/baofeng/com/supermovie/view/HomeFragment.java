package dev.baofeng.com.supermovie.view;

import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HomeAdapter;
import dev.baofeng.com.supermovie.adapter.LAdapter;
import dev.baofeng.com.supermovie.adapter.VPadapter;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.BlurUtil;
import dev.baofeng.com.supermovie.utils.NetworkUtils;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment implements IMoview, BGARefreshLayout.BGARefreshLayoutDelegate {

    Unbinder unbinder;
    private static HomeFragment homeFragment;
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.bga_refresh)
    BGARefreshLayout bgaRefresh;
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
    private HomeAdapter adapter;
    private GetRecpresenter getRecpresenter;
    private MovieInfo info;
    private VPadapter vPadapter;

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
        appbar.addOnOffsetChangedListener(new MyOffsetChangedListener());
        toobar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            getContext().startActivity(intent);
        });
        downtask.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DownTaskActivity.class);
            getContext().startActivity(intent);
        });
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
        String[] type = {"war", "sci", "docu", "fun", "love", "scare", "act", "all"};
        getRecpresenter = new GetRecpresenter(getContext(), this);
        bgaRefresh.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        // 设置下拉刷新和上拉加载更多的风格
        bgaRefresh.setRefreshViewHolder(refreshViewHolder);
        getRecpresenter.getRecommend(type[0], 1, 22);
        getRecpresenter.getBtRecommend("2017", 3, 8);
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
    public void loadData(MovieInfo info) {
        this.info = info;
        rvlist.setLayoutManager(new GridLayoutManager(getContext(), 3));
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), info);
        rvlist.setAdapter(homeAdapter);
    }

    @Override
    public void loadError(String msg) {

    }

    @Override
    public void loadMore(MovieInfo result) {

    }

    @Override
    public void loadBtData(MovieInfo result) {
        vPadapter = new VPadapter(result, getContext());
        vp.setPageTransformer(false, new CustPagerTransformer(getContext()));
        LAdapter adapter = new LAdapter(getContext(), (ArrayList<MovieInfo.DataBean>) result.getData(), vp);

        vp.setAdapter(adapter);
        vp.setCurrentItem(3);
        Glide.with(getContext()).load(result.getData().get(3).getDownimgurl()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BlurUtil.setViewBg(5, 5, imgBg, resource);
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Glide.with(getContext()).load(result.getData().get(position).getDownimgurl()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        BlurUtil.setViewBg(5, 5, imgBg, resource);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void loadDetail(BtInfo result) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (NetworkUtils.isNetAvailable(getContext())) {
            //网络可用。异步加载后停止刷新
            // 加载完毕后在 UI 线程结束加载更多
            new Handler().postDelayed(() -> {
                bgaRefresh.endRefreshing();
            }, 2500);
        } else {
            // 网络不可用，结束下拉刷新
            Toast.makeText(getActivity(), "网络不可用", Toast.LENGTH_SHORT).show();
            bgaRefresh.endRefreshing();
        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        new Handler().postDelayed(() -> bgaRefresh.endLoadingMore(), 2000);
        return true;
    }
}
