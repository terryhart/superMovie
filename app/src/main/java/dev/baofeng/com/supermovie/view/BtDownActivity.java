package dev.baofeng.com.supermovie.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;


import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.BtDownAdapter;
import dev.baofeng.com.supermovie.domain.BTParamInfo;
import dev.baofeng.com.supermovie.domain.BtInfo;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;
import dev.baofeng.com.supermovie.presenter.GetRecpresenter;
import dev.baofeng.com.supermovie.presenter.iview.IMoview;
import dev.baofeng.com.supermovie.utils.BlurUtil;

/**
 * Created by huangyong on 2018/2/12.
 */

public class BtDownActivity extends AppCompatActivity implements IMoview{

    @BindView(R.id.poster)
    ImageView poster;
    @BindView(R.id.mvname)
    TextView mvname;
    @BindView(R.id.btdownrv)
    RecyclerView btdownrv;
    @BindView(R.id.detail_img_bg)
    ImageView detailImgBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bt_downlayout);
        ButterKnife.bind(this);
        GetRecpresenter getRecpresenter = new GetRecpresenter(this, this);
        String title = getIntent().getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        String posterurl = getIntent().getStringExtra(GlobalMsg.KEY_POST_IMG);
        mvname.setText(title);

        getRecpresenter.getBtDetail(title);//获取详情
        Glide.with(this).load(posterurl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                BlurUtil.setViewBg(4, 3, detailImgBg, resource);
            }
        });
        Glide.with(this).load(posterurl).into(poster);
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
        BtDownAdapter adapter = new BtDownAdapter(this,result);
        btdownrv.setLayoutManager(new LinearLayoutManager(this));
        btdownrv.setAdapter(adapter);
    }
    public BTParamInfo getParams(String param){
        Gson gson = new Gson();
        String json = "{\"data\": "+param+"}";
        BTParamInfo info = gson.fromJson(json,BTParamInfo.class);
        String de = "";
        return info;
    }
    private File dir;
    private File getDir(){
        if (dir!=null && dir.exists()){
            return dir;
        }

        dir = new File(getExternalCacheDir(), "download");
        if (!dir.exists()){
            dir.mkdirs();
        }
        return dir;
    }
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f M" : "%.1f M", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f K" : "%.1f K", f);
        } else
            return String.format("%d B", size);
    }

}
