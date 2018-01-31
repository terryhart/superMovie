package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.MainAdapter;
import dev.baofeng.com.supermovie.adapter.PagerAdapter;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;

/**
 * Created by huangyong on 2018/1/31.
 */

public class ChannelFragment extends Fragment implements IMoview {
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    private GetRecpresenter recpresenter;
    private MainAdapter adapter;
    private static ChannelFragment channelFragment;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag_layout,null);
        bind = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public static ChannelFragment newInstance(String type){
        channelFragment = new ChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Type",type);
        channelFragment.setArguments(bundle);
        return channelFragment;

    }
    private void initView() {
        Bundle bundle = getArguments();
        String type = bundle.getString("Type");
        recpresenter = new GetRecpresenter(getContext(),this);
        recpresenter.getRecommend(type,1,12);
    }

    @Override
    public void loadData(MovieInfo info) {
        adapter = new MainAdapter(getContext(),info);
        rvlist.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvlist.setAdapter(adapter);
    }

    @Override
    public void loadError(String msg) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
