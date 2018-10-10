package dev.baofeng.com.supermovie.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.model.Params;

import java.util.ArrayList;
import java.util.List;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.DetailAdapter;
import dev.baofeng.com.supermovie.adapter.DownListAdapter;
import dev.baofeng.com.supermovie.domain.DetailInfo;

/**
 *  intent.putExtra(GlobalMsg.KEY_POST_IMG, finalImgUrl);
 intent.putExtra(GlobalMsg.KEY_DOWN_URL,datas.getData().get(position).getDownLoadUrl());
 intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE,datas.getData().get(position).getDownLoadName());
 intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,datas.getData().get(position).getMvdesc());
 */
public class MovieDetailActivity extends AppCompatActivity implements OnItemClickListenr {

    private BlurImageView poster;
//    private TextView mvdesc;
    private String title;
    private String downUrl;
    private String posterUrl;
    private String mvdescTx;
    private TextView sdesc;
    private RecyclerView recyclerView;
    private String posterImagUrl;
    private String imgScreenShot;
    private Toolbar toolbar;
    private AppBarLayout detail_app_bar;
    private ImageView backup;
    private TextView titleView;
    private DetailAdapter detailAdapter;
    private String downItemTitle;
    private String[] downItemList;
    private LinearLayoutManager layoutManager;
    private String[] items;
    private ImageView favor;
    private boolean hasFavor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        initData();
        initView();

    }

    private void initData() {
        layoutManager = new LinearLayoutManager(this);

        Intent intent = getIntent();
        posterUrl = intent.getStringExtra(GlobalMsg.KEY_POST_IMG);
        downItemTitle = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE);
        Log.e("kdkdkdd",downItemTitle);
        downItemList = downItemTitle.split(",");


        if (posterUrl.contains(",")){
           String[] imgArr =  posterUrl.split(",");
            imgScreenShot = imgArr[1];
        }

        posterImagUrl = posterUrl.substring(0,posterUrl.indexOf("jpg")+3);
        downUrl = intent.getStringExtra(GlobalMsg.KEY_DOWN_URL);
        Log.e("downurllist",posterUrl);
        title = intent.getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        mvdescTx = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DETAIL);
    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        detail_app_bar = findViewById(R.id.app_bar);
        favor = findViewById(R.id.favor);
        backup = findViewById(R.id.backup);
        detail_app_bar.addOnOffsetChangedListener(new MyOffsetChangedListener());
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavor();
            }
        });
        poster = findViewById(R.id.poster);
//        mvdesc = findViewById(R.id.mvdesc);
        recyclerView = findViewById(R.id.rv_detail);
        titleView = findViewById(R.id.toolbarTitle);
        titleView.setText(title);
        recyclerView.setLayoutManager(layoutManager);


        sdesc = findViewById(R.id.sdesc);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(title);

        String[] splitArr = mvdescTx.split("◎");
        StringBuffer buffer = new StringBuffer();
        ArrayList<String> listDesc=new ArrayList<>();
        DetailInfo info = new DetailInfo();


        if (splitArr.length>5){
            for (int i = 1; i < 5; i++) {
                if (splitArr[i].contains("//")||splitArr[i].contains("类") ){
                    continue;
                }
                buffer.append(splitArr[i]);
                listDesc.add(splitArr[i]);
            }

            StringBuffer descBuffer =new StringBuffer();
            for (int i = 5; i <splitArr.length ; i++) {
                if (splitArr[i].contains("//")||splitArr[i].contains("类") ){
                    continue;
                }
                if (splitArr[i].contains("简")){
                    descBuffer.append("\n◎"+splitArr[i]);
                }else {
                    descBuffer.append("◎"+splitArr[i]);
                }
            }
            //详情介绍
            info.setMvDesc(descBuffer.toString());
        }else {
            //详情介绍
            StringBuffer shortDesc = new StringBuffer();
            for (int i = 0; i < splitArr.length; i++) {

                shortDesc.append(splitArr[i]);
            }
            info.setMvDesc(shortDesc.toString());
        }


        //海报右边的短简介
        sdesc.setText(buffer.toString());
        //详情里的长简介

        //
        String[] downUrl = this.downUrl.split(",");


        ArrayList url = new ArrayList();
        for (int i = 0; i < downUrl.length; i++) {
            url.add(downUrl[i]);
        }
        //截屏
        info.setImgScreenShot(imgScreenShot);
        //下载地址
        info.setDownUrl(url);
        //下载页显示的海报
        info.setImgUrl(posterImagUrl);
        ArrayList<DetailInfo> list = new ArrayList<>();
        list.add(info);
        detailAdapter = new DetailAdapter(downItemList,list,this);
        DownListAdapter dialogAdapter = new DownListAdapter(downItemList,list,this);
        recyclerView.setAdapter(detailAdapter);

        DownLoadListDialog downLoadListDialog= new DownLoadListDialog(this,0,dialogAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intents = new Intent(MovieDetailActivity.this, DownLoadMainActivity.class);
//                startActivity(intents);

                downLoadListDialog.show();
            }
        });
        Glide.with(this).load(posterImagUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                poster.setImageBitmap(resource);
            }
        });

    }

    /**
     * 添加或取消收藏
     */
    private void toggleFavor() {
        if (hasFavor){
            favor.setBackgroundResource(R.drawable.fullscreen_favority_normal);
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
        }else {
            favor.setBackgroundResource(R.drawable.fullscreen_favority_press);
            Toast.makeText(this, "已添加收藏", Toast.LENGTH_SHORT).show();
        }
        hasFavor=!hasFavor;
    }

    public class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return items[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View views = LayoutInflater.from(MovieDetailActivity.this).inflate(R.layout.down_item,viewGroup,false);
            TextView tidown = views.findViewById(R.id.tv_down);
            tidown.setText(items[i]);
            return views;
        }
    }


    private class MyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            float progress = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
            if (progress >= 0.8) {
                toolbar.setVisibility(View.VISIBLE);
                titleView.setAlpha(progress);
                backup.setAlpha(progress);
            } else {
                toolbar.setVisibility(View.VISIBLE);
                titleView.setAlpha(0.0f);
                backup.setAlpha(0.4f);
            }
        }
    }

    @Override
    public void clicked(String url, String imgUrl) {
        Toast.makeText(this, "下载任务已添加", Toast.LENGTH_SHORT).show();
        Log.e("dowurllsit",url+"\n"+imgUrl);
        TaskLibHelper.addNewTask(url, Params.DEFAULT_PATH,imgUrl,getApplicationContext());
    }
}
