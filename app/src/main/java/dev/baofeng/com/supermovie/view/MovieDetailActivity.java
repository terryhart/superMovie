package dev.baofeng.com.supermovie.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.db.FavorDao;
import com.huangyong.downloadlib.domain.FavorInfo;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.BlurUtil;
import com.huangyong.downloadlib.utils.MD5Utils;
import com.xyzlf.share.library.bean.ShareEntity;
import com.xyzlf.share.library.interfaces.ShareConstant;
import com.xyzlf.share.library.util.ShareUtil;

import java.util.ArrayList;
import java.util.List;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.DetailAdapter;
import dev.baofeng.com.supermovie.adapter.DownListAdapter;
import dev.baofeng.com.supermovie.adapter.HeaderAndFooterWrapper;
import dev.baofeng.com.supermovie.domain.DetailInfo;

/**
 *  intent.putExtra(GlobalMsg.KEY_POST_IMG, finalImgUrl);
 intent.putExtra(GlobalMsg.KEY_DOWN_URL,datas.getData().get(position).getDownLoadUrl());
 intent.putExtra(GlobalMsg.KEY_MOVIE_TITLE,datas.getData().get(position).getDownLoadName());
 intent.putExtra(GlobalMsg.KEY_MOVIE_DETAIL,datas.getData().get(position).getMvdesc());
 */
public class MovieDetailActivity extends AppCompatActivity implements OnItemClickListenr {

    private ImageView poster;
    private String title;
    private String downUrl;
    private String posterUrl;
    private String mvdescTx;
    private RecyclerView recyclerView;
    private String posterImagUrl;
    private String imgScreenShot;
    private Toolbar toolbar;
    private AppBarLayout detail_app_bar;
    private TextView titleView;
    private DetailAdapter detailAdapter;
    private String downItemTitle;
    private String[] downItemList;
    private LinearLayoutManager layoutManager;
    private String[] items;
    private ImageView favor;
    private String playUrl;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private String playTitle;
    private ImageView mDetailPoster;
    private TextView imgTitle;
    private CoordinatorLayout root;
    private ImageView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_layout);
        initData();
        initView();

    }

    private void initData() {
        layoutManager = new LinearLayoutManager(this);

        Intent intent = getIntent();
        posterUrl = intent.getStringExtra(GlobalMsg.KEY_POST_IMG);
        playUrl = intent.getStringExtra(GlobalMsg.KEY_PLAY_URL);
        playTitle = intent.getStringExtra(GlobalMsg.KEY_PLAY_TITLE);
        downItemTitle = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DOWN_ITEM_TITLE);
        downItemList = downItemTitle.split(",");


        if (posterUrl.contains(",")){
           String[] imgArr =  posterUrl.split(",");
            imgScreenShot = imgArr[1];
        }

        posterImagUrl = posterUrl.split(",")[0];
        downUrl = intent.getStringExtra(GlobalMsg.KEY_DOWN_URL);
        title = intent.getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        mvdescTx = intent.getStringExtra(GlobalMsg.KEY_MOVIE_DETAIL);
    }


    private void initView() {
        root = findViewById(R.id.root);
        toolbar = findViewById(R.id.toolbars);
        mDetailPoster = findViewById(R.id.detail_poster);
        imgTitle = findViewById(R.id.imgTitle);
        detail_app_bar = findViewById(R.id.app_bar);
        share = findViewById(R.id.share);
        favor = findViewById(R.id.favor);
        detail_app_bar.addOnOffsetChangedListener(new MyOffsetChangedListener());


        poster = findViewById(R.id.blurPoster);
//        mvdesc = findViewById(R.id.mvdesc);
        recyclerView = findViewById(R.id.rv_detail);
        titleView = findViewById(R.id.toolbarTitle);
        titleView.setText(title);
        imgTitle.setText(title);
        recyclerView.setLayoutManager(layoutManager);


        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle(title);

        String[] splitArr = mvdescTx.split("◎");
        StringBuffer buffer = new StringBuffer();
        ArrayList<String> listDesc=new ArrayList<>();
        DetailInfo info = new DetailInfo();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (splitArr.length>5){
            for (int i = 1; i < 5; i++) {
                if (splitArr[i].contains("//") ){
                    continue;
                }
                buffer.append(splitArr[i]);
                listDesc.add(splitArr[i]);
            }

            StringBuffer descBuffer =new StringBuffer();
            for (int i = 1; i <splitArr.length ; i++) {
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
        downLoadListDialog.setCanceledOnTouchOutside(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downLoadListDialog.show();
            }
        });
        Glide.with(this).load(posterImagUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap blurBitmap = BlurUtil.getBlurBitmap(4, 4, resource);
                mDetailPoster.setImageBitmap(resource);
                poster.setImageBitmap(blurBitmap);
            }
        });
        //初始化收藏状态
        initFavorTag();
        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavor();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareEntity testBean = new ShareEntity(title, "看电影，更方便");
                testBean.setContent("热门电影，美剧，海量资源每日更新");
                testBean.setUrl("https://fir.im/btmovie"); //分享链接
                testBean.setDrawableId(R.mipmap.icon_share);
                ShareUtil.showShareDialog(MovieDetailActivity.this, testBean, ShareConstant.REQUEST_CODE);
            }
        });

    }

    /**
     * 使用统一数据结构
     */
    public void showShareDialog(View view) {
        ShareEntity testBean = new ShareEntity("我是标题", "我是内容，描述内容。");
        testBean.setUrl("https://www.baidu.com"); //分享链接
        testBean.setImgUrl("https://www.baidu.com/img/bd_logo1.png");
        ShareUtil.showShareDialog(this, testBean, ShareConstant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void initFavorTag() {
        FavorDao dao = FavorDao.getInstance(getApplicationContext());
        String md5 = MD5Utils.stringToMD5(downUrl);
        List<FavorInfo> favorInfos = dao.queryForFeilds("urlMd5", md5);
        if (favorInfos!=null&&favorInfos.size()>0){
            favor.setBackgroundResource(R.drawable.fullscreen_favority_press);
        }else {
            favor.setBackgroundResource(R.drawable.fullscreen_favority_normal);
        }
    }

    /**
     * 添加或取消收藏
     */
    private void toggleFavor() {

        FavorDao dao = FavorDao.getInstance(getApplicationContext());
        String md5 = MD5Utils.stringToMD5(downUrl);
        List<FavorInfo> favorInfos = dao.queryForFeilds("urlMd5", md5);
        if (favorInfos!=null&&favorInfos.size()>0){
            dao.delete(favorInfos.get(0).getId());
            favor.setBackgroundResource(R.drawable.fullscreen_favority_normal);
            Toast.makeText(this, "已取消收藏", Toast.LENGTH_SHORT).show();
        }else {
            FavorInfo info = new FavorInfo();
            info.setMovieDesc(mvdescTx);
            info.setPostImgUrl(posterUrl);
            info.setTitle(title);
            info.setTaskUrl(downUrl);
            info.setUrlMd5(md5);
            info.setDownItemTitle(downItemTitle);
            dao.add(info);
            Toast.makeText(this, "已添加收藏", Toast.LENGTH_SHORT).show();
            favor.setBackgroundResource(R.drawable.fullscreen_favority_press);
        }

    }

    private class MyOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {

        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            float progress = Math.abs(verticalOffset) * 1.0f / appBarLayout.getTotalScrollRange();
            if (progress >= 0.8) {
                toolbar.setVisibility(View.VISIBLE);
                titleView.setAlpha(progress);
            } else {
                toolbar.setVisibility(View.VISIBLE);
                titleView.setAlpha(0.0f);
            }
        }
    }

    @Override
    public void clicked(String url, String imgUrl) {
        TaskLibHelper.addNewTask(url, Params.DEFAULT_PATH,imgUrl,getApplicationContext());

        Snackbar.make(root, "下载任务已添加", Snackbar.LENGTH_LONG).show();
    }
}
