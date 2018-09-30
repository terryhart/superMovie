package com.ghost.thunder.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.se_bastiaan.torrentstream.TorrentOptions;
import com.github.se_bastiaan.torrentstream.TorrentStream;
import com.huangyong.downloadlib.DownLoadMainActivity;
import com.huangyong.downloadlib.TaskLibHelper;
import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.FileUtils;


public class MainActivity extends AppCompatActivity {
    private TextView tvstatu;
    private Button btdown;

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
        tvstatu =  findViewById(R.id.tv_status);
        btdown =  findViewById(R.id.btn_down);
        initReceiver();
        Button center =findViewById(R.id.center);

        center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TorrentOptions torrentOptions = new TorrentOptions.Builder()
                        .saveLocation(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
                        .removeFilesAfterStop(true)
                        .build();

                TorrentStream torrentStream = TorrentStream.init(torrentOptions);
                torrentStream.startStream("http://192.168.8.208/test.torrent");

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
//                String url = "magnet:?xt=urn:btih:7HB6WIZ5PEBUTSOFSREGXQUUNQXNE6RC&dn=%e5%8a%9e%e5%85%ac%e5%ae%a4%e5%83%b5%e5%b0%b8%e8%b5%b7%e4%b9%89%2e720p%2eBD%e4%b8%ad%e8%8b%b1%e5%8f%8c%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e66ys%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337";
//                String url = "ed2k://|file|%E5%8A%9E%E5%85%AC%E5%AE%A4%E5%83%B5%E5%B0%B8%E8%B5%B7%E4%B9%89.720p.BD%E4%B8%AD%E8%8B%B1%E5%8F%8C%E5%AD%97[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mp4|1056302592|E2A1F1EF5D1FF132A5D1B7C483AF1248|h=3C5BBNH52KOB7GQXOOBVNIBH3CAGWEDO|/";
//                String savepath = Params.DEFAULT_PATH;
//                String postImgUrl = "https://tu.66vod.net/2018/3469.jpg";
//                TaskLibHelper.addNewTask(url,savepath,postImgUrl,getApplicationContext());
            }
        });
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Params.ACTION_PLAY_URL);
        registerReceiver(playerRecevier,intentFilter);
    }
    BroadcastReceiver playerRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Params.ACTION_PLAY_URL)){
               /* String url = intent.getStringExtra(Params.PROXY_PALY_URL);
                Intent ints = new Intent(MainActivity.this, PlayerActivity.class);
                ints.putExtra(Params.PROXY_PALY_URL,url);
                startActivity(ints);*/
            }
        }
    };
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


    public void downTorrent(View view) {
        String url = "magnet:?xt=urn:btih:7HB6WIZ5PEBUTSOFSREGXQUUNQXNE6RC&dn=%e5%8a%9e%e5%85%ac%e5%ae%a4%e5%83%b5%e5%b0%b8%e8%b5%b7%e4%b9%89%2e720p%2eBD%e4%b8%ad%e8%8b%b1%e5%8f%8c%e5%ad%97%5b%e6%9c%80%e6%96%b0%e7%94%b5%e5%bd%b1www%2e66ys%2etv%5d%2emp4&tr=udp%3a%2f%2f9%2erarbg%2eto%3a2710%2fannounce&tr=udp%3a%2f%2f9%2erarbg%2eme%3a2710%2fannounce&tr=http%3a%2f%2ftr%2ecili001%2ecom%3a8070%2fannounce&tr=http%3a%2f%2ftracker%2etrackerfix%2ecom%3a80%2fannounce&tr=udp%3a%2f%2fopen%2edemonii%2ecom%3a1337&tr=udp%3a%2f%2ftracker%2eopentrackr%2eorg%3a1337%2fannounce&tr=udp%3a%2f%2fp4p%2earenabg%2ecom%3a1337";
        String savepath = Params.DEFAULT_PATH;
        String postImgUrl = "https://tu.66vod.net/2018/3469.jpg";
        TaskLibHelper.addNewTask(url,savepath,postImgUrl,getApplicationContext());
    }

    public void downed2k(View view) {
        String url = "ed2k://|file|%E6%97%85%E8%A1%8C%E5%90%A7%EF%BC%81%E4%BA%95%E5%BA%95%E4%B9%8B%E8%9B%99.1080P%E6%97%A0%E6%B0%B4%E5%8D%B0[%E6%9C%80%E6%96%B0%E7%94%B5%E5%BD%B1www.66ys.tv].mp4|1275738885|BB50DFAD60BCDBB26C4C8388E285AD86|h=I35SOTM7GPD4JOF7W2KO45E62GQV3RS7|/";
        String savepath = Params.DEFAULT_PATH;
        String postImgUrl = "https://tu.66vod.net/2018/3561.jpg";
        TaskLibHelper.addNewTask(url,savepath,postImgUrl,getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(playerRecevier);
        super.onDestroy();
    }
}
