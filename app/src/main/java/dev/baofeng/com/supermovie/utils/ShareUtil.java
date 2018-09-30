package dev.baofeng.com.supermovie.utils;
/**
 * Created by HuangYong on 2018/9/30.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import dev.baofeng.com.supermovie.R;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @created 2018/9/30
 * @changeRecord [修改记录] <br/>
 * 2018/9/30 ：created
 */
public class ShareUtil {

    public static void share(Context context, int stringRes) {
        share(context, context.getString(stringRes));
    }

    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static void share(Context context, String extraText) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.action_share));
        intent.putExtra(Intent.EXTRA_TEXT, extraText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.action_share)));
    }
}
