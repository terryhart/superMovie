package com.huangyong.playerlib;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.danikula.videocache.HttpProxyCacheServer;

import org.song.videoplayer.DemoQSVideoView;
import org.song.videoplayer.IVideoPlayer;
import org.song.videoplayer.PlayListener;
import org.song.videoplayer.Util;
import org.song.videoplayer.cache.Proxy;
import org.song.videoplayer.floatwindow.FloatParams;
import org.song.videoplayer.media.AndroidMedia;
import org.song.videoplayer.media.BaseMedia;
import org.song.videoplayer.media.IjkExoMedia;
import org.song.videoplayer.media.IjkMedia;

import java.io.File;


/**
 * creator huangyong
 * createTime 2019/3/2 下午7:17
 * path cn.sddman.download.activity
 * description:
 */
public class PlayView extends AppCompatActivity {


    private DemoQSVideoView videoPlayer;
    private String url;
    private Class<? extends BaseMedia> decodeMedia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19)//透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.local_test_play_layout);

        videoPlayer = findViewById(R.id.player);
        String url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        videoPlayer.isWindowGesture = true;
        //进入全屏的模式 0横屏 1竖屏 2传感器自动横竖屏 3根据视频比例自动确定横竖屏      -1什么都不做
        videoPlayer.enterFullMode = 0;
        videoPlayer.setPlayListener(new PlayListener() {
            @Override
            public void onStatus(int status) {//播放状态
                if (status == IVideoPlayer.STATE_AUTO_COMPLETE)
                    videoPlayer.quitWindowFullscreen();//播放完成退出全屏
            }

            @Override//全屏/普通/浮窗
            public void onMode(int mode) {

            }

            @Override
            public void onEvent(int what, Integer... extra) {
                if (what == DemoQSVideoView.EVENT_CONTROL_VIEW & Build.VERSION.SDK_INT >= 19 & !videoPlayer.isWindowFloatMode())
                    if (extra[0] == 0)//状态栏隐藏/显示
                        Util.CLEAR_FULL(PlayView.this);
                    else
                        Util.SET_FULL(PlayView.this);
                //系统浮窗点击退出退出activity
                if (what == DemoQSVideoView.EVENT_CLICK_VIEW
                        && extra[0] == R.id.help_float_close)
                    if (videoPlayer.isSystemFloatMode())
                        finish();
            }

        });
        play(url, AndroidMedia.class);



    }

    private void play(String url, Class<? extends BaseMedia> decodeMedia) {
        videoPlayer.release();
        videoPlayer.setDecodeMedia(decodeMedia);
        videoPlayer.setUp(url, "这是一一一一一一一一一个标题");
        videoPlayer.getCoverImageView().setImageResource(R.drawable.preview_bg);
        videoPlayer.openCache();//缓存配置见最后
        videoPlayer.play();
        this.url = url;
        this.decodeMedia = decodeMedia;
        videoPlayer.invalidate();
    }


    public void 系统硬解(View v) {
        play(url, AndroidMedia.class);
        setTitle("系统硬解");

    }

    public void ijk_ffmepg解码(View v) {
        play(url, IjkMedia.class);
        setTitle("ijk_ffmepg解码");

    }



    public void ijk_exo解码(View v) {
        play(url, IjkExoMedia.class);
        setTitle("ijk_exo解码");

    }


    float rate = 1.f;

    public void 倍速(View v) {
        rate += 0.25f;
        if (rate > 2)
            rate = 0.25f;

        if (!videoPlayer.setSpeed(rate)) {
            Toast.makeText(this, "该解码器不支持", Toast.LENGTH_SHORT).show();
            rate = 1f;
        }
        ((Button) v).setText("倍速 X" + rate);
    }


    //android:launchMode="singleTask" 根据自己需求设置
    public void 系统浮窗(View v) {
        if (videoPlayer.getCurrentMode() == IVideoPlayer.MODE_WINDOW_FLOAT_ACT)
            return;
        enterFloat(true);
        ((Button) v).setText(videoPlayer.isWindowFloatMode() ? "退出浮窗" : "系统浮窗");
    }

    //返回键
    @Override
    public void onBackPressed() {
        //全屏和系统浮窗不finish
        if (videoPlayer.onBackPressed()) {
            if (videoPlayer.isSystemFloatMode())
                //系统浮窗返回上一界面 android:launchMode="singleTask"
                moveTaskToBack(true);
            return;
        }
        super.onBackPressed();
    }

    //进入浮窗
    private void enterFloat(boolean isSystemFloat) {
        FloatParams floatParams = videoPlayer.getFloatParams();
        if (floatParams == null) {
            floatParams = new FloatParams();
            floatParams.x = 0;
            floatParams.y = 0;
            floatParams.w = getResources().getDisplayMetrics().widthPixels * 3 / 4;
            floatParams.h = floatParams.w * 9 / 16;
            floatParams.round = 30;
            floatParams.fade = 0.8f;
            floatParams.canMove = true;
            floatParams.canCross = false;
        }
        floatParams.systemFloat = isSystemFloat;

        if (videoPlayer.isWindowFloatMode())
            videoPlayer.quitWindowFloat();
        else {
            if (!videoPlayer.enterWindowFloat(floatParams)) {
                Toast.makeText(this, "没有浮窗权限", Toast.LENGTH_LONG).show();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 0);
                }
            }
        }
        if (videoPlayer.isSystemFloatMode())
            onBackPressed();
    }



    //=======================以下生命周期控制=======================

    @Override
    public void onResume() {
        super.onResume();
        if (position > 0) {
            videoPlayer.seekTo(position);
            position = 0;
        }
    }

    int position;//记录销毁时的进度 回来继续盖进度播放

    @Override
    public void onPause() {
        super.onPause();
        //暂停
        videoPlayer.pause();
    }




    @Override
    public void onDestroy() {
        position = videoPlayer.getPosition();
        videoPlayer.release();
        super.onDestroy();//销毁
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (videoPlayer.getCurrentState() != IVideoPlayer.STATE_AUTO_COMPLETE)
                position = videoPlayer.getPosition();
                //videoPlayer.release();
        }
    };


    //缓存配置
    private void cacheConfig() {
        Proxy.setConfig(new HttpProxyCacheServer
                        .Builder(this)
                        .cacheDirectory(new File("/sdcard/zmovie_video"))
                        //.fileNameGenerator() 存储文件名规则
//                        .maxCacheSize(512 * 1024 * 1024)//缓存文件大小
                .maxCacheFilesCount(100)//缓存文件数目 二选一

        );
    }
}
