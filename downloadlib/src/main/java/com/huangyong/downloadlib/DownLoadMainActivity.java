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

import com.google.gson.Gson;
import com.huangyong.downloadlib.adapter.TaskFragmentPagerAdapter;
import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.db.TaskedDao;
import com.huangyong.downloadlib.domain.DataList;
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
    private DataList dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dowload_main);


        downTask = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);


        List listfragment=new ArrayList<Fragment>();

        ingList = new DownloadingTaskFragment();
        doneList = new DownloadedTaskFragment();

        listfragment.add(ingList);
        listfragment.add(doneList);

        FragmentManager fm=getSupportFragmentManager();
        TaskFragmentPagerAdapter adapter=new TaskFragmentPagerAdapter(fm, listfragment);

        downTask.setAdapter(adapter);
        downTask.setScanScroll(false);
        downTask.setCurrentItem(0);

        tabLayout.setupWithViewPager(downTask);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Params.TASK_START);
        registerReceiver(taskReceiver,intentFilter);

        initData();
    }

    private void initData() {
        //下载中列表
        List<DowningTaskInfo> taskInfos = TaskDao.getInstance(getApplicationContext()).queryAll();
        if (taskInfos.size()>0){
            Log.e("downtaskinit","本地数据库有数据"+taskInfos.size());

            ingList.updateTaskData(taskInfos);
        }else {
            Log.e("downtaskinit","本地数据库为空");
        }
        //下载完成任务列表
        List<DowningTaskInfo> doneTaskInfos = TaskDao.getInstance(getApplicationContext()).queryAll();
        if (doneTaskInfos.size()>0){
            Log.e("downtaskinit","本地数据库有数据"+doneTaskInfos.size());
            doneList.updateTaskData(doneTaskInfos);
        }else {
            Log.e("downtaskinit","本地数据库为空");
        }
    }

    /**
     * 下载中的任务完成后，会从下载中移除，并添加到已完成的列表，已完成因为没有下载进度，所以不会实时更新，只
     * 在数据变化时更新，所以已完成的列表在这个页面封装并传送
     */
    BroadcastReceiver taskReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Params.UPDATE_PROGERSS)){
                //TODO 若页面可见，更新数据，该广播每2秒一次，
                // TODO 只有页面可见时才更新列表。但是服务中的数据封装实时进行着。除非任务列表为空。本页只初始化时从数据库获取一次
                if (viewVisible){
                    //查询数据库所有数据
                    List<DowningTaskInfo> downingTaskInfos = TaskDao.getInstance(getApplicationContext()).queryAll();
                    if (downingTaskInfos!=null&&downingTaskInfos.size()>0){
                        if (ingList!=null){
                            ingList.updateTaskData(downingTaskInfos);
                        }
                    }

                    //TODO 已完成列表数据
                    List<DoneTaskInfo> doneTaskInfos = TaskedDao.getInstance(getApplicationContext()).queryAll();
                    if (doneTaskInfos!=null&&doneTaskInfos.size()>0){
                        if (doneList!=null){
                            doneList.updateTaskData(dataList.getDoneList());
                        }
                    }

                }
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        this.viewVisible = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.viewVisible = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData(dataList);
    }
    private void saveData(DataList dataList) {
        List<DowningTaskInfo> doneTaskInfos = TaskDao.getInstance(getApplicationContext()).queryAll();
        Log.e("downtaskinit","退出列表，保存数据到数据库"+dataList.getInfoList().size()+"---"+dataList.getDoneList().size());
    }
}
