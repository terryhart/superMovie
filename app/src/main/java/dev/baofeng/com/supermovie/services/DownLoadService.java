package dev.baofeng.com.supermovie.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.huangyong.downloadlib.model.Params;

import java.io.File;

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
            public void onDownloadSuccess(File downPath) {
                //下载完成，静默安装，完成后会发广播
                showToastByRunnable(DownLoadService.this,"下载完成,准备安装",Toast.LENGTH_SHORT);
                installApp(downPath);
            }
            @Override
            public void onDownloading(int progress) {
                Log.e("下载中",progress+"");
                Intent intents = new Intent();
                intents.setAction(Params.ACTION_UPDATE_PROGERSS);
                intents.putExtra(Params.UPDATE_PROGERSS,progress);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intents);
            }

            @Override
            public void onDownloadFailed() {
                Log.e("下载中","下载失败");

            }
        });
    }
    private void installApp(File apkFile) {



        if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(this, "dev.baofeng.com.supermovie.fileprovider", apkFile);//在AndroidManifest中的android:authorities值
            Intent install = new Intent();
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            startActivity(install);
        } else{
            Intent install = new Intent();
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(install);
        }








       /* Uri uri = Uri.fromFile(new File(savePath));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        getApplicationContext().startActivity(intent);*/
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


   /* private void showRemoteView(Context context,int progress) {

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
    }*/
}
