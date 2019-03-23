package com.huangyong.playerlib;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dueeeke.videoplayer.player.AbstractPlayer;
import com.dueeeke.videoplayer.player.IjkPlayer;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.huangyong.playerlib.manager.PIPManager;
import com.huangyong.playerlib.util.WindowPermissionCheck;
import com.huangyong.playerlib.widget.AndroidMediaPlayer;

import java.io.File;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import zmovie.com.dlan.DeviceListActivityPage;
import zmovie.com.dlan.DlanLib;
import zmovie.com.dlan.DlanLocalActivity;
import zmovie.com.dlan.DlanPresenter;


public class PlayerActivity extends AppCompatActivity {

    private static int PlayMode =0;
    private String title;
    private String urlMd5;
    private String movieProgress;
    private String progress = "";
    private String url;
    private String poster;
    private String path;
    private CustomControler controller;
    private PIPManager mPIPManager;
    private FrameLayout playerContainer;
    private IjkVideoView ijkVideoView;
    private DlanPresenter dlanPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_main);
        playerContainer = findViewById(R.id.exo_play_view);

        mPIPManager = PIPManager.getInstance();
        ijkVideoView = mPIPManager.getIjkVideoView();
        //四个参数，地址，海报，电影名称，需要到达的进度（第一次为0）
        url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        title = getIntent().getStringExtra(Params.TASK_TITLE_KEY);
        urlMd5 = getIntent().getStringExtra(Params.URL_MD5_KEY);
        poster = getIntent().getStringExtra(Params.POST_IMG_KEY);

        ijkVideoView.setTitle(title);

        controller = new CustomControler(this);
        controller.getThumb().setImageResource(R.drawable.preview_bg);
        controller.setOnCheckListener(listener);
        controller.setOnstateChangeListener(changeListener);
        ijkVideoView.setVideoController(controller);


        //设置这个是为了ijk支持4K视频播放
        IjkPlayer ijkPlayer = new IjkPlayer(this) {
            @Override
            public void setEnableMediaCodec(boolean isEnable) {
                int value = isEnable ? 1 : 0;
                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-hevc", value);
            }

            @Override
            public void setOptions() {
                super.setOptions();
                //设置ijkplayer支持concat协议，以播放分段视频
                mMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "protocol_whitelist", "rtmp,concat,ffconcat,file,subfile,http,https,tls,rtp,tcp,udp,crypto");
            }
        };

        AbstractPlayer player = new AndroidMediaPlayer(this);


        //如果是本地文件，如果是在线视频
        if (!TextUtils.isEmpty(url) && url.startsWith("/storage")) {
            File file = new File(url);
            if (file.exists()) {
                path = Uri.parse("file://" + file.getAbsolutePath()).toString();
            }
            //如果是本地文件。投屏走本地投屏
            Log.e("urllocalse",url+"----"+path);
            PlayMode = DlanLib.DLAN_MODE_LOCAL;
        } else {
            path = url;
            //如果是在线资源，投屏走在线投屏
            PlayMode = DlanLib.DLAN_MODE_ONLINE;
        }

        if (mPIPManager.isStartFloatWindow()) {
            mPIPManager.stopFloatWindow();
            controller.setPlayerState(ijkVideoView.getCurrentPlayerState());
            controller.setPlayState(ijkVideoView.getCurrentPlayState());
        } else {
            mPIPManager.setActClass(PlayerActivity.class);
            controller.getThumb().setImageResource(R.drawable.preview_bg);

            ijkVideoView.setUrl(path);
            ijkVideoView.setTitle(title);
            PlayerConfig playerConfig = new PlayerConfig.Builder()
                    //启用边播边缓存功能
                    // .autoRotate() //启用重力感应自动进入/退出全屏功能
//                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
                    .savingProgress() //保存播放进度
                    .disableAudioFocus() //关闭AudioFocusChange监听
                    .setCustomMediaPlayer(player)
                    .build();

            ijkVideoView.setPlayerConfig(playerConfig);
        }
        playerContainer.addView(ijkVideoView);
        ijkVideoView.startFullScreen();
        ijkVideoView.start();

        initDlan();
    }

    private void initDlan() {
        dlanPresenter = new DlanPresenter(this);
        dlanPresenter.initService();
    }


    OncheckListener listener = new OncheckListener() {
        @Override
        public void onChecked(int index) {

            if (ijkVideoView != null && controller != null) {

                switch (index) {
                    case 0:
                        ijkVideoView.setSpeed(1.0f);
                        controller.setCheckUpdate("正常");
                        break;
                    case 1:
                        ijkVideoView.setSpeed(1.25f);
                        controller.setCheckUpdate("1.25x");
                        break;
                    case 2:
                        ijkVideoView.setSpeed(1.5f);
                        controller.setCheckUpdate("1.5x");
                        break;
                    case 3:
                        ijkVideoView.setSpeed(1.75f);
                        controller.setCheckUpdate("1.75x");
                        break;
                    case 4:
                        ijkVideoView.setSpeed(2.0f);
                        controller.setCheckUpdate("2.0x");
                        break;
                    default:
                        ijkVideoView.setSpeed(1.0f);
                        controller.setCheckUpdate("1.0x");
                        break;
                }

            }
        }
    };

    interface OncheckListener {
        void onChecked(int index);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPIPManager.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPIPManager.pause();
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.putExtra(Params.TASK_TITLE_KEY, title);
        intent.putExtra(Params.LOCAL_PATH_KEY, url);
        intent.putExtra(Params.MOVIE_PROGRESS, ijkVideoView.getCurrentPosition() + "");
        intent.putExtra(Params.URL_MD5_KEY, urlMd5);
        intent.putExtra(Params.POST_IMG_KEY, poster);
        intent.setAction(Params.HISTORY_SAVE);
        sendBroadcast(intent);
        super.onDestroy();
        mPIPManager.reset();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


    CustomControler.OnstateChangeListener changeListener = new CustomControler.OnstateChangeListener() {
        @Override
        public void onAirPlay() {

            if ( PlayMode == DlanLib.DLAN_MODE_ONLINE){

                dlanPresenter.showDialogTip(PlayerActivity.this,path,title);

//                if (dlanPresenter!=null){
//                    dlanPresenter.startPlay(path,title);
//                }

            }else {


            }
        }

        @Override
        public void onPic2Pic() {
            startFloatWindow();
        }

        @Override
        public void onLocalCast() {
            startActivity(new Intent(PlayerActivity.this, DlanLocalActivity.class));
        }
    };

    /**
     * 小窗播放
     */
    public void startFloatWindow() {

        if (WindowPermissionCheck.checkPermission(this)) {
            mPIPManager.startFloatWindow();
            finish();
        }
    }
}
