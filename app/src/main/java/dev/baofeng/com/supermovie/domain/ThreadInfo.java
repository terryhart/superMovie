package dev.baofeng.com.supermovie.domain;

/**
 *文件下载线程的信息 
 **/
public class ThreadInfo {
	//-------------------------
	/**
	 * 文件下载线程的id标识
	 */
	private int id ;
	/**
	 * 下载文件的URL路径
	 */
	private String url ;
	/**
	 * 文件下载的起始字节位置
	 */
	private int start ;
	/**
	 * 文件下载的结束字节位置
	 */
	private int end ;
	/**
	 * 文件已下载的字节长度
	 */
	private int finished ;
	/**
	 * 下载文件的 MD5值
	 */
	private String  md5 ;
	/**
	 * 下载文件是否完成下载
	 */
	private String  over ;
	/**
	 * 下载文件完成下载的时间
	 */
	private String  overtime ;



	//---------------
	public ThreadInfo() {
	}

	/**
	 * 存储文件下载线程的属性信息
	 * @param id    文件下载线程id标识
	 * @param url	下载文件路径
	 * @param start 文件下载起始字节位置
	 * @param end   文件下载终止字节位置
	 * @param finished 文件已下载的字节长度
	 * @param md5            文件的md5值
	 * @param over     文件是否完成下载
	 * @param over_time文件完成下载的时间
	 */
	public ThreadInfo(int id, String url, int start, int end, int finished, String md5, String over,String  overtime) {
		this.id = id;
		this.url = url;
		this.start = start;
		this.end = end;
		this.finished = finished;
		this.md5 = md5;
		this.over = over;
		this.overtime = overtime;
	}

	/**
	 * 获取文件完成下载的时间
	 */
	public String getOvertime() {
		return overtime;
	}
	/**
	 * 设置文件完成下载的时间
	 */
	public void setOver_time(String overtime) {
		this.overtime = overtime;
	}

	//-----------------------------
	/**
	 * 获取文件下载线程的id标识
	 */
	public int getId() {
		return id;
	}
	/**
	 * 设置文件下载线程的id标识
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * 获取文件下载线程的url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置下载文件的url
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * 获取文件下载线程的起始字节位置
	 */
	public int getStart() {
		return start;
	}
	/**
	 * 设置下载文件的起始字节位置
	 */
	public void setStart(int start) {
		this.start = start;
	}


	/**
	 * 获取文件下载线程的结束字节位置
	 */
	public int getEnd() {
		return end;
	}
	/**
	 * 设置下载文件的结束字节位置
	 */
	public void setEnd(int end) {
		this.end = end;
	}


	/**
	 * 获取文件下载线程已下载的字节长度
	 */
	public int getFinished() {
		return finished;
	}
	/**
	 * 设置文件下载线程已下载的字节长度
	 */
	public void setFinished(int finished) {
		this.finished = finished;
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
	 * 获取文件下载线程的所有属性信息
	 */
	@Override
	public String toString() {
		return "ThreadInfo [id=" + id + ", url=" + url + ", start=" + start
				+ ", end =" + end + ", finished=" + finished +", md5=" + md5
				+ ", over=" + over +"]";
	}



}
