package dev.baofeng.com.supermovie.utils;

import android.os.Environment;


public class DownloadConfig {

	/**
	 * 每个文件的分段下载线程数
	 */
	public static final int DONWNLOAD_THREAD_NUM = 3;

	/**
	 * 文件下载后在本地的保存路径
	 */
	public static final String DOWNLOAD_PATH =
			Environment.getExternalStorageDirectory().getAbsolutePath()+ "/downloads/";
	//----------------
	//web相关请求路径名称
	/**
	 * web上的URL路径
	 */
//	private final static String URL = "http://120.36.65.18:8080/";
//	private final static String URL = "http://110.80.146.185:8080/";
	private final static String URL = "http://192.168.254.158:8080/";
//	public final static String URL = "http://172.26.201.4:8080/";
//	public final static String URL = "http://222.76.33.169:8080/";
	/**
	 * web上的工程名
	 */
	private final static String PROJECT_NAME = "webService";
	/**
	 * web上的请求内容
	 */
	private final static String URL_TARGET = URL + PROJECT_NAME+"/index.jsp?content=";
	/**
	 * 文件web上的下载地址
	 */
	public final static String URL_DOWNLOAD_PATH = URL +PROJECT_NAME+"/temp/";

	//------------------------------
	//get和set方法
	/**
	 * 获取URL路径
	 */
	public static String getURL() {
		return URL;
	}
	/**
	 * 获取发送给web的url和信息
	 */
	public static String getURL_TARGET() {
		return URL_TARGET;
	}

	/**
	 * 获取下载文件web端的地址/路径
	 */
	public static String getURL_DOWNLOAD_PATH() {
		return URL_DOWNLOAD_PATH;
	}
}
