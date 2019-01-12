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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huangyong.downloadlib.adapter.TaskFragmentPagerAdapter;
import com.huangyong.downloadlib.fragment.DownloadedTaskFragment;
import com.huangyong.downloadlib.fragment.DownloadingTaskFragment;
import com.huangyong.downloadlib.model.ITask;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.presenter.DownLoadPresenter;
import com.huangyong.downloadlib.room.AppDatabaseManager;
import com.huangyong.downloadlib.room.DowningTaskDao;
import com.huangyong.downloadlib.room.data.DoneTaskInfo;
import com.huangyong.downloadlib.room.data.DowningTaskInfo;
import com.huangyong.downloadlib.utils.FileUtils;
import com.huangyong.downloadlib.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class DownLoadMainActivity extends AppCompatActivity implements View.OnClickListener, ITask {


    private CustomViewPager downTask;
    private TabLayout tabLayout;
    private boolean viewVisible =false;
    private DownloadingTaskFragment ingList;
    private DownloadedTaskFragment doneList;
    private TextView speedTitle;
    private TextView memerysize;
    private ImageView exit;
    private DownLoadPresenter downLoadPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dowload_main);

        downTask = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        speedTitle = findViewById(R.id.speedTitle);
        memerysize = findViewById(R.id.tv_memerysize);
        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);

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

        downLoadPresenter = new DownLoadPresenter(this, this);
        downLoadPresenter.initDownloadLiveData(this);

        ingList.setPresenter(downLoadPresenter);
        doneList.setPresenter(downLoadPresenter);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.viewVisible = true;
        initData();
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
                DowningTaskDao taskDao = AppDatabaseManager.getInstance(getApplicationContext()).donwingDao();
                List<DowningTaskInfo> downingTaskInfos = taskDao.getAll();
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

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.exit){
            finish();
        }
    }

    @Override
    public void repeatAdd(String s) {

    }


    @Override
    public void updateIngTask(List<DowningTaskInfo> taskInfo) {
        ingList.FlushData(taskInfo);
    }

    @Override
    public void updateDoneTask(List<DoneTaskInfo> taskInfo) {
        doneList.FlushData(taskInfo);
    }
}
