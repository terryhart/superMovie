package dev.baofeng.com.supermovie.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangyong on 2018/2/11.
 */

public class CycleViewPager extends ViewPager {
    private InnerPagerAdapter mAdapter;

    public CycleViewPager(Context context) {
        super( context);
        setOnPageChangeListener( null);
    }

    public CycleViewPager(Context context, AttributeSet attrs) {
        super( context, attrs);
        setOnPageChangeListener( null);
    }

    @Override
    public void setAdapter(PagerAdapter arg0) {
        mAdapter = new InnerPagerAdapter( arg0);
        super.setAdapter( mAdapter);
        setCurrentItem( 1);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        super.setOnPageChangeListener( new InnerOnPageChangeListener( listener));
    }

    private class InnerOnPageChangeListener implements OnPageChangeListener {

        private OnPageChangeListener listener;
        private int position;

        public InnerOnPageChangeListener(OnPageChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if(null != listener) {
                listener.onPageScrollStateChanged( arg0);
            }
            if(arg0 == ViewPager.SCROLL_STATE_IDLE) {
                if(position == mAdapter.getCount() - 1) {
                    setCurrentItem( 1, false);
                }
                else if(position == 0) {
                    setCurrentItem( mAdapter.getCount() - 2, false);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if(null != listener) {
                listener.onPageScrolled( arg0, arg1, arg2);
            }
        }

        @Override
        public void onPageSelected(int arg0) {
            position = arg0;
            if(null != listener) {
                listener.onPageSelected( arg0);
            }
        }
    }

    private class InnerPagerAdapter extends PagerAdapter {

        private PagerAdapter adapter;

        public InnerPagerAdapter(PagerAdapter adapter) {
            this.adapter = adapter;
            adapter.registerDataSetObserver( new DataSetObserver() {

                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onInvalidated() {
                    notifyDataSetChanged();
                }

            });
        }

        @Override
        public int getCount() {
            return adapter.getCount() + 2;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return adapter.isViewFromObject( arg0, arg1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(position == 0) {
                position = adapter.getCount() - 1;
            }
            else if(position == adapter.getCount() + 1) {
                position = 0;
            }
            else {
                position -= 1;
            }
            return adapter.instantiateItem( container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            adapter.destroyItem( container, position, object);
        }

    }
}
