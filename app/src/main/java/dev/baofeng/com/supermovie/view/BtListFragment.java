package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mingle.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.BTcategoryAdapter;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.CenterPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IAllView;
import dev.baofeng.com.supermovie.view.loadmore.LoadMoreAdapter;
import dev.baofeng.com.supermovie.view.loadmore.LoadMoreWrapper;

/**
 * Created by huangyong on 2018/1/31.
 */

public class BtListFragment extends Fragment implements IAllView {
    @BindView(R.id.rvlist)
    XRecyclerView rvlist;
    @BindView(R.id.empty_img)
    TextView empImg;
    @BindView(R.id.empty_view)
    FrameLayout empFram;
    @BindView(R.id.loadView)
    LoadingView loadingView;
    private CenterPresenter recpresenter;
    BTcategoryAdapter adapter;
    private static BtListFragment btlistFragment;
    private Unbinder bind;
    private int index;
    private RecentUpdate movieInfo;
    private String type;

    @BindView(R.id.refresh_root)
    SwipeRefreshLayout pullRefresh;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag_layout, null);
        bind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initData() {
        recpresenter = new CenterPresenter(getContext(), this);
        index = 1;
        recpresenter.getLibraryDdata(type,index,18);

        rvlist.setPullRefreshEnabled(false);
        rvlist.getDefaultFootView().setLoadingHint("正在加载请稍后");
        rvlist.getDefaultFootView().setNoMoreHint("已经到底了");
        rvlist.setLimitNumberToCallLoadMore(2);
        rvlist.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvlist.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rvlist.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                recpresenter.getLibraryMoreDdata(type, ++index, 18);
            }
        });
    }

    public static BtListFragment newInstance(String type) {
        btlistFragment = new BtListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", type);
        btlistFragment.setArguments(bundle);
        return btlistFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setLoadingText("正在加载，请稍后……");
    }
    private void initView() {
        Bundle bundle = getArguments();
        this.type = bundle.getString("Type");

        pullRefresh.setSize(SwipeRefreshLayout.DEFAULT);

        pullRefresh.setColorSchemeResources(
                android.R.color.black,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        pullRefresh.setProgressBackgroundColor(android.R.color.white);

        pullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recpresenter.getLibraryDdata(type,1,18);
            }
        });

    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void loadSuccess(RecentUpdate movieBean) {
        this.movieInfo = movieBean;
        if (loadingView!=null){
            loadingView.setVisibility(View.GONE);
            pullRefresh.setRefreshing(false);
            adapter =new BTcategoryAdapter(getContext(),movieBean);
            rvlist.setLayoutManager(new GridLayoutManager(getContext(), 3));
            rvlist.setAdapter(adapter);
            if (empFram.isShown()){
                empFram.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void loadMore(RecentUpdate movieBean) {
        this.movieInfo.getData().addAll(movieBean.getData());
        adapter.notifyDataSetChanged();
        if (empFram.isShown()){
            empFram.setVisibility(View.GONE);
        }
        rvlist.loadMoreComplete();
    }

    @Override
    public void loadFail() {
        empFram.setVisibility(View.VISIBLE);
        empImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recpresenter.getLibraryDdata(type,1,18);
            }
        });
        rvlist.setNoMore(true);
    }

}
