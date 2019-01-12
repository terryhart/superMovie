package com.huangyong.playerlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.dueeeke.videoplayer.player.IjkVideoView;

/**
 * creator huangyong
 * createTime 2018/12/21 下午1:01
 * path com.huangyong.playerlib
 * description:
 */
public class CustomIjkplayer extends IjkVideoView {

    public CustomIjkplayer(@NonNull Context context) {
        this(context, null);
    }

    public CustomIjkplayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomIjkplayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean checkNetwork() {
        return false;
    }
}
