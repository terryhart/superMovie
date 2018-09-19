package com.huangyong.downloadlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huangyong.downloadlib.adapter.DownTaskAdapter;

public class DownLoadDetail extends AppCompatActivity {


    private RecyclerView downTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dowload_main);

        downTask = findViewById(R.id.rv_down_task);

        DownTaskAdapter adapter = new DownTaskAdapter();

        downTask.setLayoutManager(new LinearLayoutManager(this));

        downTask.setAdapter(adapter);
    }
}
