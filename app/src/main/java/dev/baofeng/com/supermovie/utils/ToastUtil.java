package dev.baofeng.com.supermovie.utils;

import android.app.Activity;
import android.widget.Toast;


import dev.baofeng.com.supermovie.MyApp;

public class ToastUtil {

	public static void showMessage(final String msg) {
		Toast.makeText(MyApp.getContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public static void showMessage(Activity activity, final String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}

}
