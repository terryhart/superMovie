package dev.baofeng.com.supermovie.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huangyong.downloadlib.utils.MD5Utils;

import java.util.HashMap;

/**
 * 对Bitmap统一管理，避免重复decode资源
 */
public class SharedBitmapManager {
    static final HashMap<Integer, Bitmap> cache = new HashMap();
    static final HashMap<String, Bitmap> cacheUrl = new HashMap();

    public static Bitmap obtainBitmap(Context context, int bitmapID) {
        Bitmap d = cache.get(bitmapID);
        if (d == null) {
            d = BitmapFactory.decodeResource(context.getResources(), bitmapID);
            cache.put(bitmapID, d);
        }
        return d;
    }

    public static Bitmap obtainBitmap(Context context,String url){
        String md5 = MD5Utils.stringToMD5(url);
        Bitmap d = cacheUrl.get(md5);
        if (d==null){
           Glide.with(context).load(url).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    cacheUrl.put(md5, resource);
                }
            });
            return cacheUrl.get(md5);
        }else {
            return d;
        }
    }

}
