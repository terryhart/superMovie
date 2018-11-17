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
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bftv.myapplication.view.IndexActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;
import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.BlurUtil;
import com.xiaosu.pulllayout.SimplePullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;


import java.util.ArrayList;
import java.util.List;

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


/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment implements IMoview,  BasePullLayout.OnPullCallBackListener, ViewPager.OnPageChangeListener, View.OnClickListener {

    Unbinder unbinder;
    private static HomeFragment homeFragment;
    @BindView(R.id.rvlist)
    SwipeMenuRecyclerView rvlist;
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
    CoordinatorLayout contentMain;
    /*@BindView(R.id.pulllayout)
    SimplePullLayout pulllayout;*/
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

    private final class SpringInterpolator implements Interpolator {

        private final static float FACTOR = 0.5F;

        @Override
        public float getInterpolation(final float input) {
            return (float) (Math.pow(2.0F, -10.0F * input) *
                    Math.sin((input - FACTOR / 4.0F) * (2.0F * Math.PI) / FACTOR) + 1.0F);
        }
    }
    private void initEvent() {
       // pulllayout.postRefresh();

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

        rvlist.useDefaultLoadMore(); // 使用默认的加载更多的View。
        rvlist.setLoadMoreListener(mLoadMoreListener); // 加载更多的监听。
    }

    SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeMenuRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            // 该加载更多啦。
            // 数据完更多数据，一定要调用这个方法。
            // 第一个参数：表示此次数据是否为空。
            // 第二个参数：表示是否还有更多数据。
            getRecpresenter.getMoreData(++index,18);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //  pulllayout.finishPull("加载完成",true);
                    rvlist.loadMoreFinish(false, true);
                    adapter.notifyDataSetChanged();
                }
            },1000);
            // 如果加载失败调用下面的方法，传入errorCode和errorMessage。
            // errorCode随便传，你自定义LoadMoreView时可以根据errorCode判断错误类型。
            // errorMessage是会显示到loadMoreView上的，用户可以看到。
            // mRecyclerView.loadMoreError(0, "请求网络失败");
        }
    };


    private OnDownPageListener listener;
    public void setOnDownPageListener(OnDownPageListener onDownPageListener) {
        this.listener = onDownPageListener;
    }

    public void autoRefresh(){
//        if (pulllayout!=null){
//            pulllayout.postRefresh();
//        }
        if (getRecpresenter!=null){
            getRecpresenter.getBtRecommend(1,10);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRecpresenter.getRecentUpdate(1,18);
//                if (pulllayout!=null){
//                    pulllayout.finishPull("加载完成",true);
//                }
            }
        },1000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getRecpresenter.getMoreData(++index,18);
              //  pulllayout.finishPull("加载完成",true);
            }
        },1000);
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
                Intent intentOnline = new Intent(getContext(),OnlineActivity.class);
                startActivity(intentOnline);
                break;
            case R.id.bangdan:
                Intent intents = new Intent(getContext(), IndexActivity.class);
                startActivity(intents);
                break;
            case R.id.douban:
                Intent intentHistory = new Intent(getContext(), HistoryActivity.class);
                startActivity(intentHistory);
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
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int spanSize;
                if (homeAdapter.getItemViewType(position) == GlobalMsg.ITEM_TYPE_1) {
                    spanSize =3;
                    //跨2列
                } else   {
                    spanSize = 1;
                    //跨1列
                }
                return spanSize;
            }
        });
        rvlist.setAdapter(homeAdapter);
        rvlist.loadMoreFinish(false, true);
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
