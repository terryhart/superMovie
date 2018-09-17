package dev.baofeng.com.supermovie.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import chuangyuan.ycj.videolibrary.video.ManualPlayer;
import chuangyuan.ycj.videolibrary.video.VideoPlayerManager;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;
import dev.baofeng.com.supermovie.R;

/**
 * Created by huangyong on 2018/9/16.
 */

public class VideoActivity extends AppCompatActivity {

    @BindView(R.id.exo_play_context_id)
    VideoPlayerView exoPlayContextId;
    private ManualPlayer exoPlayerManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_layout);
        ButterKnife.bind(this);
        String playUrl = getIntent().getStringExtra(GlobalMsg.KEY_DOWN_URL);
        exoPlayerManager = new VideoPlayerManager.Builder(VideoPlayerManager.TYPE_PLAY_MANUAL, exoPlayContextId).create();
        exoPlayerManager.setPlayUri(Uri.parse(playUrl));
    }

    @Override
    protected void onResume() {
        super.onResume();
        exoPlayerManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayerManager.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        exoPlayerManager.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayerManager.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exoPlayerManager.onBackPressed();
    }
}
