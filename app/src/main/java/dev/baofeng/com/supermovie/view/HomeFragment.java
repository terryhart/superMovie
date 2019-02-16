package dev.baofeng.com.supermovie.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bftv.myapplication.view.IndexActivity;
import com.bumptech.glide.Glide;
import com.huangyong.downloadlib.DownLoadMainActivity;
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
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.Util;
import dev.baofeng.com.supermovie.view.online.OnlineFilmActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static dev.baofeng.com.supermovie.utils.ColorHelper.colorBurn;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment implements IMoview, View.OnClickListener {

    Unbinder unbinder;
    private static HomeFragment homeFragment;
    @BindView(R.id.homepager)
    ViewPager homepager;
    @BindView(R.id.img_bg)
    ImageView imgBg;
    @BindView(R.id.appbar)
    AppBarLayout appbar;

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
                    if (getContext() == null) {
                        return;
                    }

                    Glide.with(getContext()).load(poster).bitmapTransform(new BlurTransformation(getContext(), 25)).into(imgBg);

                }
            }
        });
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.catfrag:
                Intent onlineIntent = new Intent(getActivity(), OnlineFilmActivity.class);
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
                // toobar.setVisibility(View.VISIBLE);
                // toobar.setAlpha(progress);
            } else {
                // toobar.setVisibility(View.VISIBLE);
                // toobar.setAlpha(0.4f);
            }
        }
    }

    private void initData() {
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
        Glide.with(getContext()).load(poster).bitmapTransform(new BlurTransformation(getContext(), 25)).into(imgBg);

    }
    @Override
    public void loadDetail(BtInfo result) {

    }


    public void getColor(Bitmap bitmap) {
        // Palette的部分
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                Palette.Swatch vibrant = palette.getLightMutedSwatch();
                //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
                if (appbar != null) {
                    if (vibrant != null) {

                        ValueAnimator colorAnim2 = ValueAnimator.ofArgb(Color.rgb(110, 110, 100), colorBurn(vibrant.getRgb()));
                        colorAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                //root.setBackgroundColor((Integer) animation.getAnimatedValue());
                                // toolbar.setBackgroundColor((Integer) animation.getAnimatedValue());
//                                detail_app_bar.setBackgroundColor((Integer) animation.getAnimatedValue());
                            }
                        });
                        colorAnim2.setDuration(300);
                        colorAnim2.setRepeatMode(ValueAnimator.RESTART);
                        colorAnim2.start();

                        if (Build.VERSION.SDK_INT >= 21) {
                            Window window = getActivity().getWindow();
                            window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                            window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
                        }
                    }
                }

            }
        });
    }


}
