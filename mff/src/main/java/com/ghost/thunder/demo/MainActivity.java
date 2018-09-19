package com.ghost.thunder.demo;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huangyong.downloadlib.DownLoadMainActivity;
import com.xunlei.downloadlib.XLTaskHelper;
import com.xunlei.downloadlib.parameter.XLTaskInfo;

public class MainActivity extends AppCompatActivity {
    private TextView tvstatu;
    private Button btdown;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0) {
                long taskId = (long) msg.obj;
                XLTaskInfo taskInfo = XLTaskHelper.instance().getTaskInfo(taskId);
                tvstatu.setText(
                        "fileSize:" + taskInfo.mFileSize
                                + " downSize:" + taskInfo.mDownloadSize
                                + " speed:" + convertFileSize(taskInfo.mDownloadSpeed)
                                + "/s dcdnSpeed:" + convertFileSize(taskInfo.mAdditionalResDCDNSpeed)
                                + "/s filePath:" + "/sdcard/" + XLTaskHelper.instance().getFileName("magnet:?xt=urn:btih:EXE5HEFPOVAJLNONKPQMX5HVEW7HS3KD&dn=dzd6.720p.HD%E9%9F%A9%E7%89%88%E4%B8%AD%E8%8B%B1%E5%8F%8C%E5%AD%97%5B%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv%5D.mp4&tr=udp%3A%2F%2F9.rarbg.to%3A2710%2Fannounce&tr=udp%3A%2F%2F9.rarbg.me%3A2710%2Fannounce&tr=http%3A%2F%2Ftracker.trackerfix.com%2Fannounce&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&tr=udp%3A%2F%2Fp4p.arenabg.com%3A1337&tr=wss%3A%2F%2Ftracker.openwebtorrent.com&tr=wss%3A%2F%2Ftracker.btorrent.xyz&tr=wss%3A%2F%2Ftracker.fastcast.nz&tr=udp%3A%2F%2F10.rarbg.com%2Fannounce&tr=udp%3A%2F%2F10.rarbg.me%3A80%2Fannounce&tr=udp%3A%2F%2F11.rarbg.com%3A80%2Fannounce&tr=udp%3A%2F%2F11.rarbg.me%3A80%2Fannounce&xl=1692176734")
                );
                handler.sendMessageDelayed(handler.obtainMessage(0,taskId),1000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvstatu = (TextView) findViewById(R.id.tv_status);
        btdown = (Button) findViewById(R.id.btn_down);
        Button center = (Button) findViewById(R.id.center);

        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DownLoadMainActivity.class);
                startActivity(intent);
            }
        });
        btdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long taskId = 0;
                try {
                    taskId = XLTaskHelper.instance().addThunderTask("magnet:?xt=urn:btih:EXE5HEFPOVAJLNONKPQMX5HVEW7HS3KD&dn=dzd6.720p.HD%E9%9F%A9%E7%89%88%E4%B8%AD%E8%8B%B1%E5%8F%8C%E5%AD%97%5B%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv%5D.mp4&tr=udp%3A%2F%2F9.rarbg.to%3A2710%2Fannounce&tr=udp%3A%2F%2F9.rarbg.me%3A2710%2Fannounce&tr=http%3A%2F%2Ftracker.trackerfix.com%2Fannounce&tr=udp%3A%2F%2Fopen.demonii.com%3A1337&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337%2Fannounce&tr=udp%3A%2F%2Fp4p.arenabg.com%3A1337&tr=wss%3A%2F%2Ftracker.openwebtorrent.com&tr=wss%3A%2F%2Ftracker.btorrent.xyz&tr=wss%3A%2F%2Ftracker.fastcast.nz&tr=udp%3A%2F%2F10.rarbg.com%2Fannounce&tr=udp%3A%2F%2F10.rarbg.me%3A80%2Fannounce&tr=udp%3A%2F%2F11.rarbg.com%3A80%2Fannounce&tr=udp%3A%2F%2F11.rarbg.me%3A80%2Fannounce&xl=1692176734","/sdcard/",null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendMessage(handler.obtainMessage(0,taskId));
            }
        });
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
