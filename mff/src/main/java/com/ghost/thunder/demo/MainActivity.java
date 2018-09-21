package com.ghost.thunder.demo;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.view.DeleteDialog;



public class MainActivity extends AppCompatActivity {
    private TextView tvstatu;
    private Button btdown;
    private Dialog dialog;

    /* Handler handler = new Handler(Looper.getMainLooper()){
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
     };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvstatu = (TextView) findViewById(R.id.tv_status);
        btdown = (Button) findViewById(R.id.btn_down);
        dialog = DeleteDialog.getInstance(this, com.huangyong.downloadlib.R.layout.delete_alert_layout);

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
              /*  long taskId = 0;
                try {
                    taskId = XLTaskHelper.instance().addThunderTask("ed2k://|file|%E7%BE%8A%E4%B9%8B%E6%A0%91.720p.BD%E4%B8%AD%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mp4|1318890289|A3D10CE3CF7D2551007A24F121D16516|h=Y3M7DLPSYUTACLJ22Y3UL7PU56HTEKPZ|/","/sdcard/",null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handler.sendMessage(handler.obtainMessage(0,taskId));*/
                String url = "ed2k://|file|%E5%B9%BF%E4%B8%9C%E5%8D%81%E8%99%8E%E9%93%81%E6%A1%A5%E4%B8%89.1080p%E6%97%A0%E6%B0%B4%E5%8D%B0[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mp4|789446317|CE0F8BE773EBC39CA4FE5836EA3E7D67|h=JLS2U53HMR6NYF77SFZQAPCDWSMZPHDQ|/";
                String savepath = Params.DEFAULT_PATH;
                String postImgUrl = "https://tu.66vod.net/2018/3469.jpg";
                TaskLibHelper.addNewTask(url,savepath,postImgUrl,getApplicationContext());
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

    public void deleteTask(View view) {
        dialog.show();
        Button cancel = dialog.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}
