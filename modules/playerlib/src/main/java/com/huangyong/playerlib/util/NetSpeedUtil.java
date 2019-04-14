package com.huangyong.playerlib.util;

import android.content.Context;
import android.net.TrafficStats;

import java.text.DecimalFormat;

public class NetSpeedUtil {

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    /**
     * 得到网络速度
     * @param context
     * @return
     */
    public String getNetSpeed(Context context) {
        String netSpeed = "0 kb/s";
        long nowTotalRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid)==
                TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB;
        long nowTimeStamp = System.currentTimeMillis();
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换

        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
        netSpeed  = String.valueOf(speed) + " kb/s";
        return  netSpeed;
    }
}
