package com.huangyong.playerlib;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import chuangyuan.ycj.videolibrary.video.ManualPlayer;
import chuangyuan.ycj.videolibrary.video.VideoPlayerManager;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;

public class PlayerActivity extends AppCompatActivity {

    private ManualPlayer exoPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);
        VideoPlayerView play_view = findViewById(R.id.exo_play_view);
         exoPlayerManager = new VideoPlayerManager.Builder(VideoPlayerManager.TYPE_PLAY_MANUAL, play_view).create();
        String url = getIntent().getStringExtra("PROXY_PALY_URL");
        if (!TextUtils.isEmpty(url)){
            exoPlayerManager.setPlayUri(url);
            exoPlayerManager.startPlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayerManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayerManager.onPause();
    }


    @Override
    protected void onDestroy() {
        exoPlayerManager.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //使用播放返回键监听
        if(!exoPlayerManager.onBackPressed()){
            finish();
        }
    }
}
