package com.huangyong.downloadlib.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class Utils {
    public static void copyToClipboard(Context context, String text) {
        ClipboardManager systemService = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//        systemService.setPrimaryClip(ClipData.newPlainText("text", text));
        systemService.setText(text);
    }

}
