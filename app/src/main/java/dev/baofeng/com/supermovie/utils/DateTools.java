package dev.baofeng.com.supermovie.utils;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateTools {

	public DateTools() {
	}

	@SuppressLint("SimpleDateFormat") public String getCurrentTime(){
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis() );
		String currentTime = dateFormat.format(date);
		return currentTime;
	}
}
