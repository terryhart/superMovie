package dev.baofeng.com.supermovie.view;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.crazysunj.cardslideview.CardHandler;
import com.crazysunj.cardslideview.CardViewPager;
import com.xiaosu.pulllayout.SimplePullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HomeAdapter;
import dev.baofeng.com.supermovie.adapter.LAdapter;
import dev.baofeng.com.supermovie.adapter.VPadapter;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.BlurUtil;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment implements IMoview,  BasePullLayout.OnPullCallBackListener {

    Unbinder unbinder;
    private static HomeFragment homeFragment;
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    @BindView(R.id.vp)
    CardViewPager vp;
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
    private VPadapter vPadapter;
    private int index;
    private HomeAdapter homeAdapter;

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
           if (listener!=null){
               listener.toggle();
           }
        });
    }
    private OnDownPageListener listener;
    public void setOnDownPageListener(OnDownPageListener onDownPageListener) {
        this.listener = onDownPageListener;
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
                getRecpresenter.getMoreData(index++,20);
                pulllayout.finishPull("加载完成",true);
            }
        },2000);
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
    public class MyCardHandler implements CardHandler<String> {

        @Override
        public View onBind(Context context, String data, int position, int mode) {
            View view = View.inflate(context, R.layout.first_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.gallery_img);
            Glide.with(context).load(data).into(imageView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "data:" + data + "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }
    @Override
    public void loadBtData(RecentUpdate result) {
        vPadapter = new VPadapter(result, getContext());
        String[] imageArray = {
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537805483795&di=e484bce1fdb0ed04c3c455da2bf5cea7&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F8cb1cb1349540923705db16a9f58d109b3de49ea.jpg"
        };
        vp.bind(getChildFragmentManager(), new MyCardHandler(), Arrays.asList(imageArray));

//        vp.setPageTransformer(false, new CustPagerTransformer(getContext()));
        switchNormal();

    }
    //正常与卡片效果切换切换，请设置合理的值
    private void switchNormal() {

        vp.setCardTransformer(360,0.8f);
        vp.setCardPadding(60);
        vp.setCardMargin(40);
        vp.setOffscreenPageLimit(20);
        vp.notifyUI(CardViewPager.MODE_CARD);

    }
    @Override
    public void loadDetail(BtInfo result) {

    }

}
