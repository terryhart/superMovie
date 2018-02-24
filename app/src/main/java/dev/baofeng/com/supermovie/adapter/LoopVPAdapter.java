package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.support.v4.view.*;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by huangyong on 2018/2/22.
 */

public abstract class LoopVPAdapter<T> extends android.support.v4.view.PagerAdapter implements ViewPager.OnPageChangeListener {
    //    当前页面
    private int currentPosition = 0;

    protected Context mContext;
    protected ArrayList<View> views;
    protected ViewPager mViewPager;

    public LoopVPAdapter(Context context, ArrayList<T> datas, ViewPager viewPager) {
        mContext = context;
        views = new ArrayList<>();
//        如果数据大于一条
        if(datas.size() > 1) {
//            添加最后一页到第一页
            datas.add(0,datas.get(datas.size()-1));
//            添加第一页(经过上行的添加已经是第二页了)到最后一页
            datas.add(datas.get(1));
        }
        for (T data:datas) {
            views.add(getItemView(data));
        }
        mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1,false);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    protected abstract View getItemView(T data);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        若viewpager滑动未停止，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) return;
//        若当前为第一张，设置页面为倒数第二张
        if (currentPosition == 0) {
            mViewPager.setCurrentItem(views.size()-2,false);
        } else if (currentPosition == views.size()-1) {
//        若当前为倒数第一张，设置页面为第二张
            mViewPager.setCurrentItem(1,false);
        }
    }
}
