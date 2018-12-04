package dev.baofeng.com.supermovie.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bftv.myapplication.config.KeyParam;
import com.bftv.myapplication.view.IndexActivity;
import com.bftv.myapplication.view.LineWebview;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cpacm.library.SimpleViewPager;
import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.utils.BlurUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.MainActivity;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.BasicPagerAdapter;
import dev.baofeng.com.supermovie.adapter.CategoryAdapter;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.MyTransformation;
import dev.baofeng.com.supermovie.view.widget.SlideInRightAnimation;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment implements IMoview, ViewPager.OnPageChangeListener, View.OnClickListener {

    Unbinder unbinder;
    private static HomeFragment homeFragment;
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
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
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
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

    @BindView(R.id.empty_view)
    FrameLayout empView;


    private GetRecpresenter getRecpresenter;
    private RecentUpdate info;
    private int index;
    private CategoryAdapter homeAdapter;
    private RecentUpdate bannerInfo;
    private RecentUpdate update = new RecentUpdate();
    private SimpleViewPager viewPager;
    private BasicPagerAdapter adapter;
    private ArrayList<RecentUpdate.DataBean> dataBeans = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.channel_layout, null);
        unbinder = ButterKnife.bind(this, view);
        viewPager = view.findViewById(R.id.vp);
        adapter = new BasicPagerAdapter(dataBeans,getContext());
        viewPager.setPageTransformer(new MyTransformation());
        viewPager.startAutoScroll(true);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setOnPageChangeListener(this);
        viewPager.setSliderDuration(3000);
        viewPager.setAdapter(adapter);
        initData();
        initEvent();
        return view;
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

        rvlist.setItemAnimator(new DefaultItemAnimator());
        refreshLayout.setEnableAutoLoadMore(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getRecpresenter.getRecentUpdate(1,18);
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
                getRecpresenter.getBtRecommend(1,10);

            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getRecpresenter.getMoreData(++index,18);
                //传入false表示加载失败
                refreshlayout.finishLoadMore(10);

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
//                Intent intentOnline = new Intent(getContext(),OnlineActivity.class);
//                startActivity(intentOnline);
                Intent intentOline = new Intent(getContext(), LineWebview.class);
                String url = "http://1.aibl.com.cn/";
                intentOline.putExtra(KeyParam.PLAYURL,url);
                startActivity(intentOline);
                break;
            case R.id.bangdan:
                Intent intents = new Intent(getContext(), IndexActivity.class);
                startActivity(intents);
                break;
            case R.id.douban:
//                Intent intentHistory = new Intent(getContext(), HistoryActivity.class);
//                startActivity(intentHistory);
                Intent mvTv = new Intent(getContext(), LineWebview.class);
                String urls = "http://1.aibl.com.cn/index.php/label/live.html";
                mvTv.putExtra(KeyParam.PLAYURL,urls);
                startActivity(mvTv);
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
       GridLayoutManager manager =  new GridLayoutManager(getContext(), 3);
        rvlist.setLayoutManager(manager);
        homeAdapter = new CategoryAdapter(getContext(), info);
        homeAdapter.setAnimation(new SlideInRightAnimation());
        rvlist.setAdapter(homeAdapter);

        if (empView.isShown()){
            empView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void loadError(String msg) {
        empView.setVisibility(View.VISIBLE);
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
        adapter.notifyDataSetChanged();
        viewPager.notifyDataSetChanged();
        viewPager.setOffscreenPageLimit(10);
        viewPager.setCurrentItem(2);
        contentMain.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return viewPager.dispatchTouchEvent(motionEvent);
            }
        });
    }
    @Override
    public void loadDetail(BtInfo result) {

    }

}
