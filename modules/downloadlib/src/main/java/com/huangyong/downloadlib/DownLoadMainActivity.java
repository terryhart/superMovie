package com.huangyong.downloadlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ess.filepicker.FilePicker;
import com.ess.filepicker.model.EssFile;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.huangyong.com.common.SharePreferencesUtil;

public class DownLoadMainActivity extends AppCompatActivity implements View.OnClickListener, ITask {


    private static int REQUEST_CODE_CHOOSE = 1001;
    private CustomViewPager downTask;
    private TabLayout tabLayout;
    private boolean viewVisible = false;
    private DownloadingTaskFragment ingList;
    private DownloadedTaskFragment doneList;
    private TextView speedTitle;
    private TextView memerysize;
    private ImageView exit;
    private DownLoadPresenter downLoadPresenter;
    private Toolbar toolbar;
    private BottomSheetDialog bottomSheetDialog;
    private TextView tv_file_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dowload_main);

        downTask = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        speedTitle = findViewById(R.id.speedTitle);
        memerysize = findViewById(R.id.tv_memerysize);
        tv_file_path = findViewById(R.id.tv_file_path);
        toolbar = findViewById(R.id.down_toolbar);

        exit = findViewById(R.id.exit);
        exit.setOnClickListener(this);

        List listfragment = new ArrayList<Fragment>();

        ingList = DownloadingTaskFragment.getInstance();
        doneList = DownloadedTaskFragment.getInstance();

        listfragment.add(ingList);
        listfragment.add(doneList);

        FragmentManager fm = getSupportFragmentManager();
        TaskFragmentPagerAdapter adapter = new TaskFragmentPagerAdapter(fm, listfragment);

        downTask.setAdapter(adapter);
        downTask.setScanScroll(true);
        downTask.setCurrentItem(0);

        tabLayout.setupWithViewPager(downTask);

        downLoadPresenter = new DownLoadPresenter(this, this);
        downLoadPresenter.initDownloadLiveData(this);

        ingList.setPresenter(downLoadPresenter);
        doneList.setPresenter(downLoadPresenter);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initReceiver();

        SharePreferencesUtil.getStringSharePreferences(this, Params.LOCAL_PATH_KEY, Params.DEFAULT_PATH);
        //底部弹窗
        bottomSheetDialog = new BottomSheetDialog(this);

        tv_file_path.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.btn_sure) {
            showBottomSheetDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initReceiver() {
        String savePath = null;
        try {
            savePath = FileUtils.isExistDir(Params.getPath());
            Uri contentUri = Uri.fromFile(new File(savePath));
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, contentUri);
            sendBroadcast(mediaScanIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Params.UPDATE_PROGERSS);
        intentFilter.addAction(Params.TASK_COMMPLETE);
        intentFilter.addAction(Params.UPDATE_MEMERY_SIZE);
        registerReceiver(taskReceiver, intentFilter);
    }

    private void initData() {
        memerysize.setText("已下载文件" + FileUtils.getCacheSize() + "，机身剩余可用" + FileUtils.getSpaceSize()[0]);
        tv_file_path.setText("当前文件下载目录："+SharePreferencesUtil.getStringSharePreferences(this,Params.LOCAL_PATH_KEY,Params.DEFAULT_PATH));
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
            if (intent.getAction().equals(Params.UPDATE_PROGERSS)) {
                DowningTaskDao taskDao = AppDatabaseManager.getInstance(getApplicationContext()).donwingDao();
                List<DowningTaskInfo> downingTaskInfos = taskDao.getAll();
                if (downingTaskInfos != null && downingTaskInfos.size() > 0) {
                    speedTitle.setText("正在下载" + downingTaskInfos.size() + "个文件");
                } else {
                    speedTitle.setText("暂无下载任务");
                }
            }
            if (intent.getAction().equals(Params.TASK_COMMPLETE)) {
                initData();
            }
            if (intent.getAction().equals(Params.UPDATE_MEMERY_SIZE)) {
                initData();
            }
        }
    };

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.exit) {
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

    private void showBottomSheetDialog() {
        // Set up BottomSheetDialog

        View view = LayoutInflater.from(this).inflate(R.layout.setting_layout, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.show();
        view.findViewById(R.id.set_path).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePicker.from(DownLoadMainActivity.this)
                        .chooseForFloder()
                        .isSingle()
                        .setMaxCount(0)
                        //.setFileTypes("TORRENT")
                        .requestCode(REQUEST_CODE_CHOOSE)
                        .start();
            }
        });
        TextView pathTv = view.findViewById(R.id.current_path);
        pathTv.setText("当前下载文件路径：" + SharePreferencesUtil.getStringSharePreferences(DownLoadMainActivity.this, Params.LOCAL_PATH_KEY, Params.DEFAULT_PATH));
        view.findViewById(R.id.set_download_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownLoadMainActivity.this, "正在开发，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.set_allow_no_wifi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownLoadMainActivity.this, "正在开发，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<EssFile> fileList = data.getParcelableArrayListExtra(com.ess.filepicker.util.Const.EXTRA_RESULT_SELECTION);
            if (!TextUtils.isEmpty(fileList.get(0).getAbsolutePath())) {
                SharePreferencesUtil.setStringSharePreferences(this, Params.LOCAL_PATH_KEY, fileList.get(0).getAbsolutePath());
                Toast.makeText(DownLoadMainActivity.this, "文件路径已更新", Toast.LENGTH_SHORT).show();
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    TextView currentPath = bottomSheetDialog.findViewById(R.id.current_path);
                    currentPath.setText("当前下载文件路径："+fileList.get(0).getAbsolutePath());
                }
                initData();
            } else {
                Toast.makeText(DownLoadMainActivity.this, "获取文件路径失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
