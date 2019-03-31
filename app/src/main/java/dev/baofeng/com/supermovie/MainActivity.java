package dev.baofeng.com.supermovie;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import app.huangyong.com.common.SharePreferencesUtil;
import dev.baofeng.com.supermovie.view.BTFragment;
import dev.baofeng.com.supermovie.view.CenterFragment;
import dev.baofeng.com.supermovie.view.SubjectFragment;
import dev.baofeng.com.supermovie.view.online.OnlineRootFragment;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IupdateView, IShare {

    private static boolean TABLEFTSELECTED = true;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.main)
    TextView main;
    @BindView(R.id.bt_subject)
    TextView subject;
    @BindView(R.id.online)
    TextView down;
    @BindView(R.id.my)
    TextView my;
    private BTFragment homefragment;
    private CenterFragment centerFragment;
    private SubjectFragment subjectFragment;
    private UpdateAppPresenter updateAppPresenter;
    private SharePresenter sharePresenter;
    private OnlineRootFragment onlineFilmRootFragment;

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
        SharePreferencesUtil.setIntSharePreferences(MainActivity.this, Params.HAVE_UPDATE, 0);
    }

    @Override
    public void updateYes(AppUpdateInfo result) {
        SharePreferencesUtil.setIntSharePreferences(MainActivity.this, Params.HAVE_UPDATE, 1);



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
        homefragment = BTFragment.getInstance();
        centerFragment = CenterFragment.getInstance();
        subjectFragment = SubjectFragment.getInstance();
        onlineFilmRootFragment = OnlineRootFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, homefragment);
        fragmentTransaction.add(R.id.content, centerFragment);
        fragmentTransaction.add(R.id.content, subjectFragment);
        fragmentTransaction.add(R.id.content, onlineFilmRootFragment);
        fragmentTransaction.hide(centerFragment);
        fragmentTransaction.hide(subjectFragment);
        fragmentTransaction.hide(onlineFilmRootFragment);
        fragmentTransaction.show(homefragment);
        //下载中心的fragment

        fragmentTransaction.commitAllowingStateLoss();

        updateAppPresenter = new UpdateAppPresenter(this, this);
        sharePresenter = new SharePresenter(this, this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main:
                toggleFrag(1);
                break;
            case R.id.online:
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
                fragmentTransaction.show(homefragment);
                fragmentTransaction.hide(centerFragment);
                fragmentTransaction.hide(subjectFragment);
                fragmentTransaction.hide(onlineFilmRootFragment);
                fragmentTransaction.commit();
                main.setSelected(true);
                down.setSelected(false);
                my.setSelected(false);
                subject.setSelected(false);
                break;
            case 2:
                FragmentTransaction fragmentTran2 = getSupportFragmentManager().beginTransaction();
                fragmentTran2.show(onlineFilmRootFragment);
                fragmentTran2.hide(centerFragment);
                fragmentTran2.hide(homefragment);
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
                fragmentTran3.hide(homefragment);
                fragmentTran3.hide(subjectFragment);
                fragmentTran3.hide(onlineFilmRootFragment);
                fragmentTran3.commit();
                main.setSelected(false);
                down.setSelected(false);
                subject.setSelected(false);
                my.setSelected(true);
                break;
            case 4:
                FragmentTransaction fragmentTran4 = getSupportFragmentManager().beginTransaction();
                fragmentTran4.show(subjectFragment);
                fragmentTran4.hide(homefragment);
                fragmentTran4.hide(centerFragment);
                fragmentTran4.hide(onlineFilmRootFragment);
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
