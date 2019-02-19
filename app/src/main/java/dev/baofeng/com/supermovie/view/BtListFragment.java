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
import android.widget.ImageView;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.xiaosu.pulllayout.SimplePullLayout;
import com.xiaosu.pulllayout.base.BasePullLayout;

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

public class BtListFragment extends Fragment implements IAllView, BasePullLayout.OnPullCallBackListener {
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    @BindView(R.id.pull_layout)
    SimplePullLayout pulllayout;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag_layout, null);
        bind = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initData() {
        recpresenter = new CenterPresenter(getContext(), this);
        index = 1;
        recpresenter.getLibraryDdata(type,index,18);
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
        pulllayout.setOnPullListener(this);
        Bundle bundle = getArguments();
        this.type = bundle.getString("Type");
        Log.e("tytpetype", type);
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
    public void loadSuccess(RecentUpdate movieBean) {
        this.movieInfo = movieBean;
        loadingView.setVisibility(View.GONE);
        Log.e("movieInfo",movieBean.getData().size()+"");
        adapter =new BTcategoryAdapter(getContext(),movieBean);
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
                                recpresenter.getLibraryMoreDdata(type, ++index, 18);
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
    public void loadMore(RecentUpdate movieBean) {
        this.movieInfo.getData().addAll(movieBean.getData());
        adapter.notifyDataSetChanged();
        if (empFram.isShown()){
            empFram.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFail() {
        empFram.setVisibility(View.VISIBLE);
        empImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pulllayout.autoRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recpresenter.getLibraryDdata(type,1,18);
                pulllayout.finishPull("加载完成",true);
            }
        },2000);
    }

    @Override
    public void onLoad() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recpresenter.getLibraryMoreDdata(type,++index,18);
                pulllayout.finishPull("加载完成",true);
            }
        },2000);

    }

}
