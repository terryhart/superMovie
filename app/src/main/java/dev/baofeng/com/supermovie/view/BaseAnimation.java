package dev.baofeng.com.supermovie.view;

import android.animation.Animator;
import android.view.View;

/**
 * creator huangyong
 * createTime 2018/11/24 下午1:38
 * path dev.baofeng.com.supermovie.view
 * description:
 */
public interface BaseAnimation {
    Animator[] getAnimators(View view);
}
