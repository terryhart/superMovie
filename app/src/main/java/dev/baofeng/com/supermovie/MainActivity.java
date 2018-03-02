package dev.baofeng.com.supermovie;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.fragment.TaskLefFragment;
import dev.baofeng.com.supermovie.fragment.TaskRightFragment;
import dev.baofeng.com.supermovie.view.BTFragment;
import dev.baofeng.com.supermovie.view.CenterFragment;
import dev.baofeng.com.supermovie.view.DownloadService;
import dev.baofeng.com.supermovie.view.GlobalMsg;
import dev.baofeng.com.supermovie.view.HomeFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static boolean TABLEFTSELECTED = true;
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.main)
    TextView main;
    @BindView(R.id.down)
    TextView down;
    @BindView(R.id.my)
    TextView my;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.task_tab_left)
    TextView taskTabLeft;
    @BindView(R.id.task_tab_right)
    TextView taskTabRight;
    @BindView(R.id.downTaskcontent)
    FrameLayout downcontent;
    private BTFragment downfragment;
    private HomeFragment homeFragment;
    private CenterFragment centerFragment;
    private DownloadService downService;
    private ServiceConnection conn;
    private TaskLefFragment lefFragment;
    private TaskRightFragment rightFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initPermission();
        initService();
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
    /**
     * 初始化下载线程
     */
    private void initService() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                downService = ((DownloadService.LocalBinder)service).getService();
                GlobalMsg.service = downService;
                Log.d("服务开启成功","服务开了");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        Intent intent = new Intent(getApplicationContext(),DownloadService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        main.setOnClickListener(this);
        down.setOnClickListener(this);
        my.setOnClickListener(this);
        taskTabLeft.setOnClickListener(this);
        taskTabRight.setOnClickListener(this);
        main.setSelected(true);
        downfragment = BTFragment.getInstance();
        homeFragment = HomeFragment.getInstance();
        centerFragment = CenterFragment.getInstance();
        lefFragment = TaskLefFragment.getInstance();
        rightFragment = TaskRightFragment.getInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, downfragment);
        fragmentTransaction.add(R.id.content, homeFragment);
        fragmentTransaction.add(R.id.content, centerFragment);
        fragmentTransaction.hide(downfragment);
        fragmentTransaction.hide(centerFragment);
        fragmentTransaction.show(homeFragment);
        //下载中心的fragment
        fragmentTransaction.add(R.id.downTaskcontent, lefFragment);
        fragmentTransaction.add(R.id.downTaskcontent, rightFragment);
        fragmentTransaction.hide(rightFragment);

        fragmentTransaction.commitAllowingStateLoss();


        centerFragment.setOnDownPageListener(() -> {
            drawer.openDrawer(GravityCompat.END);
            toggle();

        });
        homeFragment.setOnDownPageListener(() -> {
            drawer.openDrawer(GravityCompat.END);
            toggle();
        });
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
            case R.id.task_tab_left:
                TABLEFTSELECTED = true;
                toggle();
                break;
            case R.id.task_tab_right:
                TABLEFTSELECTED = false;
                toggle();
                break;
        }
    }

    private void toggle() {
        if (TABLEFTSELECTED) {
            taskTabLeft.setSelected(true);
            taskTabRight.setSelected(false);
            FragmentTransaction fragmentTran = getSupportFragmentManager().beginTransaction();
            fragmentTran.hide(rightFragment);
            fragmentTran.show(lefFragment);
            fragmentTran.commit();

        } else {
            taskTabLeft.setSelected(false);
            taskTabRight.setSelected(true);
            FragmentTransaction fragmentTran = getSupportFragmentManager().beginTransaction();
            fragmentTran.hide(lefFragment);
            fragmentTran.show(rightFragment);
            fragmentTran.commit();
        }

    }

    private void toggleFrag(int i) {
        switch (i) {
            case 1:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(homeFragment);
                fragmentTransaction.hide(centerFragment);
                fragmentTransaction.hide(downfragment);
                fragmentTransaction.commit();
                main.setSelected(true);
                down.setSelected(false);
                my.setSelected(false);
                break;
            case 2:
                FragmentTransaction fragmentTran2 = getSupportFragmentManager().beginTransaction();
                fragmentTran2.show(downfragment);
                fragmentTran2.hide(centerFragment);
                fragmentTran2.hide(homeFragment);
                fragmentTran2.commit();
                main.setSelected(false);
                down.setSelected(true);
                my.setSelected(false);
                break;
            case 3:
                FragmentTransaction fragmentTran3 = getSupportFragmentManager().beginTransaction();
                fragmentTran3.show(centerFragment);
                fragmentTran3.hide(homeFragment);
                fragmentTran3.hide(downfragment);
                fragmentTran3.commit();
                main.setSelected(false);
                down.setSelected(false);
                my.setSelected(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
