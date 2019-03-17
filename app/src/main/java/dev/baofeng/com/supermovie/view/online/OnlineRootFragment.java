package dev.baofeng.com.supermovie.view.online;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.antiless.support.widget.TabLayout;
import com.huangyong.downloadlib.DownLoadMainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.OnlineTabFragmentPagerAdapter;
import dev.baofeng.com.supermovie.db.data.OnlineSearchHistory;
import dev.baofeng.com.supermovie.view.SearchActivity;

/**
 * Created by huangyong on 2018/1/31.
 */

public class OnlineRootFragment extends Fragment {


    @BindView(R.id.online_tab)
    TabLayout homeTabView;
    @BindView(R.id.onlinepager)
    ViewPager homepager;
    @BindView(R.id.constraintLayout)
    LinearLayout linearLayout;
    Unbinder unbinder;
    @BindView(R.id.square)
    ImageView square;
    @BindView(R.id.toobar)
    Toolbar toobar;
    private OnlineFilmRootFragment movieFragment;
    private OnlineSerisRootFragment serisFragment;
    private static OnlineRootFragment onlineRootFragment;
    private SerisListFragment curtoon;
    private SerisListFragment show;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_online_film, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    private void initData() {


        toobar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), OnlineSearchActivity.class);
            getContext().startActivity(intent);
        });

        serisFragment = OnlineSerisRootFragment.getInstance();
        curtoon = SerisListFragment.newInstance("curtoon");
        show = SerisListFragment.newInstance("show");
        movieFragment = OnlineFilmRootFragment.getInstance();
        List listfragment = new ArrayList<Fragment>();
        listfragment.add(movieFragment);
        listfragment.add(serisFragment);
        listfragment.add(curtoon);
        listfragment.add(show);
        FragmentManager fm = getChildFragmentManager();
        OnlineTabFragmentPagerAdapter adapter = new OnlineTabFragmentPagerAdapter(fm, listfragment);
        if (adapter != null) {
            homepager.setAdapter(adapter);
            homepager.setCurrentItem(0);
            homeTabView.setupWithViewPager(homepager);
        }
    }

    public static OnlineRootFragment newInstance() {
        if (onlineRootFragment == null) {
            onlineRootFragment = new OnlineRootFragment();
            Bundle bundle = new Bundle();
            onlineRootFragment.setArguments(bundle);
        }
        return onlineRootFragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
