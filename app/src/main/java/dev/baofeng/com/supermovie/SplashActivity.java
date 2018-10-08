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



import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.presenter.UpdateAppPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IupdateView;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_layout);

        presenter = new UpdateAppPresenter(this,this);
        presenter.getAppUpdate(this);
        int versioncode = UpdateAppPresenter.getVersionCode(this, "dev.baofeng.com.supermovie");
        Log.e("versioncode",versioncode+"");


    }

    @Override
    public void noUpdate() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    @Override
    public void updateYes(AppUpdateInfo result) {
        UpdateDialog dialog = new UpdateDialog(SplashActivity.this,result);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }
}
