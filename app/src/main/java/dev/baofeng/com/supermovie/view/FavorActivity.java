package dev.baofeng.com.supermovie.view;
/**
 * Created by HuangYong on 2018/9/29.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huangyong.downloadlib.db.FavorDao;
import com.huangyong.downloadlib.db.HistoryDao;
import com.huangyong.downloadlib.domain.FavorInfo;
import com.huangyong.downloadlib.domain.HistoryInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.FavorAdapter;
import dev.baofeng.com.supermovie.domain.MovieInfo;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/29
 * @changeRecord [修改记录] <br/>
 * 2018/9/29 ：created
 */
public class FavorActivity extends AppCompatActivity {

    @BindView(R.id.clearFavor)
    Button clearFavor;
    @BindView(R.id.rv_favor_list)
    RecyclerView rvFavorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favor_layout);
        ButterKnife.bind(this);

        clearFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FavorActivity.this, "已清空收藏记录", Toast.LENGTH_SHORT).show();
            }
        });


        FavorDao dao = FavorDao.getInstance(getApplicationContext());
        List<FavorInfo> favorInfos = dao.queryAll();

        if (favorInfos != null && favorInfos.size() > 0) {
            Log.e("daohistory", favorInfos.size() + "");
            MovieInfo info = new MovieInfo();
            List<MovieInfo.DataBean> dataBeans = new ArrayList<>();
            for (int i = 0; i < favorInfos.size(); i++) {
                MovieInfo.DataBean dataBean = new MovieInfo.DataBean();
                dataBean.setDownimgurl(favorInfos.get(i).getPostImgUrl());
                dataBean.setDownLoadName(favorInfos.get(i).getTitle());
                dataBean.setDownLoadUrl(favorInfos.get(i).getTaskUrl());
                dataBean.setMvdesc(favorInfos.get(i).getMovieDesc());
                dataBean.setUrlMd5(favorInfos.get(i).getUrlMd5());
                dataBeans.add(dataBean);
            }
            info.setData(dataBeans);
            rvFavorList.setLayoutManager(new LinearLayoutManager(FavorActivity.this));
            FavorAdapter adapter = new FavorAdapter(FavorActivity.this, info);
            rvFavorList.setAdapter(adapter);
        } else {
            Toast.makeText(this, "暂无收藏记录哦", Toast.LENGTH_SHORT).show();
        }
    }
}
