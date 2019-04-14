package com.huangyong.playerlib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.listener.OnVideoViewStateChangeListener;
import com.dueeeke.videoplayer.player.AbstractPlayer;
import com.dueeeke.videoplayer.player.IjkPlayer;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.huangyong.playerlib.manager.PIPManager;
import com.huangyong.playerlib.model.M3u8Bean;
import com.huangyong.playerlib.util.WindowPermissionCheck;
import com.huangyong.playerlib.widget.AndroidMediaPlayer;

import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import zmovie.com.dlan.DlanLib;
import zmovie.com.dlan.DlanPresenter;

import static com.huangyong.playerlib.Params.PALY_LIST_URL;

/**
 * 在线资源播放页，不做判断就是爽
 */
public class OnlinePlayerActivity extends AppCompatActivity {

    private static int PlayMode = 0;
    private static final int REFRESH = 100;
    private String title;
    private String urlMd5;
    private String url;
    private String poster;
    private String path;
    private CustomControler controller;
    private PIPManager mPIPManager;
    private FrameLayout playerContainer;
    private IjkVideoView ijkVideoView;
    private DlanPresenter dlanPresenter;
    private AbstractPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_main);
        playerContainer = findViewById(R.id.exo_play_view);

        mPIPManager = PIPManager.getInstance();
        ijkVideoView = mPIPManager.getIjkVideoView();
        //四个参数，地址，海报，电影名称，需要到达的进度（第一次为0）
        url = getIntent().getStringExtra(PlayKey.PLAY_PATH_KEY);
        title = getIntent().getStringExtra(PlayKey.PLAY_TITLE_KEY);
        urlMd5 = getIntent().getStringExtra(PlayKey.URL_MD5_KEY);
        poster = getIntent().getStringExtra(PlayKey.POSTER_IMG_KEY);

        Bundle bundleExtra = getIntent().getBundleExtra(PlayKey.PALY_LIST_URL);

        controller = new CustomControler(this);
//        controller.getThumb().setImageResource(R.drawable.preview_bg);
        controller.setOnCheckListener(listener);
        controller.setLoadingTips(title);
        controller.setOnItemClickListener(clickedListener);
        controller.setOnstateChangeListener(changeListener);
        //选集播放
        if (bundleExtra!= null) {
            List<M3u8Bean> m3u8Bean = (List<M3u8Bean>) bundleExtra.getSerializable(PlayKey.PALY_LIST_URL);
            int currentIndex = bundleExtra.getInt(PlayKey.CURRENT_INDEX,0);
            controller.configPlayList(m3u8Bean,currentIndex);
        }

        ijkVideoView.setVideoController(controller);

        //系统mediaplayer不支持倍速，但是在线资源有的又不支持ijk的mediaplayer，无语
        if (url.endsWith("m3u8")){
            player = new AndroidMediaPlayer(this);
        }else {
            player = new IjkPlayer(this);
        }

        PlayMode = DlanLib.DLAN_MODE_ONLINE;


        if (mPIPManager.isStartFloatWindow()) {
            mPIPManager.stopFloatWindow();
            controller.setPlayerState(ijkVideoView.getCurrentPlayerState());
            controller.setPlayState(ijkVideoView.getCurrentPlayState());
        } else {
            mPIPManager.setActClass(OnlinePlayerActivity.class);
            controller.getThumb().setImageResource(R.drawable.preview_bg);


            //播放raw
          //  String vodUrl = "android.resource://" + getPackageName() + "/" + R.raw.logo;
            ijkVideoView.setUrl(url);
            ijkVideoView.setTitle(title);
            final PlayerConfig playerConfig = new PlayerConfig.Builder()
                    //启用边播边缓存功能
                   //  .autoRotate() //启用重力感应自动进入/退出全屏功能
//                .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
                    .savingProgress() //保存播放进度
                    .disableAudioFocus() //关闭AudioFocusChange监听
                    .setCustomMediaPlayer(player)
                    .build();
            //监听播放结束,如果有广告，可以在这里处理，我就不搞了，插个桩
            ijkVideoView.addOnVideoViewStateChangeListener(new OnVideoViewStateChangeListener() {
                @Override
                public void onPlayerStateChanged(int playerState) {

                }

                @Override
                public void onPlayStateChanged(int playState) {
                    if (playState == IjkVideoView.STATE_PLAYBACK_COMPLETED) {
                       // playVideo(url);
                    }
                }
            });
            ijkVideoView.setPlayerConfig(playerConfig);
        }
        playerContainer.addView(ijkVideoView);
        ijkVideoView.startFullScreen();
        ijkVideoView.start();
        initDlan();

    }

    /**
     * 播放正片(配合广告播放使用)）
     * @param url
     */
    private void playVideo(String url) {
        ijkVideoView.release();
        //重新设置数据
        ijkVideoView.setUrl(url);
        if (controller==null){
            controller = new CustomControler(this);
        }
        ijkVideoView.setTitle(title);
        //更换控制器
        ijkVideoView.setVideoController(controller);
        //开始播放
        ijkVideoView.start();
    }

    private void initDlan() {
        dlanPresenter = new DlanPresenter(this);
        dlanPresenter.initService();
    }


    PlayerActivity.OncheckListener listener = new PlayerActivity.OncheckListener() {
        @Override
        public void onChecked(int index) {

            if (ijkVideoView != null && controller != null) {

                switch (index) {
                    case 0:
                        player.setSpeed(1.0f);
                        controller.setCheckUpdate("正常");
                        break;
                    case 1:
                        player.setSpeed(1.25f);
                        controller.setCheckUpdate("1.25x");
                        break;
                    case 2:
                        player.setSpeed(1.5f);
                        controller.setCheckUpdate("1.5x");
                        break;
                    case 3:
                        player.setSpeed(1.75f);
                        controller.setCheckUpdate("1.75x");
                        break;
                    case 4:
                        player.setSpeed(2.0f);
                        controller.setCheckUpdate("2.0x");
                        break;
                    default:
                        player.setSpeed(1.0f);
                        controller.setCheckUpdate("1.0x");
                        break;
                }

            }
        }
    };


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
        intent.putExtra(PlayKey.PLAY_TITLE_KEY, title);
        intent.putExtra(PlayKey.PLAY_PATH_KEY, url);
        intent.putExtra(PlayKey.MOVIE_PROGRESS, ijkVideoView.getCurrentPosition() + "");
        intent.putExtra(PlayKey.URL_MD5_KEY, urlMd5);
        //用于区分是在线资源还是本地资源
        intent.putExtra(PlayKey.CENTENT_TYPE,PlayKey.CONTENT_M3U8);
        intent.putExtra(PlayKey.POSTER_IMG_KEY, poster);
        //安卓8.0对广播要求必须显示注册
        intent.setPackage("dev.baofeng.com.supermovie");
        intent.setAction(PlayKey.HISTORY_SAVE);
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

            if (TextUtils.isEmpty(path)) {
                Toast.makeText(OnlinePlayerActivity.this, "投屏功能暂不可用", Toast.LENGTH_SHORT).show();
                return;
            }
            if (path.startsWith("https://") || path.startsWith("http://") && dlanPresenter != null) {
                dlanPresenter.showDialogTip(OnlinePlayerActivity.this, path, title);
            } else {
                Toast.makeText(OnlinePlayerActivity.this, "已下载完成的视频,请到首页投屏助手观看", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onPic2Pic() {
            startFloatWindow();
        }

        @Override
        public void onLocalCast() {


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

    CustomControler.OnItemClickedListener clickedListener = new CustomControler.OnItemClickedListener() {
        @Override
        public void clicked(String url) {
            ijkVideoView.stopPlayback();
            ijkVideoView.release();
            ijkVideoView.setUrl(url);
            ijkVideoView.start();
        }
    };

}
