package dev.baofeng.com.supermovie.utils;

import android.util.Log;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import dev.baofeng.com.supermovie.domain.FileInfo;
import dev.baofeng.com.supermovie.domain.ThreadInfo;

/**
 * 对url处理类
 */
public class URLTools {


	public URLTools() {
	}

	/**
	 * 对下载文件名的url编码进行解码并重命名文件
	 * @param fileInfo
	 */
	public void  toChinese(FileInfo fileInfo){
		URLTools urlTools = new URLTools();
		String fileNme = urlTools.getURLFileName(fileInfo.getUrl(), "/");
		File file = new   File( DownloadConfig.DOWNLOAD_PATH + fileNme);
		Log.i("file","文件重命名前："+ DownloadConfig.DOWNLOAD_PATH + fileNme );
		try {
			String ChineseName = URLDecoder.decode(fileNme, "UTF-8");
			File file2 = new File( DownloadConfig.DOWNLOAD_PATH + ChineseName);// 指定新文件名及路径 
			Log.i("file","文件重命名后："+ DownloadConfig.DOWNLOAD_PATH + ChineseName );
			file.renameTo(file2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对下载文件名的url编码进行解码并重命名文件
	 * @param threadInfo
	 */
	public void  toChinese(ThreadInfo threadInfo){
		URLTools urlTools = new URLTools();
		String fileNme = urlTools.getURLFileName(threadInfo.getUrl(), "/");
		File file = new   File( DownloadConfig.DOWNLOAD_PATH + fileNme);
		Log.i("file","文件重命名前："+ DownloadConfig.DOWNLOAD_PATH + fileNme );
		try {
			String ChineseName = URLDecoder.decode(fileNme, "UTF-8");
			File file2 = new File( DownloadConfig.DOWNLOAD_PATH + ChineseName);// 指定新文件名及路径 
			Log.i("file","文件重命名后："+ DownloadConfig.DOWNLOAD_PATH + ChineseName );
			file.renameTo(file2);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 获取完整http的URL中的文件名
	 * @param url 	    完整http的URL
	 * @param symbol  url中路径是正斜杠“/”或反斜杠“\”或者本地路径的"\\"
	 */
	public String getURLFileName(String url,String symbol){
		String fileName   = url.substring(url.lastIndexOf(symbol) + 1,url.lastIndexOf("."));
		String expandName = url.substring(url.lastIndexOf(".") + 1,url.length()).toLowerCase();

		return fileName+"."+expandName;
	}

	/**
	 * 获取完整http的URL中不含扩展名的文件名
	 * @param url 	    完整http的URL
	 * @param symbol  url中路径是正斜杠“/”或反斜杠“\”或者本地路径的"\\"
	 */
	public String fileName(String url,String symbol){
		String fileName   = url.substring(url.lastIndexOf(symbol) + 1,url.lastIndexOf("."));
		return fileName;
	}

	/**
	 * 获取完整http的URL中文件的扩展名
	 * @param url 	    完整http的URL
	 * @param symbol  url中路径是正斜杠“/”或反斜杠“\”或者本地路径的"\\"
	 */
	public String expandName(String url){
		String expandName = url.substring(url.lastIndexOf(".") + 1,url.length()).toLowerCase();
		return expandName;
	}
}
