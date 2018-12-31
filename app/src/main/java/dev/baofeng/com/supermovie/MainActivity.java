package dev.baofeng.com.supermovie;

import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huangyong.downloadlib.model.Params;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.domain.AppUpdateInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.SharePresenter;
import dev.baofeng.com.supermovie.presenter.UpdateAppPresenter;
import dev.baofeng.com.supermovie.presenter.iview.IShare;
import dev.baofeng.com.supermovie.presenter.iview.IupdateView;
import dev.baofeng.com.supermovie.utils.SharePreferencesUtil;
import dev.baofeng.com.supermovie.view.BTFragment;
import dev.baofeng.com.supermovie.view.CenterFragment;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.HomeFragment;
import dev.baofeng.com.supermovie.view.SubjectFragment;
import dev.baofeng.com.supermovie.view.UpdateDialog;
import rx.Observable;
import rx.functions.Action1;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IupdateView, IShare {

    private static boolean TABLEFTSELECTED = true;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.main)
    TextView main;
    @BindView(R.id.bt_subject)
    TextView subject;
    @BindView(R.id.down)
    TextView down;
    @BindView(R.id.my)
    TextView my;
    private BTFragment downfragment;
    private HomeFragment homeFragment;
    private CenterFragment centerFragment;
    private SubjectFragment subjectFragment;
    private UpdateAppPresenter updateAppPresenter;
    private SharePresenter sharePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        getPermission();
    }


    public void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(WRITE_EXTERNAL_STORAGE)
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        if (updateAppPresenter != null) {
                            updateAppPresenter.getAppUpdate(this);
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "你没有授权读写文件权限，将无法下载影片", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void noUpdate(String url) {
        //Toast.makeText(this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
        SharePreferencesUtil.setIntSharePreferences(MainActivity.this, Params.HAVE_UPDATE, 0);
    }

    @Override
    public void updateYes(AppUpdateInfo result) {
        SharePreferencesUtil.setIntSharePreferences(MainActivity.this, Params.HAVE_UPDATE, 1);
        UpdateDialog dialog = new UpdateDialog(this, result);
        dialog.show();
    }

    @Override
    public void loadData(RecentUpdate data) {

    }

    @Override
    public void loadFail(String e) {

    }


    public interface OnPageChanged{
        void clicked();
    }
    private void initView() {
        main.setOnClickListener(this);
        down.setOnClickListener(this);
        my.setOnClickListener(this);
        subject.setOnClickListener(this);
        main.setSelected(true);
        downfragment = BTFragment.getInstance();
        homeFragment = HomeFragment.getInstance();
        centerFragment = CenterFragment.getInstance();
        subjectFragment = SubjectFragment.getInstance();
        homeFragment.setOnPageChangeListener(() -> toggleFrag(4));
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, downfragment);
        fragmentTransaction.add(R.id.content, homeFragment);
        fragmentTransaction.add(R.id.content, centerFragment);
        fragmentTransaction.add(R.id.content, subjectFragment);
        fragmentTransaction.hide(downfragment);
        fragmentTransaction.hide(centerFragment);
        fragmentTransaction.hide(subjectFragment);
        fragmentTransaction.show(homeFragment);
        //下载中心的fragment

        fragmentTransaction.commitAllowingStateLoss();

        updateAppPresenter = new UpdateAppPresenter(this, this);
        sharePresenter = new SharePresenter(this, this);
        initExraData();
    }

    /**
     * 如果来自分享跳转，检查参数请求数据并直接跳转
     */
    private void initExraData() {
        String extra = getIntent().getStringExtra(GlobalMsg.KEY_MV_ID);
        if (!TextUtils.isEmpty(extra)) {
            sharePresenter.getMovieForShare(extra);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main:
                toggleFrag(1);
                break;
            case R.id.down:
                toggleFrag(2);
                break;
            case R.id.my:
                toggleFrag(3);
                break;
            case R.id.bt_subject:
                toggleFrag(4);
                break;
        }
    }


    private void toggleFrag(int i) {
        switch (i) {
            case 1:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(homeFragment);
                fragmentTransaction.hide(centerFragment);
                fragmentTransaction.hide(downfragment);
                fragmentTransaction.hide(subjectFragment);
                fragmentTransaction.commit();
                main.setSelected(true);
                down.setSelected(false);
                my.setSelected(false);
                subject.setSelected(false);
                break;
            case 2:
                FragmentTransaction fragmentTran2 = getSupportFragmentManager().beginTransaction();
                fragmentTran2.show(downfragment);
                fragmentTran2.hide(centerFragment);
                fragmentTran2.hide(homeFragment);
                fragmentTran2.hide(subjectFragment);
                fragmentTran2.commit();
                main.setSelected(false);
                down.setSelected(true);
                my.setSelected(false);
                subject.setSelected(false);
                break;
            case 3:
                FragmentTransaction fragmentTran3 = getSupportFragmentManager().beginTransaction();
                fragmentTran3.show(centerFragment);
                fragmentTran3.hide(homeFragment);
                fragmentTran3.hide(downfragment);
                fragmentTran3.hide(subjectFragment);
                fragmentTran3.commit();
                main.setSelected(false);
                down.setSelected(false);
                subject.setSelected(false);
                my.setSelected(true);
                break;
            case 4:
                FragmentTransaction fragmentTran4 = getSupportFragmentManager().beginTransaction();
                fragmentTran4.show(subjectFragment);
                fragmentTran4.hide(downfragment);
                fragmentTran4.hide(centerFragment);
                fragmentTran4.hide(homeFragment);
                fragmentTran4.commit();
                main.setSelected(false);
                down.setSelected(false);
                my.setSelected(false);
                subject.setSelected(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
