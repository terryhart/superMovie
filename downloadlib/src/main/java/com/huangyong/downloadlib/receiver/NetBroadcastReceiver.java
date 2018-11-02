package com.huangyong.downloadlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.huangyong.downloadlib.utils.NetUtil;


/**
 *
 * Created by Zion on 2017/10/5.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // 如果相等的话就说明网络状态发生了变化
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            switch (netWorkState){
                case NetUtil.NETWORK_NONE:
                    Toast.makeText(context, "当前网络连接异常，可能无法下载影片", Toast.LENGTH_SHORT).show();
                    break;
                case NetUtil.NETWORK_MOBILE:
//                    ToastUtil.showMessage("当前网络为移动网络，请注意您的流量");
                    Toast.makeText(context, "当前网络为移动网络，请注意您的流量", Toast.LENGTH_SHORT).show();
                    //不允许4G时下载
                    boolean isAllow4G = false;
                    /*if(!isAllow4G && DownloadManager.xLTaskInfos != null && DownloadManager.xLTaskInfos.size() > 0){
                        for (int i = 0; i < DownloadManager.xLTaskInfos.size(); i++){
                            if(DownloadManager.xLTaskInfos.get(i).mTaskStatus == 1)
                                DownloadManager.stopTask(DownloadManager.xLTaskInfos.get(i).mTaskId);
                        }
                    }*/
                    break;
                case NetUtil.NETWORK_WIFI:
                    break;
                default:
                    break;
            }
        }
    }
}
