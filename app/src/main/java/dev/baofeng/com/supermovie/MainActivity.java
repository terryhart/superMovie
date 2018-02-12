package dev.baofeng.com.supermovie;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.view.CenterFragment;
import dev.baofeng.com.supermovie.view.DownloadService;
import dev.baofeng.com.supermovie.view.HomeFragment;
import dev.baofeng.com.supermovie.view.BTFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.main)
    TextView main;
    @BindView(R.id.down)
    TextView down;
    @BindView(R.id.my)
    TextView my;
    private BTFragment downfragment;
    private HomeFragment homeFragment;
    private CenterFragment centerFragment;
    private DownloadService downService;
    private ServiceConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initService();
    }

    /**
     * 初始化下载线程
     */
    private void initService() {
        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                downService = ((DownloadService.LocalBinder)service).getService();
                downService.startLoop();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        Intent intent = new Intent(this,DownloadService.class);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
    }

    private void initView() {
        main.setOnClickListener(this);
        down.setOnClickListener(this);
        my.setOnClickListener(this);
        main.setSelected(true);
        downfragment = BTFragment.getInstance();
        homeFragment = HomeFragment.getInstance();
        centerFragment = CenterFragment.getInstance();
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content, downfragment);
        fragmentTransaction.add(R.id.content, homeFragment);
        fragmentTransaction.add(R.id.content, centerFragment);
        fragmentTransaction.hide(downfragment);
        fragmentTransaction.hide(centerFragment);
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main:
                toggleFrag(1);
                break;
            case R.id.down:
                toggleFrag(2);
                break;
            case R.id.my:
                toggleFrag(3);
                break;
        }
    }

    private void toggleFrag(int i) {
        switch (i){
            case 1:
                FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
                fragmentTransaction.show(homeFragment);
                fragmentTransaction.hide(centerFragment);
                fragmentTransaction.hide(downfragment);
                fragmentTransaction.commit();
                main.setSelected(true);
                down.setSelected(false);
                my.setSelected(false);
                break;
            case 2:
                FragmentTransaction fragmentTran2 =getSupportFragmentManager().beginTransaction();
                fragmentTran2.show(downfragment);
                fragmentTran2.hide(centerFragment);
                fragmentTran2.hide(homeFragment);
                fragmentTran2.commit();
                main.setSelected(false);
                down.setSelected(true);
                my.setSelected(false);
                break;
            case 3:
                FragmentTransaction fragmentTran3 =getSupportFragmentManager().beginTransaction();
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
