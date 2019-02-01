package dev.baofeng.com.supermovie.view.online;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antiless.support.widget.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.PageAdapter;
import dev.baofeng.com.supermovie.view.BtListFragment;

/**
 * Created by huangyong on 2018/1/26.
 */

public class OnlineSerisRootFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.tabline)
    View tabline;
    @BindView(R.id.btvp)
    ViewPager btvp;
    private static OnlineSerisRootFragment btFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_frag_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        String[] arr = {"美剧", "动漫", "国产剧", "港剧", "台湾剧", "日剧",
                "韩剧",
                "微电影", "海外剧"};
        String[] type = {"america", "curtoon", "native", "hongkong", "taiwan", "japanise",
                "koria", "shortmv", "ocean"};
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(SerisListFragment.newInstance(type[i]));
            tabTitle.addTab(tabTitle.newTab().setText(arr[i]));
        }
        PageAdapter adapter = new PageAdapter(getChildFragmentManager(), list, arr);
        tabTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabTitle.setupWithViewPager(btvp);
        btvp.setAdapter(adapter);
    }

    public static OnlineSerisRootFragment getInstance() {
        if (btFragment == null) {
            btFragment = new OnlineSerisRootFragment();
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
