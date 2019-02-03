package dev.baofeng.com.supermovie.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.antiless.support.widget.TabLayout;
import com.huangyong.downloadlib.DownLoadMainActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.MainActivity;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.PageAdapter;

/**
 *
 * Created by huangyong on 2018/1/26.
 */

public class BTFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.tabline)
    View tabline;
    @BindView(R.id.btvp)
    ViewPager btvp;

    @BindView(R.id.toobar)
    Toolbar toobar;
    @BindView(R.id.square)
    ImageView square;
    @BindView(R.id.downtask)
    ImageView downtask;
    private static BTFragment btFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.download_frag_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * $type=array(
     * "latest"=>"最新电影",
     * "highdpi"=>"经典高清",
     * "cznmv"=>"国配电影",
     * "hungkong"=>"经典港片",
     * "native"=>"国剧",
     * "koria"=>"日韩剧",
     * "america"=>"美剧",
     * "complex"=>"综艺",
     * "curtoon"=>"动漫",
     * "document"=>"纪录片",
     * "k720p"=>"720P+1080P",
     * "k4mv"=>"4K高清区",
     */
    /**
     * '首页', '最新电影', '经典高清', '国配电影', '经典港片', '国剧', '日韩剧', '美剧', '综艺', '动漫', '纪录片', '720P+1080P', '4K高清区', '3D电影']
     */
    private void initView() {


        toobar.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            getContext().startActivity(intent);
        });
        downtask.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DownLoadMainActivity.class);
            startActivity(intent);
        });

        String[] arr = {"首页", "最新电影", "经典高清", "国配电影",
                "经典港片","国产剧","日韩剧",
                "美剧","综艺","动漫",
                "纪录片","4K高清区"};
        String[] type = {"home", "latest", "highdpi", "cznmv",
                "hungkong","native","koria",
                "america","complex","curtoon",
                "document","k4mv"};
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                list.add(HomeFragment.getInstance());
            } else {
                list.add(BtListFragment.newInstance(type[i]));
            }

            tabTitle.addTab(tabTitle.newTab().setText(arr[i]));
        }
        PageAdapter adapter = new PageAdapter(getChildFragmentManager(),list,arr);
        tabTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabTitle.setupWithViewPager(btvp);
        btvp.setAdapter(adapter);
    }

    public static BTFragment getInstance() {
        if (btFragment == null) {
            btFragment = new BTFragment();
        } else {
            return btFragment;
        }
        return btFragment;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
