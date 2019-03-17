package com.huangyong.playerlib.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.huangyong.playerlib.IjkAndMediaPlayer;
import com.huangyong.playerlib.data.DataInter;
import com.huangyong.playerlib.manager.ivew.IPlayerView;
import com.huangyong.playerlib.util.OrientationSensor;
import com.huangyong.playerlib.util.PUtil;
import com.kk.taurus.playerbase.assist.AssistPlay;
import com.kk.taurus.playerbase.assist.OnAssistPlayEventHandler;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.render.AspectRatio;
import com.kk.taurus.playerbase.widget.BaseVideoView;

import tv.danmaku.ijk.media.exo.IjkExoMediaPlayer;


/**
 * creator huangyong
 * createTime 2019/3/13 下午9:08
 * path com.xiachufang.utils.video
 * description:
 */
public class PlayerPresenter implements OnPlayerEventListener {


    private Context context;
    private BaseVideoView baseVideoView;
    private boolean isLandscape;
    private ReceiverGroup receiverGroup;
    private RelationAssist mAssist;
    private boolean isLandScape;
    private ViewGroup container;
    private ReceiverGroup mReceiverGroup;
    private int mVideoContainerH;
    private AspectRatio mAspectRatio = AspectRatio.AspectRatio_FILL_PARENT;
    private IPlayerView iPlayerView;
    private OrientationSensor mOrientationSensor;

    public PlayerPresenter(Context mContext, IPlayerView iPlayerView) {
        this.context = mContext;
        this.iPlayerView = iPlayerView;
        init();
    }

    private void init() {
        mAssist = new RelationAssist(context);
        mAssist.getSuperContainer().setBackgroundColor(Color.TRANSPARENT);
        mAssist.setEventAssistHandler(eventHandler);
        mAssist.setOnPlayerEventListener(this);

        mAssist.setAspectRatio(mAspectRatio);
        mReceiverGroup = ReceiverGroupManager.get().getLiteReceiverGroup(context);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, true);
        mAssist.setReceiverGroup(mReceiverGroup);
    }

    private int mWhoIntentFullScreen;
    private final int WINDOW_INTENT_FULL_SCREEN = 2;

    private OnAssistPlayEventHandler eventHandler = new OnAssistPlayEventHandler() {
        @Override
        public void onAssistHandle(AssistPlay assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode) {
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                    ((Activity) context).onBackPressed();
                    break;
                case DataInter.Event.EVENT_CODE_ERROR_SHOW:
                    mAssist.stop();
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                    if (isLandScape) {
                        quitFullScreen();
                    } else {
                        mWhoIntentFullScreen = WINDOW_INTENT_FULL_SCREEN;
                        enterFullScreen();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_CLOSE:
                    normalPlay();
                    break;

            }
        }

        @Override
        public void requestRetry(AssistPlay assist, Bundle bundle) {
            if (PUtil.isTopActivity((Activity) context)) {
                super.requestRetry(assist, bundle);
            }
        }
    };

    /**
     * 不需要竖屏播放，可不做判断，直接返回false
     *
     * @return
     */
    public boolean onBackPressed() {
//        if (isLandScape){
//            quitFullScreen();
//            return true;
//        }else {
//            return false;
//        }
        return false;
    }


    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode) {
            case OnPlayerEventListener.PLAYER_EVENT_ON_VIDEO_RENDER_START:
                if (iPlayerView != null) {
                    iPlayerView.onPlayStart();
                }
                break;
            case OnPlayerEventListener.PLAYER_EVENT_ON_BUFFERING_END:

                if (iPlayerView != null) {
                    iPlayerView.onPlayFinish();
                }
                break;
        }
    }

    public void setData(String url, String title, final ViewGroup container) {
        this.container = container;

        container.post(new Runnable() {
            @Override
            public void run() {
                mVideoContainerH = container.getHeight();
            }
        });
        DataSource dataSource = new DataSource(url);
        dataSource.setTitle(title);
        mAssist.setDataSource(dataSource);
        mAssist.attachContainer(container);
        mAssist.play();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        isLandScape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        ViewGroup.LayoutParams params = container.getLayoutParams();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = mVideoContainerH;
        }
        container.setLayoutParams(params);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_IS_LANDSCAPE, isLandScape);
    }


    private void quitFullScreen() {
        ((Activity) context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void enterFullScreen() {
        ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (mWhoIntentFullScreen == WINDOW_INTENT_FULL_SCREEN) {
            normalPlay();
        }
    }

    private void normalPlay() {
        mAssist.attachContainer(container);
    }

    public AssistPlay getPlayer() {
        return mAssist;
    }

    public void pause() {
        if (mAssist != null) {
            mAssist.pause();
        }
    }

    public int getCurrentPosition() {
        if (mAssist != null) {
            return mAssist.getCurrentPosition();
        }
        return 0;
    }

    public void seekTo(int position) {
        if (mAssist != null) {
            mAssist.seekTo(position);
        }
    }

    public void start() {
        if (mAssist != null) {
            mAssist.play();
        }
    }

    public void stop() {
        if (mAssist != null) {
            mAssist.stop();
        }
    }

    public void configOrientationSensor(Activity context) {
        mOrientationSensor = new OrientationSensor(context, onOrientationListener);
        mOrientationSensor.disable();
    }

    /**
     * 获取屏幕方向感应，自动旋转屏幕,仅供演示，极光影院为直接横屏，故不使用
     */
    private OrientationSensor.OnOrientationListener onOrientationListener =
            new OrientationSensor.OnOrientationListener() {
                @Override
                public void onLandScape(int orientation) {
                    if (getPlayer().isInPlaybackState()) {
                        ((Activity) context).setRequestedOrientation(orientation);
                    }
                }

                @Override
                public void onPortrait(int orientation) {
                    if (getPlayer().isInPlaybackState()) {
                        ((Activity) context).setRequestedOrientation(orientation);
                    }
                }
            };

    public void enableOrientation() {
        if (mOrientationSensor != null) {
            mOrientationSensor.enable();
        }
    }

    public void disableOrientationSensor() {
        if (mOrientationSensor!=null){
            mOrientationSensor.disable();
        }
    }
}
