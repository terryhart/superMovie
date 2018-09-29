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

	public static String formatDuring(long mss) {
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		if (hours<=0){
			return  minutes + " 分钟 "
					+ seconds + " 秒 ";
		}else {
			return hours + " 小时 " + minutes + " 分钟 "
					+ seconds + " 秒 ";
		}

	}
}
