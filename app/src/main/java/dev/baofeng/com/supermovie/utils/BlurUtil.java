package dev.baofeng.com.supermovie.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.glide.transformations.internal.FastBlur;

/**
 * Created by huangyong on 2018/2/11.
 */

public class BlurUtil {
    public static void setViewBg(float blur, float scaleFactor, View view, Bitmap src){
        Bitmap bitmap = src;
        //创建一个长宽等比缩小的Bitmap
        int bitmap_width = bitmap.getWidth();
        int bitmap_hight = bitmap.getHeight();
        Bitmap overlay = Bitmap.createBitmap((int) (bitmap_width / scaleFactor), (int) (bitmap_hight / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        //将canvas按照bitmap等量缩放，模糊处理的图片才能显示正常
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //对采样后的bitmap进行模糊处理，缩放采样后的图片处理比原图处理要省很多时间和内存开销
        overlay = dev.baofeng.com.supermovie.utils.FastBlur.doBlur(overlay, (int) blur, false);
        //模糊处理后的图片设置为头部布局背景图
        BitmapDrawable drawable = new BitmapDrawable(overlay);
        view.setBackground(drawable);
    }
}
