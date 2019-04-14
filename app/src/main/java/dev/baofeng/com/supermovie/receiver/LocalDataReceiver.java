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
import com.huangyong.playerlib.PlayKey;

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
        if (intent.getAction().equals(PlayKey.HISTORY_SAVE)){
            //播放退出，保存观看信息
            String title =intent.getStringExtra(PlayKey.PLAY_TITLE_KEY);
            String path = intent.getStringExtra(PlayKey.PLAY_PATH_KEY);
            int originType = intent.getIntExtra(PlayKey.CENTENT_TYPE,0);
            String progress = intent.getStringExtra(PlayKey.MOVIE_PROGRESS);
            String urlMd5 = intent.getStringExtra(PlayKey.URL_MD5_KEY);
            String posterUrl = intent.getStringExtra(PlayKey.POSTER_IMG_KEY);
            HistoryDao dao = HistoryDao.getInstance(context);
            //更新本地数据,根据urlmd5去查询，理论上应该是唯一记录
            List<HistoryInfo> urlMd5Info = dao.queryForFeilds("urlMd5", urlMd5);
            if (urlMd5Info!=null&&urlMd5Info.size()>0){
                //更新数据
                urlMd5Info.get(0).setProgress(progress);
                urlMd5Info.get(0).setLocalPath(path);
                dao.updata(urlMd5Info.get(0));
            }else {
                //插入新数据
                HistoryInfo info = new HistoryInfo();
                info.setUrlMd5(urlMd5);
                info.setTitle(title);
                info.setPostImgUrl(posterUrl);
                info.setProgress(progress);
                info.setContent_type(String.valueOf(originType));
                info.setLocalPath(path);
                dao.add(info);

            }

        }
    }
}
