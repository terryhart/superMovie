package dev.baofeng.com.supermovie;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.view.BTFragment;
import dev.baofeng.com.supermovie.view.CenterFragment;
import dev.baofeng.com.supermovie.view.HomeFragment;
import dev.baofeng.com.supermovie.view.SubjectFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initPermission();
    }
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE,  Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

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
        homeFragment.setOnPageChangeListener(new OnPageChanged() {
            @Override
            public void clicked() {
                toggleFrag(4);
            }
        });
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
