package dev.baofeng.com.supermovie;
/**
 * Created by HuangYong on 2018/9/27.
 */

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.huangyong.downloadlib.model.Params;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.presenter.UpdateAppPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IupdateView;
import dev.baofeng.com.supermovie.utils.SharePreferencesUtil;
import dev.baofeng.com.supermovie.view.UpdateDialog;


/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @created 2018/9/27
 * @changeRecord [修改记录] <br/>
 * 2018/9/27 ：created
 */
public class SplashActivity extends AppCompatActivity implements IupdateView {

    private UpdateAppPresenter presenter;
    private RelativeLayout root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);
        root = findViewById(R.id.root);

        presenter = new UpdateAppPresenter(this,this);
        presenter.getAppUpdate(this);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        },3000);
    }

    @Override
    public void noUpdate(String url) {
        SharePreferencesUtil.setIntSharePreferences(SplashActivity.this,Params.HAVE_UPDATE,0);
    }

    @Override
    public void updateYes(AppUpdateInfo result) {
        Snackbar.make(root, "发现新版本，请到“我的”页面手动下载", Snackbar.LENGTH_LONG).show();
        SharePreferencesUtil.setIntSharePreferences(SplashActivity.this,Params.HAVE_UPDATE,1);
    }

}
