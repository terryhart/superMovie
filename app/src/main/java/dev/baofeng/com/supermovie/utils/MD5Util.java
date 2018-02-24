package dev.baofeng.com.supermovie.utils;

import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import dev.baofeng.com.supermovie.domain.FileInfo;
import dev.baofeng.com.supermovie.domain.ThreadInfo;

/**
 * 获取文件的md5值
 */
public class MD5Util {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static{
		try{
			messagedigest = MessageDigest.getInstance("MD5");
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件的md5值
	 * @param file 文件位置
	 */
	public String getFileMD5String( File file ) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		messagedigest.update(byteBuffer);
		in.close();
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}


	/**
	 * 比较本地下载后文件的md5值与验证值是否相同
	 * @param fileInfo 下载的文件
	 * @return 当md5一致时返回true；否则返回false
	 */
	public boolean isMD5Equal(FileInfo fileInfo){
		boolean isEqual = false ;
		String md5 = "" ;
		URLTools urlTools = new URLTools();
		MD5Util md5Util = new MD5Util();
		String name = urlTools.getURLFileName(fileInfo.getUrl(), "/");
		File file = new File( DownloadConfig.DOWNLOAD_PATH + name);
		try {
			md5 = md5Util.getFileMD5String(file);
			Log.i("md5", "MD5Util-81行  "+fileInfo.getUrl()+"  【生成】的md5值为：    "+ md5);
			if (fileInfo.getMd5().equals(md5)) {
				isEqual = true ;
			}else {
				isEqual = false ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isEqual;
	}

	/**
	 * 比较本地下载后文件的md5值与验证值是否相同
	 * @param threadInfo 文件下载线程信息
	 * @return 当md5一致时返回true；否则返回false
	 */
	public boolean isMD5Equal(ThreadInfo threadInfo){
		boolean isEqual = false ;
		String md5 = "" ;
		URLTools urlTools = new URLTools();
		MD5Util md5Util = new MD5Util();
		String name = urlTools.getURLFileName(threadInfo.getUrl(), "/");
		File file = new File( DownloadConfig.DOWNLOAD_PATH + name);
		try {
			md5 = md5Util.getFileMD5String(file);
			Log.i("md5", "MD5Util-107行  "+threadInfo.getUrl()+"  【生成】的md5值为：    "+ md5);
			if( threadInfo.getMd5().equals(md5) ){
				isEqual = true ;
			}else {
				isEqual = false ;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isEqual;
	}

}