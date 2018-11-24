package dev.baofeng.com.supermovie.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dev.baofeng.com.supermovie.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HuangYong on 2018/6/25.
 */
public class DownloadUtil {
    private static DownloadUtil downloadUtil;
    private final OkHttpClient okHttpClient;
    private static String currentUrl;
    private boolean isDownloading=false;
    public static DownloadUtil get() {
        if (downloadUtil == null) {
            downloadUtil = new DownloadUtil();
        }
        return downloadUtil;
    }

    private DownloadUtil() {
        okHttpClient = new OkHttpClient();
    }
    /**
     * @param url 下载连接
     * @param saveDir 储存下载文件的SDCard目录
     * @param listener 下载监听
     */
    public void download(final String url, final String saveDir, final OnDownloadListener listener) {
        if (isDownloading){
            return;
        }
        isDownloading=true;
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
                isDownloading=false;
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(savePath, getNameFromUrl(url));
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                       int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    isDownloading=false;
                    listener.onDownloadSuccess(file);
                } catch (Exception e) {
                    isDownloading=false;
                    listener.onDownloadFailed();
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null){
                            is.close();
                        }
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null){
                            fos.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        });
    }


    /**
     * @param saveDir
     * @return
     * @throws IOException
     * 判断下载目录是否存在
     */
    private String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }else {
            File[] files = downloadFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    @NonNull
    private String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    public interface OnDownloadListener {
        /**
         * 下载成功
         * @param file
         */
        void onDownloadSuccess(File file);

        /**
         * @param progress
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();
    }
    private void showRemoteView(Context context) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.notification_upgrade);
        remoteViews.setImageViewResource(R.id.icon_noti, R.mipmap.icon);
        remoteViews.setTextViewText(R.id.progress, 20+"");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setContent(remoteViews);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            builder.setCustomBigContentView(remoteViews);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(111, builder.build());
    }
}
