package dev.baofeng.com.supermovie.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import dev.baofeng.com.supermovie.utils.NetUtil;
import dev.baofeng.com.supermovie.utils.ToastUtil;


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
                    break;
                case NetUtil.NETWORK_MOBILE:
                    ToastUtil.showMessage("当前网络为移动网络，请注意您的流量");
                    //不允许4G时下载
                    boolean isAllow4G = false;
                    break;
                case NetUtil.NETWORK_WIFI:
                    break;
                default:
                    break;
            }
        }
    }
}
