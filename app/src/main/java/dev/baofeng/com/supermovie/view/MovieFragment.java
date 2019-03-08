package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.CategoryAdapter;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.RecentPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IRecentView;
import dev.baofeng.com.supermovie.view.loadmore.LoadMoreAdapter;
import dev.baofeng.com.supermovie.view.loadmore.LoadMoreWrapper;

/**
 * Created by huangyong on 2018/1/31.
 */

public class MovieFragment extends Fragment implements   IRecentView {
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    @BindView(R.id.empty_img)
    TextView empImg;
    @BindView(R.id.empty_view)
    FrameLayout empFram;

    CategoryAdapter adapter;
    private static MovieFragment btlistFragment;
    private Unbinder bind;
    private int index;
    private RecentUpdate movieInfo;
    private String type;
    private RecentPresenter recpresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_seris_layout, null);
        bind = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        recpresenter = new RecentPresenter(getContext(),this);
        index = 1;
        recpresenter.getMovieUpdate(index,18);
    }

    public static MovieFragment newInstance(String type) {
        btlistFragment = new MovieFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", type);
        btlistFragment.setArguments(bundle);
        return btlistFragment;

    }

    private void initView() {
        Bundle bundle = getArguments();
        this.type = bundle.getString("Type");
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
    public void loadData(RecentUpdate movieBean) {
        this.movieInfo = movieBean;
        Log.e("testloadmore", "tesloadmore");
        adapter = new CategoryAdapter(getActivity(), movieBean);
        rvlist.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvlist.setAdapter(adapter);
        LoadMoreWrapper.with(adapter)
                .setLoadMoreEnabled(true)
                .setListener(new LoadMoreAdapter.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore(LoadMoreAdapter.Enabled enabled) {
                        rvlist.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                recpresenter.getMovieMore(++index, 18);
                            }
                        }, 1);
                    }
                })
                .into(rvlist);
        if (empFram.isShown()){
            empFram.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFail(String s) {
        empFram.setVisibility(View.VISIBLE);
        empImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }


    @Override
    public void loadMore(RecentUpdate movieBean) {
        this.movieInfo.getData().addAll(movieBean.getData());
        adapter.notifyDataSetChanged();
        if (empFram.isShown()){
            empFram.setVisibility(View.GONE);
        }
    }

    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recpresenter.getMovieUpdate(1,18);
            }
        },10);
    }
}
