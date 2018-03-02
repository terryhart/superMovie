package dev.baofeng.com.supermovie.bt;

import android.os.SystemClock;
import android.util.Log;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import dev.baofeng.com.supermovie.MyApp;
import dev.baofeng.com.supermovie.domain.RunTaskInfo;
import dev.baofeng.com.supermovie.domain.TaskInfo;

/**
 *  1线程池管理，限制线程池中的数量为3个,
 *  Excutores创建固定数量的线程池
 *  增加和减少操作：
 *      增加：只要没有达到上限，可以添加任务
 *      减少：执行的任务完成了，可以减少操作
 *  2.等待队列的管理，如果下载任务超过3个，进入等待队列，先进先出
 *  如果线程池中有空闲线程，需要从队列头部获取任务
 * Created by Huangyong on 2018/3/1.
 *
 *  难点：选择容器
 *      有序，对尾部元素的操作，和对头部元素的操作
 *      使用linkedList
 *      获取头元素 pollFirst() 从队列中拿出第一个，并在队列中删除，如果为空返回null
 *      添加尾部元素 addLast()将元素添加到队列尾部
 */

public class ThreadUtils {
    private static final int NUM = 60;
    protected static int count = 0;
    public static ThreadPoolExecutor POOLEXCUTOR = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUM);

    public static LinkedList<Task> Tasks = new LinkedList<>();
    /**
     * 执行任务
     * @param task
     * @return 是否执行了，如果线程池满了，返回false（等待状态）
     */
    public static boolean execute(Task task){
        boolean isExcute = false;
        if (count<NUM){
            count++;
            isExcute = true;
            POOLEXCUTOR.execute(task);
        }else {
            //TODO 等待队列，
            //队列的最后一条肯定是数据库最后一条，等待状态，如此类推
            isExcute = false;
            Tasks.addLast(task);
            List<TaskInfo> all = DataSupport.findAll(TaskInfo.class);
            TaskInfo info = all.get(all.size() - 1);
            info.setIsWaiting(1);
            info.save();
//            //发送广播，更新进度条，更新数据库
//            Intent intent = new Intent();
//            intent.setAction(GlobalMsg.ACTIONWAIT);
//            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(MyApp.appInstance());
//            localBroadcastManager.sendBroadcast(intent);
            Log.d("下载任务基数：","下载d队列已满，队列总数"+Tasks.size()+"正在等待队列，广播已发出");

        }
        return isExcute;
    }



    /**
     * 自定义的任务
     *
     */
    public static abstract class Task implements Runnable{

        @Override
        public void run() {
            //自身的耗时工作
            work();

            //问题，当前线程还没执行完，就执行新的耗时操作了
            //可以把下面的代码放到主线程中执行
            //对线程池的操作，减少计数
            MyApp.appInstance().getHandler().post(() -> {
                count--;
                // 从等待队列中获取新任务执行
                Task task = Tasks.pollFirst();
                if (task!=null){
                   execute(task);
                }
            });

        }

        /**
         * 自身耗时的工作
         */
        protected abstract void work();
    }
}

