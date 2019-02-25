package dev.baofeng.com.supermovie.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huangyong.downloadlib.db.HistoryDao;
import com.huangyong.downloadlib.domain.HistoryInfo;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HistoryAdapter;
import dev.baofeng.com.supermovie.domain.MovieInfo;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.rv_his_list)
    SwipeMenuRecyclerView rvHisList;
    @BindView(R.id.clear)
    Button clear;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        clear.setOnClickListener(this);

        HistoryDao dao = HistoryDao.getInstance(getApplicationContext());
        List<HistoryInfo> historyInfos = dao.queryAll();

        if (historyInfos != null && historyInfos.size() > 0) {
            MovieInfo info = new MovieInfo();
            List<MovieInfo.DataBean> dataBeans = new ArrayList<>();
            for (int i = 0; i < historyInfos.size(); i++) {
                MovieInfo.DataBean dataBean = new MovieInfo.DataBean();
                dataBean.setDownimgurl(historyInfos.get(i).getPostImgUrl());
                dataBean.setDownLoadName(historyInfos.get(i).getTitle());
                dataBean.setDownLoadUrl(historyInfos.get(i).getLocalPath());
                dataBean.setProgress(historyInfos.get(i).getProgress());
                dataBean.setUrlMd5(historyInfos.get(i).getUrlMd5());
                dataBeans.add(dataBean);
            }
            info.setData(dataBeans);
            rvHisList.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
            adapter = new HistoryAdapter(HistoryActivity.this, info);
            rvHisList.setAdapter(adapter);
        } else {
            Toast.makeText(this, "暂无观看记录哦", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.mipmap.icon)//设置标题的图片
                .setTitle("提示！")//设置对话框的标题
                .setMessage("是否清空历史记录")//设置对话框的内容
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
                        HistoryDao dao = HistoryDao.getInstance(getApplicationContext());
                        dao.deleteAll();
                        if (adapter!=null){
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();



    }
}
