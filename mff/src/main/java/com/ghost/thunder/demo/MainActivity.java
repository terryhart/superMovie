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
                                + "/s filePath:" + "/sdcard/" + XLTaskHelper.instance().getFileName("ed2k://|file|%E7%BE%8A%E4%B9%8B%E6%A0%91.720p.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mp4|1318890289|A3D10CE3CF7D2551007A24F121D16516|h=Y3M7DLPSYUTACLJ22Y3UL7PU56HTEKPZ|/")
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
                    taskId = XLTaskHelper.instance().addThunderTask("ed2k://|file|%E7%BE%8A%E4%B9%8B%E6%A0%91.720p.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mp4|1318890289|A3D10CE3CF7D2551007A24F121D16516|h=Y3M7DLPSYUTACLJ22Y3UL7PU56HTEKPZ|/","/sdcard/",null);
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
