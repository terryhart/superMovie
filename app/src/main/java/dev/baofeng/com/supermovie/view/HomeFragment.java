package dev.baofeng.com.supermovie.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.view.IndexActivity;
import com.bftv.myapplication.view.LineWebview;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.utils.BlurUtil;
import com.leochuan.AutoPlayRecyclerView;
import com.leochuan.CarouselLayoutManager;
import com.leochuan.ViewPagerLayoutManager;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.MainActivity;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.BannerAdapter;
import dev.baofeng.com.supermovie.adapter.CategoryAdapter;
import dev.baofeng.com.supermovie.adapter.HomeTabFragmentPagerAdapter;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.http.UrlConfig;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.Util;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment implements IMoview, ViewPager.OnPageChangeListener, View.OnClickListener {

    Unbinder unbinder;
    private static HomeFragment homeFragment;
    @BindView(R.id.homepager)
    ViewPager homepager;
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
    @BindView(R.id.content_main)
    LinearLayout contentMain;
    @BindView(R.id.home_tabs)
    com.antiless.support.widget.TabLayout homeTabView;
    /**
     * 磁力搜索
     */
    @BindView(R.id.reclist)
    TextView recList;
    /**
     * 下载中心
     */
    @BindView(R.id.bangdan)
    TextView bangdan;
    /**
     * 高分整理
     */
    @BindView(R.id.douban)
    TextView douban;
    /**
     * 分类频道
     */
    @BindView(R.id.catfrag)
    TextView catfrag;

    @BindView(R.id.downCenter)
    TextView downCenter;

    @BindView(R.id.recycler)
    AutoPlayRecyclerView banner;


    private GetRecpresenter getRecpresenter;
    private RecentUpdate info;
    private int index;
    private CategoryAdapter homeAdapter;
    private RecentUpdate bannerInfo;
    private RecentUpdate update = new RecentUpdate();

    private ArrayList<RecentUpdate.DataBean> dataBeans = new ArrayList<>();
    private BannerAdapter bannerAdapter;
    private CarouselLayoutManager carouselLayoutManager;
    private String poster;
    private MovieFragment movieFragment;
    private SerisFragment serisFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.channel_layout, null);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initData();
        initEvent();
        return view;
    }

    private void initView() {
        movieFragment = MovieFragment.newInstance("movie");
        serisFragment = SerisFragment.newInstance("seris");
        List listfragment=new ArrayList<Fragment>();
        listfragment.add(movieFragment);
        listfragment.add(serisFragment);
        FragmentManager fm=getChildFragmentManager();
        HomeTabFragmentPagerAdapter adapter=new HomeTabFragmentPagerAdapter(fm, listfragment);
        homepager.setAdapter(adapter);
        homepager.setCurrentItem(0);
        homeTabView.setupWithViewPager(homepager);
    }


    private MainActivity.OnPageChanged lisener;
    public void setOnPageChangeListener(MainActivity.OnPageChanged onPageChanged) {
        this.lisener = onPageChanged;
    }


    private void initEvent() {

        appbar.addOnOffsetChangedListener(new MyOffsetChangedListener());
        toobar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            getContext().startActivity(intent);
        });
        downtask.setOnClickListener(v -> {
           Intent intent =new Intent(getContext(), DownLoadMainActivity.class);
           startActivity(intent);
        });


        recList.setOnClickListener(this);
        bangdan.setOnClickListener(this);
        douban.setOnClickListener(this);
        catfrag.setOnClickListener(this);
        downCenter.setOnClickListener(this);

        carouselLayoutManager = new CarouselLayoutManager(getContext(), Util.Dp2px(getContext(), 100));
        carouselLayoutManager.setItemSpace(Util.Dp2px(getContext(),80));
        carouselLayoutManager.setMoveSpeed(0.3f);
        banner.setLayoutManager(carouselLayoutManager);

        carouselLayoutManager.setOnPageChangeListener(new ViewPagerLayoutManager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

                if (i==2){
                    if (carouselLayoutManager.getCurrentPosition()==9){
                        poster = bannerInfo.getData().get(0).getDownimgurl().split(",")[0];
                    }else {
                        poster = bannerInfo.getData().get(carouselLayoutManager.getCurrentPosition()+1).getDownimgurl().split(",")[0];
                    }
                    if (isDetached()) {
                        return;
                    }
                    Glide.with(getContext()).load(poster).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Bitmap reverseBitmapById = BlurUtil.getBlurBitmap(4,4,resource);
                            imgBg.setImageBitmap(reverseBitmapById);
                        }
                    });
                }

            }
        });
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int i) {
        if (bannerInfo!=null&&bannerInfo.getData().size()>0){
            int currentItem = i;
            Log.e("currentItem",i+"");
            String poster = bannerInfo.getData().get(currentItem%10).getDownimgurl().split(",")[0];
            Glide.with(getContext()).load(poster).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Bitmap reverseBitmapById = BlurUtil.getBlurBitmap(4,4,resource);
                    imgBg.setImageBitmap(reverseBitmapById);
                }
            });

        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.catfrag:
                Intent onlineIntent = new Intent(getActivity(), OnlineActivity.class);
                startActivity(onlineIntent);
                break;
            case R.id.bangdan:
                Intent intents = new Intent(getContext(), IndexActivity.class);
                startActivity(intents);
                break;
            case R.id.douban:
                Intent favor = new Intent(getContext(), FavorActivity.class);
                startActivity(favor);
                break;
            case R.id.reclist:
                if (lisener!=null){
                    lisener.clicked();
                }
                break;
            case R.id.downCenter:
                Intent intent = new Intent(getContext(), DownLoadMainActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
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
       // pulllayout.setOnPullListener(this);
        index = 1;
        getRecpresenter = new GetRecpresenter(getContext(), this);
        getRecpresenter.getRecentUpdate( index,18);
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
        this.dataBeans.clear();
        this.dataBeans.addAll(result.getData()) ;
        bannerAdapter = new BannerAdapter(getContext(),dataBeans);
        banner.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
        poster = bannerInfo.getData().get(carouselLayoutManager.getCurrentPosition()).getDownimgurl().split(",")[0];
        Glide.with(getContext()).load(poster).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap reverseBitmapById = BlurUtil.getBlurBitmap(4,4,resource);
                imgBg.setImageBitmap(reverseBitmapById);
            }
        });

    }
    @Override
    public void loadDetail(BtInfo result) {

    }

}
