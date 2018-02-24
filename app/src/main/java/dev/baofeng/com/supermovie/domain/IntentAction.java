package dev.baofeng.com.supermovie.domain;


/**
 * 存储Intent中Action的值类
 */
public class IntentAction {

	/**
	 * 设置intent中【打开已下载文件查看内容】的action
	 */
	public static final  String ACTION_OVER_LOOK ="ACTION_OVER_LOOK";
	/**
	 * 设置intent中【文件开始下载】的action
	 */
	public static final  String ACTION_START ="ACTION_START";
	/**
	 * 设置intent中【文件删除下载】的action
	 */
	public static final  String ACTION_DELETE ="ACTION_DELETE";
	/**
	 * 设置intent中【文件更新进度】的action
	 */
	public static final  String ACTION_UPDATE ="ACTION_UPDATE";
	/**
	 * 设置intent中【文件暂停下载】的action
	 */
	public static final  String ACTION_PAUSE ="ACTION_PAUSE";
	/**
	 * 设置intent中【文件下载结束】的action
	 */
	public static final  String ACTION_FINISH ="ACTION_FINISH";
	/**
	 * 设置intent中【文件下载结束】的action
	 */
	public static final  String ACTION_FINISH_FILE ="ACTION_FINISH_FILE";
}
