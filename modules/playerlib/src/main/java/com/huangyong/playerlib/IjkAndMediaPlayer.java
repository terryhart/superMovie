package com.huangyong.playerlib;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.huangyong.playerlib.manager.PlayerPresenter;
import com.huangyong.playerlib.manager.ivew.IPlayerView;
import com.kk.taurus.playerbase.config.PlayerConfig;

/**
 * playBase播放开源库，地址：https://github.com/jiajunhui/PlayerBase
 */
public class IjkAndMediaPlayer extends AppCompatActivity implements IPlayerView {

    private FrameLayout container;
    private String url;

    /**
     * 播放器管理封装
     */
    private PlayerPresenter playerPresenter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_and_media_player);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                , WindowManager.LayoutParams.FLAG_FULLSCREEN);

        url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        title = getIntent().getStringExtra(Params.TASK_TITLE_KEY);
        container = findViewById(R.id.player_container);

        //在线资源目前只能通过mediaplayer解码播放，至于ijk、exo如何配置还没解决
        PlayerConfig.setDefaultPlanId(PlayerConfig.DEFAULT_PLAN_ID);

        playerPresenter = new PlayerPresenter(this,this);

        playerPresenter.configOrientationSensor(this);

        playerPresenter.setData(url, title,container);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        playerPresenter.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (playerPresenter.onBackPressed()){
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        playerPresenter.getPlayer().resume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerPresenter.getPlayer().destroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        playerPresenter.enableOrientation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerPresenter.disableOrientationSensor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerPresenter.getPlayer().pause();
    }



    @Override
    public void onPlayStart() {

    }

    @Override
    public void onPlayFinish() {

    }
}
