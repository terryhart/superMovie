package com.huangyong.playerlib.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.huangyong.playerlib.PlayerbaseActivity;
import com.huangyong.playerlib.cover.CloseCover;
import com.huangyong.playerlib.cover.GestureCover;
import com.huangyong.playerlib.data.DataInter;
import com.huangyong.playerlib.manager.ivew.IPlayerView;
import com.huangyong.playerlib.util.OrientationSensor;
import com.huangyong.playerlib.util.PUtil;
import com.huangyong.playerlib.util.WindowPermissionCheck;
import com.kk.taurus.playerbase.assist.AssistPlay;
import com.kk.taurus.playerbase.assist.OnAssistPlayEventHandler;
import com.kk.taurus.playerbase.assist.RelationAssist;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.render.AspectRatio;
import com.kk.taurus.playerbase.widget.BaseVideoView;
import com.kk.taurus.playerbase.window.FloatWindow;
import com.kk.taurus.playerbase.window.FloatWindowParams;

import static com.kk.taurus.playerbase.config.AppContextAttach.getApplicationContext;


/**
 * creator huangyong
 * createTime 2019/3/13 下午9:08
 * path com.xiachufang.utils.video
 * description:
 */
public class PlayerPresenter implements OnPlayerEventListener {


    private Context context;
    private RelationAssist mAssist;
    private boolean isLandScape;
    private ViewGroup container;
    private ReceiverGroup mReceiverGroup;
    private int mVideoContainerH;
    private AspectRatio mAspectRatio = AspectRatio.AspectRatio_FIT_PARENT;
    private IPlayerView iPlayerView;
    private OrientationSensor mOrientationSensor;
    private FloatWindow mFloatWindow;
    private FrameLayout mWindowVideoContainer;
    private final int VIEW_INTENT_FULL_SCREEN = 1;
    public PlayerPresenter(Context mContext, IPlayerView iPlayerView) {
        this.context = mContext;
        this.iPlayerView = iPlayerView;
        init();
    }

    private void init() {
        mAssist = new RelationAssist(context);
        mAssist.getSuperContainer().setBackgroundColor(Color.TRANSPARENT);
        mAssist.setEventAssistHandler(eventHandler);

        mAssist.setAspectRatio(mAspectRatio);
        mReceiverGroup = ReceiverGroupManager.get().getLiteReceiverGroup(context);
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_NETWORK_RESOURCE, true);
        mAssist.setReceiverGroup(mReceiverGroup);

        //浮窗相关初始化
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int width = (int) (widthPixels * 0.8f);

        int type;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){//8.0+
            type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }else {
            type =  WindowManager.LayoutParams.TYPE_PHONE;
        }
        mWindowVideoContainer = new FrameLayout(context);
        mFloatWindow = new FloatWindow(context, mWindowVideoContainer,
                new FloatWindowParams()
                        .setWindowType(type)
                        .setX(100)
                        .setY(400)
                        .setWidth(width)
                        .setHeight(width*9/16));
        mFloatWindow.setBackgroundColor(Color.BLACK);

        //初状态
        changeMode(false);
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
                    if(isLandScape){
                        quitFullScreen();
                    }else{
                        mWhoIntentFullScreen =
                                mFloatWindow.isWindowShow()?
                                        WINDOW_INTENT_FULL_SCREEN:
                                        VIEW_INTENT_FULL_SCREEN;
                        enterFullScreen();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_CLOSE:
                    normalPlay();
                    break;
                case DataInter.Event.EVENT_CODE_TO_FLOAT_MODE:
                    if(WindowPermissionCheck.checkPermission((Activity) context)){
                        windowPlay();
                        //直接到桌面
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        context.startActivity(intent);
                    }
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

        if (isLandScape){
            quitFullScreen();
            return true;
        }else {
            return false;
        }
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

        if(mWhoIntentFullScreen==WINDOW_INTENT_FULL_SCREEN){
            windowPlay();
        }
    }

    private void windowPlay() {
        if(!mFloatWindow.isWindowShow()){
            changeMode(true);
            mFloatWindow.setElevationShadow(20);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                mFloatWindow.setRoundRectShape(10);
            mFloatWindow.show();
            mAssist.attachContainer(mWindowVideoContainer);
        }
    }

    public void enterFullScreen() {

        if (PUtil.isTopActivity((Activity) context)){

            ((Activity) context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else {
            context.startActivity(new Intent(getApplicationContext(), PlayerbaseActivity.class));
        }

    }

    private void normalPlay() {
        changeMode(false);
        mAssist.attachContainer(container);
        closeWindow();
    }

    private void closeWindow(){
        if(mFloatWindow.isWindowShow()){
            mFloatWindow.close();
        }
    }
    private void changeMode(boolean window){
        if (context==null){
            return;
        }
        if(window){
            mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER);
            mReceiverGroup.addReceiver(DataInter.ReceiverKey.KEY_CLOSE_COVER, new CloseCover(context));
        }else{
            mReceiverGroup.removeReceiver(DataInter.ReceiverKey.KEY_CLOSE_COVER);
            mReceiverGroup.addReceiver(DataInter.ReceiverKey.KEY_GESTURE_COVER, new GestureCover(context));
        }
        mReceiverGroup.getGroupValue().putBoolean(DataInter.Key.KEY_CONTROLLER_TOP_ENABLE, !window);
    }

    public AssistPlay getPlayer() {
        return mAssist;
    }


    public void start() {
        if (mAssist != null) {
            mAssist.play();
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

    public void onPause() {
        int state = mAssist.getState();
        if(state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return;
        if (mFloatWindow.isWindowShow()){
            return;
        }
        if(mAssist.isInPlaybackState()){
            mAssist.pause();
        }else{
            mAssist.stop();
        }
    }

    public void onResume() {
        int state = mAssist.getState();
        if(state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return;
        if(mAssist.isInPlaybackState()){
            mAssist.resume();
        }else{
            mAssist.rePlay(0);
        }
    }

    public void onDestroy() {
        closeWindow();
        mAssist.destroy();
    }

    public void onStop() {
        disableOrientationSensor();
    }
}
