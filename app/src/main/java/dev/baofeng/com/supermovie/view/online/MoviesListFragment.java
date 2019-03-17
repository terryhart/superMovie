package dev.baofeng.com.supermovie.view.online;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import com.mingle.widget.LoadingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.OnlineCategoryAdapter;
import dev.baofeng.com.supermovie.domain.online.OnlinePlayInfo;
import dev.baofeng.com.supermovie.presenter.GetRandomRecpresenter;
import dev.baofeng.com.supermovie.presenter.online.GetOnlinePresenter;
import dev.baofeng.com.supermovie.presenter.online.iview.IOnlineView;
import dev.baofeng.com.supermovie.view.loadmore.LoadMoreWrapper;

/**
 * Created by huangyong on 2018/1/31.
 */

public class MoviesListFragment extends Fragment implements IOnlineView {
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    @BindView(R.id.empty_img)
    TextView empImg;
    @BindView(R.id.empty_view)
    FrameLayout empFram;
    @BindView(R.id.loadView)
    LoadingView loadingView;
    OnlineCategoryAdapter adapter;
    @BindView(R.id.refresh_root)
    SwipeRefreshLayout refreshRoot;
    private GetOnlinePresenter recpresenter;
    private Unbinder bind;
    private int index;
    private OnlinePlayInfo movieInfo;
    private String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag_layout, null);
        bind = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setLoadingText("正在加载，请稍后……");
    }

    private void initData() {
        recpresenter = new GetOnlinePresenter(getContext(), this);
        index = 1;
        recpresenter.getOnlineMvData(type, index, 18);
    }



    public static MoviesListFragment newInstance(String type) {

        MoviesListFragment btlistFragment = new MoviesListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", type);
        btlistFragment.setArguments(bundle);
        return btlistFragment;
    }



    private void initView() {
        Bundle bundle = getArguments();
        this.type = bundle.getString("Type");

        refreshRoot.setSize(SwipeRefreshLayout.DEFAULT);

        refreshRoot.setColorSchemeResources(
                android.R.color.black,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        refreshRoot.setProgressBackgroundColor(android.R.color.white);

        refreshRoot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recpresenter.getOnlineMvData(type, 1, 18);
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

    }



    @Override
    public void loadData(OnlinePlayInfo movieBean) {
        this.movieInfo = movieBean;
        if (refreshRoot!=null){
            refreshRoot.setRefreshing(false);
            loadingView.setVisibility(View.GONE);
            Log.e("movieInfo", movieBean.getData().size() + "");
            adapter = new OnlineCategoryAdapter(getActivity(), movieBean, type, 0);
            rvlist.setLayoutManager(new GridLayoutManager(getContext(), 3));
            rvlist.setAdapter(adapter);
            LoadMoreWrapper.with(adapter)
                    .setLoadMoreEnabled(true)
                    .setListener(enabled -> rvlist.postDelayed(() -> recpresenter.getMovieMoreData(type, ++index, 18), 1))
                    .into(rvlist);
            if (empFram.isShown()) {
                empFram.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void loadError(String msg) {
        refreshRoot.setRefreshing(false);
        empFram.setVisibility(View.VISIBLE);
        empImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void loadMore(OnlinePlayInfo result) {
        this.movieInfo.getData().addAll(result.getData());
        adapter.notifyDataSetChanged();
        if (empFram.isShown()) {
            empFram.setVisibility(View.GONE);
        }
    }

}
