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
     * "war"=>"战争",
     * "sci"=>"科幻",
     * "2017"=>"2017",
     * "2016"=>"2016",
     * "hk"=>"香港",
     * "america"=>"美国",
     * "scare"=>"惊悚",
     * "motion"=>"剧情",
     * "crim"=>"犯罪",
     * "confuse"=>"悬疑",
     * "fun"=>"喜剧",
     * "love"=>"爱情",
     * "horri"=>"恐怖",
     * "carton"=>"动画",
     * "fam"=>"家庭",
     * "riben"=>"日本",
     * "risk"=>"冒险",
     * "act"=>"动作");
     */
    private void initView() {
        String[] arr = { "战争","科幻","2017",
                "2016","香港","美国",
                "惊悚","剧情","犯罪",
                "悬疑","喜剧" ,"爱情",
                "恐怖","动画","家庭",
                "日本","冒险" ,"动作"};
        String[] type = {"war","sci","2017",
                "2016","hk","america",
                "scare","motion","crim",
                "confuse","fun","love",
                "horri","carton","fam",
                "riben","risk","act"};
        ArrayList<Fragment> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(BtListFragment.newInstance(type[i]));
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
}
