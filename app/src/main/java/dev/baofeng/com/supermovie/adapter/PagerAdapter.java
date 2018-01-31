package dev.baofeng.com.supermovie.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by huangyong on 2018/1/31.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] tabTitle;
    public PagerAdapter(FragmentManager fm,List<Fragment> fragments, String[] tabTiltle) {
        super(fm);
        this.fragments = fragments;
        this.tabTitle = tabTiltle;
    }

    @Override
    public Fragment getItem(int position) {
        return  fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
