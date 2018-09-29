package dev.baofeng.com.supermovie.receiver;
/**
 * Created by HuangYong on 2018/9/29.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.huangyong.downloadlib.db.HistoryDao;
import com.huangyong.downloadlib.domain.HistoryInfo;
import com.huangyong.downloadlib.model.Params;

import java.util.List;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/29
 * @changeRecord [修改记录] <br/>
 * 2018/9/29 ：created
 */
public class LocalDataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Params.HISTORY_SAVE)){
            //播放退出，保存观看信息
            String title =intent.getStringExtra(Params.TASK_TITLE_KEY);
            String path = intent.getStringExtra(Params.LOCAL_PATH_KEY);
            String progress = intent.getStringExtra(Params.MOVIE_PROGRESS);
            String urlMd5 = intent.getStringExtra(Params.URL_MD5_KEY);
            Log.e("baocunjilu","baocunjilu"+progress);
            String posterUrl = intent.getStringExtra(Params.POST_IMG_KEY);
            HistoryDao dao = HistoryDao.getInstance(context);
            List<HistoryInfo> historyInfos = dao.queryAll();
            if (historyInfos!=null&&historyInfos.size()>0){
                //更新本地数据
                List<HistoryInfo> urlMd5Info = dao.queryForFeilds("urlMd5", urlMd5);
                if (urlMd5Info!=null&&urlMd5Info.size()>0){
                    //更新数据
                    urlMd5Info.get(0).setProgress(progress);
                    dao.updata(urlMd5Info.get(0));
                    Log.e("baocunjilu","更新数据baocunjilu");
                }else {
                    //插入新数据
                    HistoryInfo info = new HistoryInfo();
                    info.setUrlMd5(urlMd5);
                    info.setTitle(title);
                    info.setPostImgUrl(posterUrl);
                    info.setProgress(progress);
                    info.setLocalPath(path);
                    dao.add(info);
                    Log.e("baocunjilu","插入新电影数据baocunjilu");
                }
            }else {
                //插入新数据
                HistoryInfo info = new HistoryInfo();
                info.setUrlMd5(urlMd5);
                info.setTitle(title);
                info.setPostImgUrl(posterUrl);
                info.setProgress(progress);
                info.setLocalPath(path);
                dao.add(info);
                Log.e("baocunjilu","baocunjil插入新数据u");
            }

        }
    }
}
