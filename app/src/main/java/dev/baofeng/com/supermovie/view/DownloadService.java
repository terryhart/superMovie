package dev.baofeng.com.supermovie.view;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by huangyong on 2018/2/12.
 */

public class DownloadService extends Service {

    private Timer timer;
    private TimerTask task;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    /**
     * 开始循环查询下载列表，如果有新任务，就开始下载
     * 多任务下载，多次peek即可，每次取出任务都移除掉队列中的元素
     * 如果正在下载的任务达到上限，队列中其他任务开始等待。
     */
    public void startLoop(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Log.d("TTTTTTDOWN","正在查询是否有新任务");
                 if ( GlobalMsg.downQueue.size()>0){
//                     String peek = GlobalMsg.downQueue.element();//返回第一个元素
//                     String peek = GlobalMsg.downQueue.peek();//返回第一个元素
                     String peek = GlobalMsg.downQueue.peek();//返回第一个元素并在队列中删除
                     Log.d("TTTTTTDOWN","当前有新任务了:"+peek);
                     GlobalMsg.downQueue.poll();//下载完成后删除该任务
                 }else {
                    // stopLoop();
                 }
            }
        };
        timer.schedule(task,0,2000l);
    }
    public void stopLoop(){
        timer.cancel();
        task.cancel();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    public class LocalBinder extends Binder {
        public DownloadService getService() {
            // Return this instance of LocalService so clients can call public methods
            return DownloadService.this;
        }
    }

    @Override
    public void onDestroy() {
        stopLoop();
        super.onDestroy();
    }
}
