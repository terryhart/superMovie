package com.huangyong.downloadlib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.huangyong.downloadlib.adapter.TaskFragmentPagerAdapter;
import com.huangyong.downloadlib.fragment.DownloadedTaskFragment;
import com.huangyong.downloadlib.fragment.DownloadingTaskFragment;

import java.util.ArrayList;
import java.util.List;


public class MTabActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_layout);
        viewPager = findViewById(R.id.viewpager);
        List listfragment=new ArrayList<Fragment>();

        Fragment f1 = new DownloadedTaskFragment();
        Fragment f2 = new DownloadingTaskFragment();

        listfragment.add(f1);
        listfragment.add(f2);

        FragmentManager fm=getSupportFragmentManager();
        TaskFragmentPagerAdapter adapter=new TaskFragmentPagerAdapter(fm, listfragment);

        viewPager.setAdapter(adapter);


    }
}
