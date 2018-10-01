package dev.baofeng.com.supermovie;
/**
 * Created by HuangYong on 2018/9/27.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.vector.update_app.UpdateAppManager;

import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.http.UpdateAppHttpUtil;
import dev.baofeng.com.supermovie.http.UrlConfig;
import dev.baofeng.com.supermovie.presenter.UpdateAppPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IupdateView;
import dev.baofeng.com.supermovie.view.BottomStyleDialog;


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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);

        presenter = new UpdateAppPresenter(this,this);
        presenter.getAppUpdate(this);
        int versioncode = UpdateAppPresenter.getVersionCode(this, "dev.baofeng.com.supermovie");
        Log.e("versioncode",versioncode+"");

      /*  new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl(UrlConfig.BASE_URL+"ygcms/app/update.json")
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil(SplashActivity.this))
                .build()
                .update();*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }

    @Override
    public void noUpdate() {

    }

    @Override
    public void updateYes(AppUpdateInfo result) {

      /*  BottomStyleDialog dialog = new BottomStyleDialog(SplashActivity.this,result);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();*/
    }
}
