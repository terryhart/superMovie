package dev.baofeng.com.supermovie.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * creator huangyong
 * createTime 2018/11/18 上午12:13
 * path dev.baofeng.com.supermovie.view.widget
 * description:  自定义的加载更多的动画，有点懒，慢慢弄吧
 */
public class AutoLoadMoreView extends FrameLayout
        implements SwipeMenuRecyclerView.LoadMoreView,
        View.OnClickListener {

    private SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener;

    public AutoLoadMoreView(@NonNull Context context) {
        this(context,null);
    }

    public AutoLoadMoreView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AutoLoadMoreView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    public void onClick(View v) {
        if (mLoadMoreListener != null) mLoadMoreListener.onLoadMore();
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFinish(boolean dataEmpty, boolean hasMore) {

    }

    @Override
    public void onWaitToLoadMore(SwipeMenuRecyclerView.LoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
    }

    @Override
    public void onLoadError(int errorCode, String errorMessage) {

    }
}
