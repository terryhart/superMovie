package com.huangyong.playerlib.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * creator huangyong
 * createTime 2019/4/4 下午10:20
 * path com.huangyong.playerlib.adapter
 * description:
 */
public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {
    private int itemSpace;
    private int itemNum;

    /**
     *
     * @param itemSpace item间隔
     * @param itemNum 每行item的个数
     */
    public RecyclerItemDecoration(int itemSpace, int itemNum) {
        this.itemSpace = itemSpace;
        this.itemNum = itemNum;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = itemSpace;
        if (parent.getChildLayoutPosition(view)%itemNum == 0){  //parent.getChildLayoutPosition(view) 获取view的下标
            outRect.left = 0;
        } else {
            outRect.left = itemSpace;
        }

    }
}
