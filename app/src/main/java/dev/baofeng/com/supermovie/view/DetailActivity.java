package dev.baofeng.com.supermovie.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.DownAdapter;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieBean;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.domain.TaskInfo;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.BlurUtil;

/**
 * Created by huangyong on 2018/1/29.
 */

public class DetailActivity extends AppCompatActivity implements IMoview {

    @BindView(R.id.post_img)
    ImageView postImg;
    @BindView(R.id.tv_mv_mame)
    TextView tvMvMame;
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    @BindView(R.id.tv_statu)
    TextView tvStatu;
    @BindView(R.id.down_bg)
    ImageView downBg;
    private String downUrl;
    private String postImg1;
    private String pathurl;

    private String title;
    private GetRecpresenter getRecpresenter;
    private TaskInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_layout);
        ButterKnife.bind(this);
        try {
            iniData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void iniData() {
        downUrl = getIntent().getStringExtra(GlobalMsg.KEY_DOWN_URL);
        title = getIntent().getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        postImg1 = getIntent().getStringExtra(GlobalMsg.KEY_POST_IMG);
        tvMvMame.setText(title);
        getRecpresenter = new GetRecpresenter(this, this);
        getRecpresenter.getBtDetail(title);
        Glide.with(this).load(postImg1).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BlurUtil.setViewBg(4, 3, downBg, resource);
            }
        });
        Glide.with(this).load(postImg1).into(postImg);

        String[] split = downUrl.split(",");
        ArrayList<String> urlList = new ArrayList<>();
        for (int i = 0; i <split.length; i++) {
            Log.e("下载地址为：",split[i]+"\n");
            urlList.add(split[i]);
        }

        MovieBean bean = new MovieBean();
        bean.setDate(urlList);


        DownAdapter adapter = new DownAdapter(this, bean);

        //添加下载任务，这里不下载，都转移到个人中心的下载列表开始下载
        adapter.setOnItemClickListener(new DownAdapter.onItemClick() {
            @Override
            public void onItemclicks(String url) {
                Log.d("下载的地址",url);
                TaskInfo info = new TaskInfo();
                info.setProgress(0);
                info.setAction(GlobalMsg.ACTION);
                info.setName(title);
                info.setDownSize("0");
                info.setFileSize("0");
                info.setPath(url);
                info.setIsWaiting(1);//默认是等待状态

            }

            @Override
            public void onBaiduPanClick(String url) {
                Toast.makeText(DetailActivity.this, "即将打开浏览器，前往浏览器继续操作", Toast.LENGTH_SHORT).show();
            }
        });
        rvlist.setLayoutManager(new GridLayoutManager(this, 3));
        rvlist.setAdapter(adapter);

        info = new TaskInfo();
    }

    @Override
    public void loadData(RecentUpdate info) {

    }

    @Override
    public void loadData(MovieInfo info) {

    }

    @Override
    public void loadError(String msg) {

    }

    @Override
    public void loadMore(MovieInfo result) {

    }

    @Override
    public void loadMore(RecentUpdate result) {

    }

    @Override
    public void loadBtData(MovieInfo result) {

    }

    @Override
    public void loadDetail(BtInfo result) {

    }
}
