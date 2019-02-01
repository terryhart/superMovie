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

/**
 * Created by huangyong on 2018/1/26.
 */

public class OnlineFilmRootFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.tab_title)
    TabLayout tabTitle;
    @BindView(R.id.tabline)
    View tabline;
    @BindView(R.id.btvp)
    ViewPager btvp;
    private static OnlineFilmRootFragment btFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.online_frag_layout, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        String[] arr = {"科幻", "喜剧", "爱情", "战争", "剧情", "恐怖",
                "综艺"};
        String[] type = {"science", "comedy", "love", "war", "story", "terror", "show"};
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(MoviesListFragment.newInstance(type[i]));
            tabTitle.addTab(tabTitle.newTab().setText(arr[i]));
        }
        PageAdapter adapter = new PageAdapter(getChildFragmentManager(), list, arr);
        tabTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabTitle.setupWithViewPager(btvp);
        btvp.setAdapter(adapter);
    }

    public static OnlineFilmRootFragment getInstance() {
        if (btFragment == null) {
            btFragment = new OnlineFilmRootFragment();
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
