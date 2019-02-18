package dev.baofeng.com.supermovie.adapter;
/**
 * Created by HuangYong on 2018/9/19.
 */

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @created 2018/9/19
 * @changeRecord [修改记录] <br/>
 * 2018/9/19 ：created
 */
public class OnlineTabFragmentPagerAdapter extends FragmentPagerAdapter {

    String[] tabName = {"电影", "剧集","动漫","综艺"};
    //创建FragmentManager

    private FragmentManager fragmetnmanager;

    //创建一个List<Fragment>

    private List<Fragment> listfragment;

    public OnlineTabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragmetnmanager = fm;
        this.listfragment = list;
    }

    @Override
    public Fragment getItem(int position) {
        return listfragment.get(position);
    }

    @Override
    public int getCount() {
        return listfragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabName[position];
    }
}
