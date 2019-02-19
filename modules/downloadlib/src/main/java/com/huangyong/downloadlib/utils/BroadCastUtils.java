package com.huangyong.downloadlib.utils;

import android.content.Context;
import android.content.Intent;


public class BroadCastUtils {

    public static void sendIntentBroadCask(Context context, Intent intent, String action){
        intent.setAction(action);
        context.sendBroadcast(intent);
    }
}
