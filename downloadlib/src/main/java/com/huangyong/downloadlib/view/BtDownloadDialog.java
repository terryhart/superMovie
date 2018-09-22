package com.huangyong.downloadlib.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.huangyong.downloadlib.R;


public class BtDownloadDialog {
    private static Dialog dialog;

    public static Dialog getInstance(Context context, int layoutId) {
        dialog = new Dialog(context, R.style.Dialog_Fullscreen);
        dialog.setContentView(layoutId);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }
}
