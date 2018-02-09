package dev.baofeng.com.supermovie.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.adapter.DownAdapter;
import dev.baofeng.com.supermovie.domain.MovieBean;

/**
 * Created by huangyong on 2018/1/29.
 */

public class DownActivity extends AppCompatActivity {

    @BindView(R.id.post_img)
    ImageView postImg;
    @BindView(R.id.tv_mv_mame)
    TextView tvMvMame;
    @BindView(R.id.rvlist)
    RecyclerView rvlist;
    @BindView(R.id.tv_statu)
    TextView tvStatu;
    private String downUrl;
    private String postImg1;
    private String pathurl;
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                long taskId = (long) msg.obj;
                XLTaskInfo taskInfo = XLTaskHelper.instance(getApplicationContext()).getTaskInfo(taskId);
                tvStatu.setText(
                        "fileSize:" + convertFileSize(taskInfo.mFileSize)
                                + "\n"+ " downSize:" + convertFileSize(taskInfo.mDownloadSize)
                                + "\n"+ " speed:" + convertFileSize(taskInfo.mDownloadSpeed)
                                +  "\n"+"/s dcdnSoeed:" + convertFileSize(taskInfo.mAdditionalResDCDNSpeed)
                                + "\n"+ "/s filePath:" + "/sdcard/" + XLTaskHelper.instance(getApplicationContext()).getFileName(pathurl)
                );
                handler.sendMessageDelayed(handler.obtainMessage(0, taskId), 1000);
            }
        }
    };
    private String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_layout);
        ButterKnife.bind(this);
        XLTaskHelper.init(getApplicationContext());
        iniData();
    }

    private void iniData() {
        downUrl = getIntent().getStringExtra(GlobalMsg.KEY_DOWN_URL);
        postImg1 = getIntent().getStringExtra(GlobalMsg.KEY_POST_IMG);
        title = getIntent().getStringExtra(GlobalMsg.KEY_MOVIE_TITLE);
        tvMvMame.setText(title);
        Glide.with(this).load(postImg1).into(postImg);
        Gson gson = new Gson();

        String before = "{\"msg\":\"请求成功\",\"reg\":\"-101\",\"date\":";
        String later = "}";
        String a = before + downUrl + later;
        MovieBean bean = gson.fromJson(a, MovieBean.class);
        String bbb = "";
        if (bean.getDate().size()>=2){
            for (int i = 0; i < bean.getDate().size(); i++) {
                if (bean.getDate().get(i).contains("http://pan.baidu.com")) ;
//                bean.getDate().remove(i+1);
            }
            for (int i = 0; i < bean.getDate().size(); i++) {
                bbb += bean.getDate().get(i) + "\n";
            }
        }else {
            bbb += bean.getDate().get(0);
            if (bbb.contains("http://pan.baidu.com")||bbb.length()<10);
            bbb+="下载地址暂无";
            tvMvMame.setText(title + "\n" + bbb);
            return;
        }

        DownAdapter adapter = new DownAdapter(this, bean);
        Log.d("ZIZIZIZIIZI:",bbb);
        adapter.setOnItemClickListener(new DownAdapter.onItemClick() {
            @Override
            public void onItemclicks(String url) {
                try {
                    pathurl = url;
                    long taskId = 0;
                    try {
                        taskId = XLTaskHelper.instance(getApplicationContext()).addThunderTask(url, "/sdcard/", null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.sendMessage(handler.obtainMessage(0, taskId));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onBaiduPanClick(String url) {
                Toast.makeText(DownActivity.this, "即将打开浏览器，前往浏览器继续操作", Toast.LENGTH_SHORT).show();
            }
        });
        rvlist.setLayoutManager(new GridLayoutManager(this, 3));
        rvlist.setAdapter(adapter);
    }

    /**
     * 下载文件
     *
     * @param view
     */
    public void downLoad(View view) {
        if (!TextUtils.isEmpty(downUrl)) {
            long taskId = 0;
            try {
                taskId = XLTaskHelper.instance(getApplicationContext()).addThunderTask(downUrl, "/sdcard/", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            handler.sendMessage(handler.obtainMessage(0, taskId));
        }
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
