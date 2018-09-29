package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.huangyong.downloadlib.db.HistoryDao;
import com.huangyong.downloadlib.db.TaskDao;
import com.huangyong.downloadlib.domain.HistoryInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.HistoryAdapter;
import dev.baofeng.com.supermovie.adapter.HomeAdapter;
import dev.baofeng.com.supermovie.adapter.SearchAdapter;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.domain.RecentUpdate;

import static dev.baofeng.com.supermovie.MyApp.getContext;

public class HistoryActivity extends AppCompatActivity {

    @BindView(R.id.rv_his_list)
    RecyclerView rvHisList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        HistoryDao dao = HistoryDao.getInstance(getApplicationContext());
        List<HistoryInfo> historyInfos = dao.queryAll();

        if (historyInfos!=null&&historyInfos.size()>0){
            Log.e("daohistory",historyInfos.size()+"");
            MovieInfo info = new MovieInfo();
            for (int i = 0; i < historyInfos.size(); i++) {
                MovieInfo.DataBean dataBean = new MovieInfo.DataBean();
                dataBean.setDownimgurl(historyInfos.get(i).getPostImgUrl());
                dataBean.setDownLoadName(historyInfos.get(i).getTitle());
                dataBean.setDownLoadUrl(historyInfos.get(i).getLocalPath());
                dataBean.setProgress(historyInfos.get(i).getProgress());
                dataBean.setUrlMd5(historyInfos.get(i).getUrlMd5());
                info.getData().add(dataBean);
            }
            rvHisList.setAdapter(new HistoryAdapter(HistoryActivity.this,info));
        }else {
            Toast.makeText(this, "暂无观看记录哦", Toast.LENGTH_SHORT).show();
        }
    }
}
