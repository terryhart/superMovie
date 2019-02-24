package dev.baofeng.com.supermovie.view;
/**
 * Created by HuangYong on 2018/9/29.
 */

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import dev.baofeng.com.supermovie.adapter.SearchAdapter;
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
public class FavorActivity extends AppCompatActivity implements SearchAdapter.onLongClickedListener {

    @BindView(R.id.clearFavor)
    Button clearFavor;
    @BindView(R.id.rv_favor_list)
    RecyclerView rvFavorList;
    private SearchAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favor_layout);
        ButterKnife.bind(this);

        clearFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(FavorActivity.this, "已清空收藏记录", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFavordata() {
        FavorDao dao = FavorDao.getInstance(getApplicationContext());
        List<FavorInfo> favorInfos = dao.queryAll();
        MovieInfo info = new MovieInfo();
        List<MovieInfo.DataBean> dataBeans = new ArrayList<>();
        if (favorInfos != null && favorInfos.size() > 0) {


            for (int i = 0; i < favorInfos.size(); i++) {
                MovieInfo.DataBean dataBean = new MovieInfo.DataBean();
                dataBean.setDownimgurl(favorInfos.get(i).getPostImgUrl());
                dataBean.setDownLoadName(favorInfos.get(i).getTitle());
                dataBean.setDownLoadUrl(favorInfos.get(i).getTaskUrl());
                dataBean.setDowndtitle(favorInfos.get(i).getDownItemTitle());
                dataBean.setMvdesc(favorInfos.get(i).getMovieDesc());
                dataBean.setId(favorInfos.get(i).getId()+"");

                dataBeans.add(dataBean);
            }


        } else {
            Toast.makeText(this, "暂无收藏记录哦", Toast.LENGTH_SHORT).show();
        }
        info.setData(dataBeans);
        rvFavorList.setLayoutManager(new LinearLayoutManager(FavorActivity.this));
        adapter = new SearchAdapter(FavorActivity.this, info, this);
        rvFavorList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFavordata();
    }

    @Override
    public void onLongClick(String id) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("提示！")//设置对话框的标题
                .setMessage("是否删除本条记录")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FavorDao dao = FavorDao.getInstance(getApplicationContext());
                        dao.delete(Integer.parseInt(id));
                        initFavordata();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }
}
