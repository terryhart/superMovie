package dev.baofeng.com.supermovie.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HomeAdapter;
import dev.baofeng.com.supermovie.adapter.VPadapter;
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
        return view;
    }

    private void initData() {
        String[] arr = {"战争", "科幻", "纪录片", "喜剧", "爱情", "恐怖", "动作", "全部"};
        String[] type = {"war", "sci", "docu", "fun", "love", "scare", "act", "all"};
        ArrayList<Fragment> list = new ArrayList<>();
        getRecpresenter = new GetRecpresenter(getContext(), this);
//        for (int i = 0; i < arr.length; i++) {
//            list.add(ChannelFragment.newInstance(type[i]));
//            tbTitle.addTab(tbTitle.newTab().setText(arr[i]));
//        }
//        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), list, arr);
//        tbTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
//        rvp.setAdapter(adapter);
//        tbTitle.setupWithViewPager(rvp);
        bgaRefresh.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        // 设置下拉刷新和上拉加载更多的风格
        bgaRefresh.setRefreshViewHolder(refreshViewHolder);
        getRecpresenter.getRecommend(type[0], 1, 22);
        getRecpresenter.getBtRecommend(type[0], 2, 8);
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
        vPadapter.setOngetListener(new VPadapter.getCurrentBitmap() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                BlurUtil.setViewBg(5,5,vp,bitmap);
            }
        });
        vp.setAdapter(vPadapter);
        vp.setCurrentItem(3);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (NetworkUtils.isNetAvailable(getContext())) {
            //网络可用。异步加载后停止刷新
            // 加载完毕后在 UI 线程结束加载更多
            new Handler().postDelayed(()->  {
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
        new Handler().postDelayed(()-> {
            bgaRefresh.endLoadingMore();
        }, 2000);
        return true;
    }
}
