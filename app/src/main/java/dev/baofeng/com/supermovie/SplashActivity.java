package dev.baofeng.com.supermovie;
/**
 * Created by HuangYong on 2018/9/27.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.huangyong.downloadlib.model.Params;


import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.SharePresenter;
import dev.baofeng.com.supermovie.presenter.UpdateAppPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IShare;
import dev.baofeng.com.supermovie.presenter.iview.IupdateView;
import dev.baofeng.com.supermovie.utils.SharePreferencesUtil;
import dev.baofeng.com.supermovie.view.GlobalMsg;


/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @created 2018/9/27
 * @changeRecord [修改记录] <br/>
 * 2018/9/27 ：created
 */
public class SplashActivity extends AppCompatActivity implements IupdateView, IShare {

    private UpdateAppPresenter presenter;
    private RelativeLayout root;
    private SharePresenter sharePresenter;
    private ImageView poster;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);
        root = findViewById(R.id.root);
        poster = findViewById(R.id.splash_poster);
        sharePresenter = new SharePresenter(this, this);
        presenter = new UpdateAppPresenter(this,this);
        presenter.getAppUpdate(this);
        initExraData();

    }

    /**
     * 如果来自分享跳转，检查参数请求数据并直接跳转
     */
    private void initExraData() {
        String extra = getIntent().getStringExtra(GlobalMsg.KEY_MV_ID);
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);

        if (!TextUtils.isEmpty(extra)) {
            sharePresenter.getMovieForShare(extra);

            finish();
        } else {
            new Handler().postDelayed(() -> {
                startActivity(intent);
                finish();
            }, 3000);
        }


    }

    @Override
    public void noUpdate(String url) {
        SharePreferencesUtil.setIntSharePreferences(SplashActivity.this,Params.HAVE_UPDATE,0);
    }

    @Override
    public void updateYes(AppUpdateInfo result) {
        //Snackbar.make(root, "发现新版本，请到“我的”页面手动下载", Snackbar.LENGTH_LONG).show();
        SharePreferencesUtil.setIntSharePreferences(SplashActivity.this,Params.HAVE_UPDATE,1);
    }

    @Override
    public void loadData(RecentUpdate data) {

    }

    @Override
    public void loadFail(String e) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}
