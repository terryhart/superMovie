package com.huangyong.downloadlib.utils;

import android.content.Context;
import android.content.Intent;


public class BroadCastUtils {

    public static void sendIntentBroadCask(Context context, String action){
        Intent intent = new Intent();
        intent.setAction(action);
        context.sendBroadcast(intent);
    }
}
