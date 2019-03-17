package com.huangyong.playerlib.widget;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.huangyong.playerlib.Params;
import com.huangyong.playerlib.R;
import com.huangyong.playerlib.manager.PIPManager;
import com.huangyong.playerlib.util.WindowPermissionCheck;

import java.io.File;

public class PIPActivity extends AppCompatActivity implements View.OnClickListener {
    private PIPManager mPIPManager;
    private static String url;
    private String title;
    private String path;
    private StandardVideoControllers controller;
    private IjkVideoView ijkVideoView;
    private LinearLayout configLayout;
    private ImageView pic2pic;
    private ImageView airPlay;
    //    private static final String URL = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
//    private static final String URL = "http://mov.bn.netease.com/open-movie/nos/flv/2017/01/03/SC8U8K7BC_hd.flv";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pip);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        title = getIntent().getStringExtra(Params.TASK_TITLE_KEY);

        //如果是本地文件，如果是在线视频
        if (!TextUtils.isEmpty(url)&&url.startsWith("/storage")){
            File file = new File(url);
            if(file.exists()){
                path = Uri.parse("file://"+file.getAbsolutePath()).toString();
            }
        }else {
            path=url;
        }


        configLayout = findViewById(R.id.playConfig);
        airPlay = findViewById(R.id.airplay);
        pic2pic = findViewById(R.id.pic2pic);
        pic2pic.setOnClickListener(this);
        airPlay.setOnClickListener(this);

        FrameLayout playerContainer = findViewById(R.id.player_container);
        mPIPManager = PIPManager.getInstance();
        ijkVideoView = mPIPManager.getIjkVideoView();
         controller = new StandardVideoControllers(this);
        controller.getThumb().setImageResource(R.drawable.preview_bg);
        controller.setOnstateChangeListener(changeListener);

        ijkVideoView.setVideoController(controller);
        if (mPIPManager.isStartFloatWindow()) {
            mPIPManager.stopFloatWindow();
            controller.setPlayerState(ijkVideoView.getCurrentPlayerState());
            controller.setPlayState(ijkVideoView.getCurrentPlayState());
        } else {
            mPIPManager.setActClass(PIPActivity.class);
            ijkVideoView.setUrl(path);
            ijkVideoView.setTitle(title);
            PlayerConfig config = new PlayerConfig.Builder()
                    .build();
            ijkVideoView.setPlayerConfig(config);
        }
        playerContainer.addView(ijkVideoView);
        ijkVideoView.start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPIPManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPIPManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPIPManager.reset();
    }


    @Override
    public void onBackPressed() {
        if (mPIPManager.onBackPress()) return;
        super.onBackPressed();
    }

    public void startFloatWindow() {

        if (WindowPermissionCheck.checkPermission(this)){
            mPIPManager.startFloatWindow();
            finish();
        }

    }

    StandardVideoControllers.OnstateChangeListener changeListener = new StandardVideoControllers.OnstateChangeListener() {

        @Override
        public void onConfigShow() {
            configLayout.setVisibility(View.VISIBLE);
        }

        @Override
        public void onConfigHide() {
            configLayout.setVisibility(View.INVISIBLE);
        }

        @Override
        public void startFlow() {
            startFloatWindow();
        }

        @Override
        public void fullscreen() {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    };

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.airplay) {

        } else if (i == R.id.pic2pic) {
            startFloatWindow();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
