package com.huangyong.playerlib;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.huangyong.playerlib.cover.CloseCover;
import com.huangyong.playerlib.cover.ControllerCover;
import com.huangyong.playerlib.cover.GestureCover;
import com.huangyong.playerlib.data.DataInter;
import com.huangyong.playerlib.util.OrientationSensor;
import com.huangyong.playerlib.util.PUtil;
import com.kk.taurus.playerbase.assist.InterEvent;
import com.kk.taurus.playerbase.assist.OnVideoViewEventHandler;
import com.kk.taurus.playerbase.entity.DataSource;
import com.kk.taurus.playerbase.event.OnPlayerEventListener;
import com.kk.taurus.playerbase.player.IPlayer;
import com.kk.taurus.playerbase.receiver.OnReceiverEventListener;
import com.kk.taurus.playerbase.receiver.ReceiverGroup;
import com.kk.taurus.playerbase.widget.BaseVideoView;

public class IjkAndMediaPlayer extends AppCompatActivity implements OnPlayerEventListener, OnReceiverEventListener {

    private BaseVideoView mVideoView;
    private DataSource mDataSource;

    private boolean hasStart;

    String KEY_LOADING_COVER = "loading_cover";
    String KEY_CONTROLLER_COVER = "controller_cover";
    String KEY_GESTURE_COVER = "gesture_cover";
    String KEY_COMPLETE_COVER = "complete_cover";
    String KEY_ERROR_COVER = "error_cover";
    String KEY_CLOSE_COVER = "close_cover";
    private ReceiverGroup receiverGroup;
    String KEY_IS_LANDSCAPE = "isLandscape";
    private String url;
    private OrientationSensor mOrientationSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_and_media_player);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        mVideoView = findViewById(R.id.videoView);
        mVideoView.setOnPlayerEventListener(this);
        mVideoView.setOnReceiverEventListener(this);

        mOrientationSensor = new OrientationSensor(this,onOrientationListener);
        mOrientationSensor.enable();

        receiverGroup = new ReceiverGroup(null);
//        receiverGroup.addReceiver(KEY_LOADING_COVER, new LoadingCover(context));
        receiverGroup.addReceiver(KEY_CONTROLLER_COVER, new ControllerCover(this));
//        receiverGroup.addReceiver(KEY_GESTURE_COVER, new GestureCover(this));
//        receiverGroup.addReceiver(KEY_COMPLETE_COVER, new CompleteCover(this));
//        receiverGroup.addReceiver(KEY_ERROR_COVER, new ErrorCover(this));
        mVideoView.setReceiverGroup(receiverGroup);


        //设置一个事件处理器
        mVideoView.setEventHandler(onVideoViewEventHandler);
//设置数据提供者 MonitorDataProvider
//        mVideoView.setDataProvider(new MonitorDataProvider());
    }
    private boolean userPause;

    private OnVideoViewEventHandler onVideoViewEventHandler = new OnVideoViewEventHandler(){
        @Override
        public void onAssistHandle(BaseVideoView assist, int eventCode, Bundle bundle) {
            super.onAssistHandle(assist, eventCode, bundle);
            switch (eventCode){
                case InterEvent.CODE_REQUEST_PAUSE:
                    userPause = true;
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_BACK:
                    if(isLandscape){
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }else{
                        finish();
                    }
                    break;
                case DataInter.Event.EVENT_CODE_REQUEST_TOGGLE_SCREEN:
                    setRequestedOrientation(isLandscape ?
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case DataInter.Event.EVENT_CODE_ERROR_SHOW:
                    mVideoView.stop();
                    break;
            }
        }

        @Override
        public void requestRetry(BaseVideoView videoView, Bundle bundle) {
            if(PUtil.isTopActivity(IjkAndMediaPlayer.this)){
                super.requestRetry(videoView, bundle);
            }
        }
    };

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {

    }

    @Override
    public void onReceiverEvent(int eventCode, Bundle bundle) {

    }
    private boolean isLandscape;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            isLandscape = true;
//            updateVideo(true);
        }else{
            isLandscape = false;
//            updateVideo(false);
        }
        receiverGroup.getGroupValue().putBoolean(KEY_IS_LANDSCAPE, isLandscape);
    }
    @Override
    public void onBackPressed() {
        if(isLandscape){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int state = mVideoView.getState();
        if(state == IPlayer.STATE_PLAYBACK_COMPLETE)
            return;
        if(mVideoView.isInPlaybackState()){
            if(!userPause)
                mVideoView.resume();
        }else{
            mVideoView.rePlay(0);
        }
        initPlay();
    }

    private void initPlay(){
        if(!hasStart){
            DataSource dataSource = new DataSource(url);
            dataSource.setTitle("音乐和艺术如何改变世界");
            mVideoView.setDataSource(dataSource);
            mVideoView.start();
            hasStart = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
        mOrientationSensor.disable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mOrientationSensor.enable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mOrientationSensor.disable();
    }


    /**
     * 获取屏幕方向感应，自动旋转屏幕
     */
    private OrientationSensor.OnOrientationListener onOrientationListener =
            new OrientationSensor.OnOrientationListener() {
                @Override
                public void onLandScape(int orientation) {
                    if(mVideoView.isInPlaybackState()){
                        setRequestedOrientation(orientation);
                    }
                }
                @Override
                public void onPortrait(int orientation) {
                    if(mVideoView.isInPlaybackState()){
                        setRequestedOrientation(orientation);
                    }
                }
            };
}
