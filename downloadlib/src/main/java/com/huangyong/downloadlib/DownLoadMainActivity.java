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
import android.widget.TextView;

import com.huangyong.downloadlib.adapter.TaskFragmentPagerAdapter;
import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DoneTaskInfo;
import com.huangyong.downloadlib.domain.DowningTaskInfo;
import com.huangyong.downloadlib.fragment.DownloadedTaskFragment;
import com.huangyong.downloadlib.fragment.DownloadingTaskFragment;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.FileUtils;
import com.huangyong.downloadlib.utils.NetSpeedUtil;
import com.huangyong.downloadlib.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class DownLoadMainActivity extends AppCompatActivity {


    private CustomViewPager downTask;
    private TabLayout tabLayout;
    private boolean viewVisible =false;
    private DownloadingTaskFragment ingList;
    private DownloadedTaskFragment doneList;
    private TextView speedTitle;
    private TextView memerysize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dowload_main);


        downTask = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        speedTitle = findViewById(R.id.speedTitle);
        memerysize = findViewById(R.id.tv_memerysize);


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
        initReceiver();
    }
    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Params.UPDATE_PROGERSS);
        intentFilter.addAction(Params.TASK_COMMPLETE);
        intentFilter.addAction(Params.UPDATE_MEMERY_SIZE);
        registerReceiver(taskReceiver,intentFilter);
    }
    private void initData() {
        memerysize.setText("已下载文件"+FileUtils.getCacheSize()+"，机身剩余可用"+ FileUtils.getSpaceSize()[0]);

        //下载中列表
        List<DowningTaskInfo> taskInfos = TaskDao.getInstance(getApplicationContext()).queryAll();
        if (taskInfos!=null&&taskInfos.size()>0){
            ingList.updateTaskDatas(taskInfos,DownLoadMainActivity.this);
        }
        //下载完成任务列表
        List<DoneTaskInfo> doneTaskInfos = TaskedDao.getInstance(getApplicationContext()).queryAll();
        if (doneTaskInfos.size()>0){
            doneList.updateTaskData(doneTaskInfos);
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
        unregisterReceiver(taskReceiver);
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 下载中的任务完成后，会从下载中移除，并添加到已完成的列表，已完成因为没有下载进度，所以不会实时更新，只
     */
    BroadcastReceiver taskReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Params.UPDATE_PROGERSS)){
                TaskDao taskDao = TaskDao.getInstance(getApplicationContext());
                List<DowningTaskInfo> downingTaskInfos = taskDao.queryAll();
                if (downingTaskInfos!=null&&downingTaskInfos.size()>0){
                    speedTitle.setText("正在下载"+downingTaskInfos.size()+"个文件");
                }else {
                    speedTitle.setText("暂无下载任务");
                }
            }
            if (intent.getAction().equals(Params.TASK_COMMPLETE)){
                initData();
            }
            if (intent.getAction().equals(Params.UPDATE_MEMERY_SIZE)){
                initData();
            }
        }
    };
}
