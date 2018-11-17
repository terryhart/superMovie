package com.huangyong.playerlib;

import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;

import java.io.File;


public class PlayerActivity extends AppCompatActivity {

    private String title;
    private String urlMd5;
    private String movieProgress;
    private String progress="";
    private String url;
    private String poster;
    private IjkVideoView ijkVideoView;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player_main);
        ijkVideoView = findViewById(R.id.exo_play_view);
        //四个参数，地址，海报，电影名称，需要到达的进度（第一次为0）
        url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        title = getIntent().getStringExtra(Params.TASK_TITLE_KEY);
        urlMd5 = getIntent().getStringExtra(Params.URL_MD5_KEY);
        movieProgress = getIntent().getStringExtra(Params.MOVIE_PROGRESS);
        poster = getIntent().getStringExtra(Params.POST_IMG_KEY);
        ijkVideoView.setTitle(title);

        StandardVideoController controller = new StandardVideoController(this);
        ijkVideoView.setVideoController(controller);


        PlayerConfig playerConfig = new PlayerConfig.Builder()
                //启用边播边缓存功能
               // .autoRotate() //启用重力感应自动进入/退出全屏功能
               // .enableMediaCodec()//启动硬解码，启用后可能导致视频黑屏，音画不同步
                .usingSurfaceView() //启用SurfaceView显示视频，不调用默认使用TextureView
                .savingProgress() //保存播放进度
                .disableAudioFocus() //关闭AudioFocusChange监听
                .setLooping() //循环播放当前正在播放的视频
                .build();
        ijkVideoView.setPlayerConfig(playerConfig);
        Log.e("exoplaypath--",url);

        if (url.startsWith("/storage")){

        }
       /* if (!TextUtils.isEmpty(movieProgress)&&!TextUtils.isEmpty(url)){
            if (Integer.parseInt(movieProgress)==0){
                ijkVideoView.setUrl(url);
            }else {
                ijkVideoView.setUrl(url);
            }
            ijkVideoView.startFullScreen();
        }else {
            movieProgress ="0";
        }*/

        File file = new File(url);
        if(file.exists()){
            path = Uri.parse("file://"+file.getAbsolutePath()).toString();
        }
        ijkVideoView.setUrl(path);
        ijkVideoView.startFullScreen();
        ijkVideoView.start();
//        exoPlayerManager.setPlaybackParameters(1f, 1f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkVideoView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        ijkVideoView.pause();
    }


    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.putExtra(Params.TASK_TITLE_KEY,title);
        intent.putExtra(Params.LOCAL_PATH_KEY,url);
        Log.e("movieprogress", ijkVideoView.getCurrentPosition()+"");
        intent.putExtra(Params.MOVIE_PROGRESS, ijkVideoView.getCurrentPosition());

        intent.putExtra(Params.URL_MD5_KEY,urlMd5);
        intent.putExtra(Params.POST_IMG_KEY,poster);
        intent.setAction(Params.HISTORY_SAVE);
        sendBroadcast(intent);
        super.onDestroy();
        ijkVideoView.release();
    }

    @Override
    public void onBackPressed() {
        if (!ijkVideoView.onBackPressed()) {
            super.onBackPressed();
        }
    }



    /*@Override
    public void updateProgress(long position, long bufferedPosition, long duration) {
        Log.e("movieprogress",String.valueOf(position));
        this.progress = String.valueOf(position);
    }*/
}
