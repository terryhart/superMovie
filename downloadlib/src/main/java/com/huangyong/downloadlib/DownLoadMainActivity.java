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
import com.huangyong.downloadlib.adapter.TaskFragmentPagerAdapter;
import com.huangyong.downloadlib.domain.DataList;
import com.huangyong.downloadlib.domain.DownTaskInfo;
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
                    //TODO 下载中列表数据，intent中包含taskId,url,进度，等等，封装成数据源，传递给adapter
                    DataList dataList = new DataList();

                    if (ingList!=null){
                        ingList.updateTaskData(dataList.getInfoList());
                    }

                    //TODO 已完成列表数据

                    if (doneList!=null){
                        doneList.updateTaskData(dataList.getInfoList());
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
}
