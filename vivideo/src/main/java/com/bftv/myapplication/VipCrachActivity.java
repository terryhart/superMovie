package com.bftv.myapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import chuangyuan.ycj.videolibrary.video.ManualPlayer;

/**
 * Created by Helloworld on 2018/7/20.
 */

public class VipCrachActivity extends AppCompatActivity {

    private ManualPlayer exoPlayerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crach_layout);


        exoPlayerManager = new ManualPlayer(this, R.id.exo_play_id);
        exoPlayerManager.setPlayUri(Uri.parse(getIntent().getStringExtra("URL")));
        exoPlayerManager.startPlayer();//开始播放
    }
}
