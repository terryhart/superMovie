package dev.baofeng.com.supermovie.bt;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class SwipeLayout extends FrameLayout {
	private View delete;
	private View content;
	private ViewDragHelper viewDragHelper;
	private boolean swipeLayoutState = false;

	public SwipeLayout(Context context) {
		this(context, null);
	}

	public SwipeLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		// 创建ViewDragHelper
		viewDragHelper = ViewDragHelper.create(this, callback);
	}

	// 获取子控件，对子控件进行位置的排版
	/**
	 * 布局文件中控件的结束标签加载完执行的操作，可以获取控件子控件的，但是不能获取宽高
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		content = getChildAt(0);
		delete = getChildAt(1);
	}

	// 对子控件进行排版操作
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		// 内容页
		content.layout(0, 0, content.getMeasuredWidth(),
				content.getMeasuredHeight());

		// 删除页面
		delete.layout(content.getMeasuredWidth(), 0, content.getMeasuredWidth()
				+ delete.getMeasuredWidth(), delete.getMeasuredHeight());
	}

	// 滑动操作实现
	// 创建触摸事件的回调监听
	ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
		// 设置是否捕获view的触摸事件
		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return true;
		}

		// 鸡肋方法，设置强制水平滑动，如果是水平滑动，返回大于0的任意值
		@Override
		public int getViewHorizontalDragRange(View child) {
			return 1;
		}

		// 滑动view的时候调用
		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			// 滑动内容页和删除页面的时候，对滑动的范围进行控制
			if (dx > 0) {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			if (child == content) {
				if (left > 0) {
					left = 0;
					swipeLayoutState = false;
				} else if (left < -delete.getMeasuredWidth()) {
					left = -delete.getMeasuredWidth();
					swipeLayoutState = true;
				}
			} else if (child == delete) {
				if (left > content.getMeasuredWidth()) {
					left = content.getMeasuredWidth();
					swipeLayoutState = false;
				} else if (left < (content.getMeasuredWidth() - delete
						.getMeasuredWidth())) {
					left = (content.getMeasuredWidth() - delete
							.getMeasuredWidth());
					swipeLayoutState = true;
				}
			}
			return left;
		}

		// 控件位置改变的时候调用的方法
		@Override
		public void onViewPositionChanged(View changedView, int left, int top,
				int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);

			// 当内容页的位置发生改变的时候，删除页面也要跟着改变
			if (changedView == content) {
				// 移动删除页面
				/*
				 * int newLeft = delete.getLeft()+dx;
				 * delete.layout(newLeft,0,newLeft
				 * +delete.getMeasuredWidth(),delete.getMeasuredHeight());
				 */
				// 参数1：移动的view
				// 参数2：根据那个值修改l和r的值
				ViewCompat.offsetLeftAndRight(delete, dx);// 修改l和r的值，
			} else if (changedView == delete) {
				// 如果删除界面的位置改变了，移动内容页面
				ViewCompat.offsetLeftAndRight(content, dx);// 修改l和r的值，
			}

			if (dx > dy) {
				// 即当水平滑动的时候 请求父控件不拦截事件
				getParent().requestDisallowInterceptTouchEvent(true);
			}

			if (listener != null) {
				if (content.getLeft() == -delete.getMeasuredWidth()) {
					listener.isOpen(true, SwipeLayout.this);
				} else if (content.getLeft() == 0) {
					listener.isOpen(false, SwipeLayout.this);
				}
			}
		}

		// 手指抬起调用的方法
		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			if (content.getLeft() > -delete.getMeasuredWidth() / 2) {
				// 关闭
				closeLayout();
			} else {
				// 打开操作
				openLayout();
			}
		}
	};
	private int startX;
	private int startY;

	/**
	 * 平滑回弹打开
	 */
	private void openLayout() {
		viewDragHelper
				.smoothSlideViewTo(content, -delete.getMeasuredWidth(), 0);
		ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
		swipeLayoutState = true;
	}

	/**
	 * 平滑回弹关闭
	 */
	public void closeLayout() {
		viewDragHelper.smoothSlideViewTo(content, 0, 0);
		ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
		swipeLayoutState = false;

	}

	/**
	 * 定义方法 快速关闭条目
	 */
	public void closeLayoutQuick() {
		// 内容页
		content.layout(0, 0, content.getMeasuredWidth(),
				content.getMeasuredHeight());

		// 删除页面
		delete.layout(content.getMeasuredWidth(), 0, content.getMeasuredWidth()
				+ delete.getMeasuredWidth(), delete.getMeasuredHeight());
	}

	/**
	 * 定义方法 获取当前控件的打开状态 默认是关闭的false
	 * 
	 * @return
	 */
	public boolean getSwipeLayoutSate() {

		return swipeLayoutState;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		// 判断是否能过继续移动
		if (viewDragHelper.continueSettling(true)) {
			ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
		}
	}

	// 将触摸事件传递给ViewDragHelper
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);
		return result;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		viewDragHelper.processTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX = (int) event.getX();
			startY = (int) event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveX = (int) event.getX();
			int moveY = (int) event.getY();
			int x = Math.abs(moveX - startX);
			int y = Math.abs(moveY - startY);
			if (x > y) {
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			break;
		case MotionEvent.ACTION_UP:

			break;

		default:
			break;
		}
		return true;
	}

	OnOpenListener listener;

	public void setOnOpenListener(OnOpenListener listener) {
		this.listener = listener;
	}

	public interface OnOpenListener {
		public void isOpen(boolean isopen, SwipeLayout swipeLayout);
	}
}
