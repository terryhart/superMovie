package com.huangyong.playerlib;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.huangyong.playerlib.manager.QsyPresenter;
import com.huangyong.playerlib.manager.ivew.IQsyView;

import org.song.videoplayer.DemoQSVideoView;
import org.song.videoplayer.IVideoPlayer;
import org.song.videoplayer.floatwindow.FloatParams;

import java.io.File;


/**
 * creator huangyong
 * createTime 2019/3/2 下午7:17
 * path cn.sddman.download.activity
 * description:
 */
public class QsyPlayerActivity extends AppCompatActivity implements IQsyView {


    private DemoQSVideoView videoPlayer;
    private String title;
    private QsyPresenter presenter;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19)//透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.local_test_play_layout);

        videoPlayer = findViewById(R.id.player);
        String url = getIntent().getStringExtra(Params.PROXY_PALY_URL);
        title = getIntent().getStringExtra(Params.TASK_TITLE_KEY);

        //如果是本地文件，如果是在线视频
        if (!TextUtils.isEmpty(url)&&url.startsWith("/storage")){
            File file = new File(url);
            if(file.exists()){
                path = Uri.parse("file://"+file.getAbsolutePath()).toString();
            }
        }else {
            path=url;
            if (TextUtils.isEmpty(path)){
                return;
            }
        }
        presenter = new QsyPresenter(this,this,videoPlayer,path,title);


        presenter.init();

        presenter.play();



    }






    //返回键
    @Override
    public void onBackPressed() {
        //全屏和系统浮窗不finish
        if (presenter!=null){
            presenter.onbackPress();
        }
        super.onBackPressed();
    }

    //=======================以下生命周期控制=======================

    @Override
    public void onResume() {
        super.onResume();

        if (presenter!=null){
            presenter.onresume();
        }

    }



    @Override
    public void onPause() {
        super.onPause();
        //暂停

        videoPlayer.pause();
    }

    @Override
    public void onDestroy() {
        if (presenter!=null)
        presenter.destroy(videoPlayer.getPosition());
        super.onDestroy();//销毁
    }


    @Override
    public void onback() {

    }

    public void windowplay(View view) {
        if (videoPlayer.getCurrentMode() == IVideoPlayer.MODE_WINDOW_FLOAT_ACT)
            return;
        enterFloat(true);
        ((Button) view).setText(videoPlayer.isWindowFloatMode() ? "退出浮窗" : "系统浮窗");
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
            floatParams.fade = 1f;
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
}
