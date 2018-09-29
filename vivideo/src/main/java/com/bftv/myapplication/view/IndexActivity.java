package com.bftv.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.bftv.myapplication.MyAdapter;
import com.bftv.myapplication.R;
import com.bftv.myapplication.config.KeyParam;

/**
 * Created by Helloworld on 2018/7/21.
 */

public class IndexActivity extends AppCompatActivity {

    private GridView gridView;
    private String [] url ={
        "http://www.iqiyi.com/",
        "http://youku.com/",
        "https://v.qq.com/",
        "https://tv.sohu.com/",
        "http://www.56.com/",
        "http://v.163.com/",
        "http://www.le.com/",
        "http://www.tudou.com/",
        "https://www.mgtv.com/"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        gridView = findViewById(R.id.list_grid);

        MyAdapter adapter = new MyAdapter(null);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (TextUtils.isEmpty(url[i])){
                    Toast.makeText(IndexActivity.this, "内容准备中", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(IndexActivity.this, WebCrossActivity.class);
                    intent.putExtra(KeyParam.BASEURL,url[i]);
                    startActivity(intent);
                }
            }
        });
    }
}
