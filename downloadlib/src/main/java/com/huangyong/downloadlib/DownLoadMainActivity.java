package com.huangyong.downloadlib;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;

import com.huangyong.downloadlib.adapter.DownTaskAdapter;
import com.huangyong.downloadlib.adapter.TaskFragmentPagerAdapter;
import com.huangyong.downloadlib.fragment.DownloadedTaskFragment;
import com.huangyong.downloadlib.fragment.DownloadingTaskFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DownLoadMainActivity extends AppCompatActivity {


    private ViewPager downTask;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dowload_main);

        downTask = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);


        List listfragment=new ArrayList<Fragment>();

        Fragment f1 = new DownloadedTaskFragment();
        Fragment f2 = new DownloadingTaskFragment();

        listfragment.add(f1);
        listfragment.add(f2);

        FragmentManager fm=getSupportFragmentManager();
        TaskFragmentPagerAdapter adapter=new TaskFragmentPagerAdapter(fm, listfragment);

        downTask.setAdapter(adapter);
        downTask.setCurrentItem(0);

        tabLayout.setupWithViewPager(downTask);
    }
}
