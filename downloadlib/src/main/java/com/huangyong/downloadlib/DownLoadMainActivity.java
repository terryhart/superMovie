package com.huangyong.downloadlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.huangyong.downloadlib.adapter.TaskFragmentPagerAdapter;
import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.fragment.DownloadedTaskFragment;
import com.huangyong.downloadlib.fragment.DownloadingTaskFragment;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class DownLoadMainActivity extends AppCompatActivity {


    private CustomViewPager downTask;
    private TabLayout tabLayout;
    private boolean viewVisible =false;
    private DownloadingTaskFragment ingList;
    private DownloadedTaskFragment doneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dowload_main);


        downTask = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);


        List listfragment=new ArrayList<Fragment>();

        ingList =DownloadingTaskFragment.getInstance();
        doneList = DownloadedTaskFragment.getInstance();

        listfragment.add(ingList);
        listfragment.add(doneList);

        FragmentManager fm=getSupportFragmentManager();
        TaskFragmentPagerAdapter adapter=new TaskFragmentPagerAdapter(fm, listfragment);

        downTask.setAdapter(adapter);
        downTask.setScanScroll(true);
        downTask.setCurrentItem(0);

        tabLayout.setupWithViewPager(downTask);

        initData();
    }

    private void initData() {
        //下载中列表
        List<DowningTaskInfo> taskInfos = TaskDao.getInstance(getApplicationContext()).queryAll();
        if (taskInfos!=null&&taskInfos.size()>0){
            Log.e("downtaskinit","本地数据库有数据"+taskInfos.size());

            ingList.updateTaskDatas(taskInfos,DownLoadMainActivity.this);
        }else {
            Log.e("downtaskinit","本地数据库为空");
        }
        //下载完成任务列表
        List<DoneTaskInfo> doneTaskInfos = TaskedDao.getInstance(getApplicationContext()).queryAll();
        if (doneTaskInfos.size()>0){
            Log.e("downtaskinit","本地数据库有数据"+doneTaskInfos.size());
            doneList.updateTaskData(doneTaskInfos);
        }else {
            Log.e("downtaskinit","本地数据库为空");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.viewVisible = true;
    }

    @Override
    protected void onDestroy() {
        this.viewVisible = false;
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
