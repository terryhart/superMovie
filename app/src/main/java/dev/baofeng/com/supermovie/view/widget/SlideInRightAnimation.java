package dev.baofeng.com.supermovie.view.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

import dev.baofeng.com.supermovie.view.BaseAnimation;

/**
 * creator huangyong
 * createTime 2018/11/24 下午1:39
 * path dev.baofeng.com.supermovie.view.widget
 * description:
 */
public class SlideInRightAnimation implements BaseAnimation {
    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view,"alpha",0,1)
        };
    }
}
