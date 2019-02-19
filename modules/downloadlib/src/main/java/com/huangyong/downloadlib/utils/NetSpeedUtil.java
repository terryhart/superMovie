package com.huangyong.downloadlib.utils;

import android.content.Context;
import android.net.TrafficStats;

import java.text.DecimalFormat;

public class NetSpeedUtil {
    private long rxtxTotal = 0;
    private long mobileRecvSum = 0;
    private long mobileSendSum = 0;
    private long wlanRecvSum = 0;
    private long wlanSendSum = 0;
    private DecimalFormat showFloatFormat = new DecimalFormat("0.00");

    public String updateViewData(Context context) {
        long tempSum = TrafficStats.getTotalRxBytes()
                + TrafficStats.getTotalTxBytes();
        long rxtxLast = tempSum - rxtxTotal;
        double totalSpeed = rxtxLast * 1000 / 2000d;
        rxtxTotal = tempSum;
       return showSpeed(totalSpeed);
    }
    private String showSpeed(double speed) {
        String speedString;
        if (speed >= 1048576d) {
            speedString = showFloatFormat.format(speed / 1048576d) + "MB/s";
        } else {
            speedString = showFloatFormat.format(speed / 1024d) + "KB/s";
        }
        return speedString;
    }

}
