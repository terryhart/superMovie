package dev.baofeng.com.supermovie.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
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

    private static final int INSTALL_APK_REQUESTCODE = 100;
    private NotificationManager notificationManager;

    private boolean isDownloading = false;
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

        downLoadFile(url);
        DownloadUtil.get().download(url, "app_update", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File apkFile) {
                isDownloading = false;
                //下载完成，静默安装，完成后会发广播
                showToastByRunnable(DownLoadService.this,"下载完成,准备安装",Toast.LENGTH_SHORT);
                installApp(apkFile);
            }
            @Override
            public void onDownloading(int progress, long total) {

                Intent intents = new Intent();
                intents.setAction(Params.ACTION_UPDATE_PROGERSS);
                intents.putExtra(Params.UPDATE_PROGERSS,progress);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intents);

                isDownloading = true;
            }

            @Override
            public void onDownloadFailed() {
                Log.e("下载中","下载失败");
                isDownloading = false;
            }
        });
    }

    private void downLoadFile(String url) {

    }

    /**
     * https://blog.csdn.net/qq_17470165/article/details/80574195
     * @param apkFile
     */
    private void installApp(File apkFile) {

        if (Build.VERSION.SDK_INT >= 26) {
            //来判断应用是否有权限安装apk
            boolean installAllowed= getPackageManager().canRequestPackageInstalls();
            //有权限
            if (installAllowed) {
                //安装apk
                install(apkFile.getPath());
            } else {
                //无权限 申请权限
                //ActivityCompat.requestPermissions(DownLoadService.this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, INSTALL_APK_REQUESTCODE);
            }
        } else {
            install(apkFile.getPath());
        }

    }

    private void install(String apkPath) {
        //7.0以上通过FileProvider
        if (Build.VERSION.SDK_INT > 25) {
            Uri uri = FileProvider.getUriForFile(this, "dev.baofeng.com.supermovie.fileprovider", new File(apkPath));
            Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(uri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + apkPath), "application/vnd.android.package-archive");
            startActivity(intent);
        }
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


}
