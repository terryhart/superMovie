package com.bftv.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import chuangyuan.ycj.videolibrary.video.ManualPlayer;

/**
 * Created by Helloworld on 2018/7/20.
 */

public class PlayActivity extends AppCompatActivity {

    private ManualPlayer exoPlayerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.play_layout);

        exoPlayerManager = new ManualPlayer(this, R.id.exo_play_context_id);
        exoPlayerManager.setPlayUri(Uri.parse("http://192.168.11.119/water.mov"));
        exoPlayerManager.startPlayer();//开始播放
    }


    @Override
    protected void onDestroy() {
        exoPlayerManager.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayerManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayerManager.onResume();
    }
}
