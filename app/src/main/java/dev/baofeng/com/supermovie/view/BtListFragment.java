package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HomeAdapter;
import dev.baofeng.com.supermovie.adapter.MainAdapter;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.NetworkUtils;

/**
 * Created by huangyong on 2018/1/31.
 */

public class BtListFragment extends Fragment implements IMoview {
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    private GetRecpresenter recpresenter;
    HomeAdapter adapter;
    private static BtListFragment btlistFragment;
    private Unbinder bind;
    private RecentUpdate infos;
    private int index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag_layout, null);
        bind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public static BtListFragment newInstance(String type) {
        btlistFragment = new BtListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type", type);
        btlistFragment.setArguments(bundle);
        return btlistFragment;

    }

    private void initView() {
        recpresenter = new GetRecpresenter(getContext(), this);


        Bundle bundle = getArguments();
        index = 1;
    }

    @Override
    public void loadData(RecentUpdate info) {

    }



    @Override
    public void loadError(String msg) {

    }

    @Override
    public void loadMore(RecentUpdate result) {

    }


    @Override
    public void loadBtData(RecentUpdate result) {

    }

    @Override
    public void loadDetail(BtInfo result) {

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
}
