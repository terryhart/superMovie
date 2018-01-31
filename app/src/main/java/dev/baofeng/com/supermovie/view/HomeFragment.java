package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.PagerAdapter;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;

/**
 * Created by huangyong on 2018/1/26.
 */

public class HomeFragment extends Fragment{

    @BindView(R.id.tab_findFragment_title)
    TabLayout tbTitle;
    Unbinder unbinder;
    private static HomeFragment homeFragment;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.vp)
    ViewPager vp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.channel_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        String[] arr = { "战争","科幻","纪录片", "喜剧" ,"爱情" ,"恐怖" ,"动作"};
        String[] type = {"war","sci","docu","fun","love","scare","act"};
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(ChannelFragment.newInstance(type[i]));
            tbTitle.addTab(tbTitle.newTab().setText(arr[i]));
        }
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(),list,arr);
        tbTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        tbTitle.setupWithViewPager(vp);
        vp.setAdapter(adapter);
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

}
