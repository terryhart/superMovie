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
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment implements IMoview {

    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    Unbinder unbinder;
    private static HomeFragment homeFragment;
    private GetRecpresenter recpresenter;
    private MainAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_frag_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }
    public static HomeFragment getInstance(){
        if (homeFragment==null){
            homeFragment = new HomeFragment();
        }else {
            return homeFragment;
        }
        return homeFragment;
    }
    private void initView() {
        recpresenter = new GetRecpresenter(getContext(),this);
        recpresenter.getRecommend("war",1,12);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
}
