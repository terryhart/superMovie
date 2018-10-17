package dev.baofeng.com.supermovie.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.huangyong.downloadlib.model.Params;
import com.huangyong.downloadlib.utils.BroadCastUtils;

import java.io.File;
import java.util.Locale;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.utils.DownloadUtil;


/**
 * Created by HuangYong on 2018/7/31.
 */
public class DownLoadService extends IntentService {

    private NotificationManager notificationManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownLoadService(String name) {
        super(name);
    }

    public DownLoadService() {
        super("someName");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getStringExtra(Params.DURL);
        //未安装或者版本低，开始下载,如果已开始下载，吐司进度
        showToastByRunnable(this,"开始下载",Toast.LENGTH_SHORT);
        DownloadUtil.get().download(url, "app_update", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String downPath) {
                //下载完成，静默安装，完成后会发广播
                showToastByRunnable(DownLoadService.this,"下载完成,准备安装",Toast.LENGTH_SHORT);
                installApp(downPath);
                showRemoteView(getApplicationContext(),100);
            }
            @Override
            public void onDownloading(int progress) {
                Log.e("下载中",progress+"");
                showRemoteView(getApplicationContext(),progress);
            }

            @Override
            public void onDownloadFailed() {
                Log.e("下载中","下载失败");

            }
        });
    }
    private void installApp(String savePath) {

        Uri uri = Uri.fromFile(new File(savePath));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
       getApplicationContext().startActivity(intent);
    }
    private void showToastByRunnable(final IntentService context, final CharSequence text, final int duration) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, duration).show();
            }
        });
    }


    private void showRemoteView(Context context,int progress) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.notification_upgrade);
        remoteViews.setImageViewResource(R.id.icon_noti, R.mipmap.icon);
        remoteViews.setTextViewText(R.id.progress, progress+"");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setContent(remoteViews);
        Notification build = builder.build();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            builder.setCustomBigContentView(remoteViews);
        }

        notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(111, build);
    }
}
