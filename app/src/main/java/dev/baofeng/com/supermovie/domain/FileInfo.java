package dev.baofeng.com.supermovie.domain;

import java.io.Serializable;
/**
 * 下载的文件的所有属性信息
 */
public class FileInfo  implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 下载文件的id
	 */
	private int id ;
	/**
	 * 下载文件总长度
	 */
	private int length ;
	/**
	 * 下载文件的web路径
	 */
	private String url ;
	/**
	 * 下载文件名
	 */
	private String  fileName ;
	/**
	 * 下载文件的 MD5值
	 */
	private String  md5 ;
	/**
	 * 文件下载进度（%）
	 */
	private double finished ;
	/**
	 * 文件下载速率
	 */
	private double rate ;

	/**
	 * 下载文件的 MD5值
	 */
	private String  over ;
	/**
	 * 下载文件完成下载的时间
	 */
	private String  overtime ;


	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	/**
	 * 下载文件是否正在下载
	 */
	private String  isDownload ;
	//---------------------------
	public FileInfo() {
	}

	/**
	 * 存储下载文件的属性信息
	 * @param id		下载文件的id标识
	 * @param length	文件总长度
	 * @param url		文件web路径
	 * @param fileName  下载文件名
	 * @param finished  文件下载进度
	 * @param rate      文件下载速率
	 * @param md5              文件的md5值
	 * @param over      文件是否完成下载
	 * @param -isDownload文件是否正在下载
	 */
	public FileInfo(int id, int length, String url, String fileName,
					int finished,double rate, String md5, String over, String  isDownload, String overtime) {
		this.id = id;
		this.url = url;
		this.length = length;
		this.fileName = fileName;
		this.finished = finished;
		this.rate = rate;
		this.md5 = md5;
		this.over = over ;
		this.overtime = overtime ;
		this.isDownload = isDownload ;
	}

	/**
	 * 获取文件下载状态
	 */
	public String getIsDownload() {
		return isDownload;
	}
	/**
	 * 设置文件下载状态
	 * @param -isDownload文件是否正在下载
	 */
	public void setIsDownload(String isDownload) {
		this.isDownload = isDownload;
	}

	//---------------------------
	/**
	 * 获取文件下载进度（%）
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * 存储文件下载进度（%）
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}


	/**
	 * 获取文件MD5
	 */
	public String getMd5() {
		return md5;
	}
	/**
	 * 存储文件MD5
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
	}


	/**
	 * 获取下载文件的id标识
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置下载文件的
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * 获取下载文件的长度
	 */
	public int getLength() {
		return length;
	}
	/**
	 * 存入下载文件的
	 */
	public void setLength(int length) {
		this.length = length;
	}


	/**
	 * 获取下载文件的URL路径
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 存储下载文件的URL路径
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * 获取下载文件的文件名
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 存储下载文件的文件名
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * 获取下载文件的下载进度
	 */
	public double getFinished() {
		return finished;
	}
	/**
	 * 存储下载文件的存储进度
	 */
	public void setFinished(double finished) {
		this.finished = finished;
	}


	/**
	 * 获取文件完成下载标识
	 */
	public String getOver() {
		return over;
	}
	/**
	 * 存储文件完成下载标识
	 */
	public void setOver(String over) {
		this.over = over;
	}

	/**
	 * 获取下载文件的所有属性信息
	 */
	@Override
	public String toString() {
		return "FileInfo [id=" + id + ", length=" + length + ", url=" + url
				+ ", fileName=" + fileName + ", finished="
				+ finished +", rate=" + rate +", md5=" + md5 + "]";
	}



}
