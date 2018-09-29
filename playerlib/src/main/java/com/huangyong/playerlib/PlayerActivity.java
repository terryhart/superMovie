package com.huangyong.playerlib;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.AnimUtils;

import chuangyuan.ycj.videolibrary.listener.OnGestureProgressListener;
import chuangyuan.ycj.videolibrary.listener.VideoInfoListener;
import chuangyuan.ycj.videolibrary.listener.VideoWindowListener;
import chuangyuan.ycj.videolibrary.video.ManualPlayer;
import chuangyuan.ycj.videolibrary.video.VideoPlayerManager;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;

public class PlayerActivity extends AppCompatActivity implements AnimUtils.UpdateProgressListener {

    private ManualPlayer exoPlayerManager;
    private String title;
    private String urlMd5;
    private String movieProgress;
    private String progress="";
    private String url;
    private String poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_main);
        VideoPlayerView play_view = findViewById(R.id.exo_play_view);
        exoPlayerManager = new VideoPlayerManager.Builder(VideoPlayerManager.TYPE_PLAY_MANUAL, play_view).addUpdateProgressListener(this).create();
        //四个参数，地址，海报，电影名称，需要到达的进度（第一次为0）
        url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        title = getIntent().getStringExtra(Params.TASK_TITLE_KEY);
        urlMd5 = getIntent().getStringExtra(Params.URL_MD5_KEY);
        movieProgress = getIntent().getStringExtra(Params.MOVIE_PROGRESS);
        poster = getIntent().getStringExtra(Params.POST_IMG_KEY);
        if (!TextUtils.isEmpty(url)){
            if (TextUtils.isEmpty(movieProgress)){
                exoPlayerManager.setPlayUri(Uri.parse(url));
            }else {
                Toast.makeText(this, "继续上次观看", Toast.LENGTH_SHORT).show();
                exoPlayerManager.setPlayUri(Uri.parse(url));
                exoPlayerManager.seekTo(Long.parseLong(movieProgress));
            }

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
        Intent intent = new Intent();
        intent.putExtra(Params.TASK_TITLE_KEY,title);
        intent.putExtra(Params.LOCAL_PATH_KEY,url);
        Log.e("movieprogress",progress);
        intent.putExtra(Params.MOVIE_PROGRESS,progress);

        intent.putExtra(Params.URL_MD5_KEY,urlMd5);
        intent.putExtra(Params.POST_IMG_KEY,poster);
        intent.setAction(Params.HISTORY_SAVE);
        sendBroadcast(intent);
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



    @Override
    public void updateProgress(long position, long bufferedPosition, long duration) {
        Log.e("movieprogress",String.valueOf(position));
        this.progress = String.valueOf(position);
    }
}
