package dev.baofeng.com.supermovie.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/8/20.
 */

public class ImageUtil {
    public static Bitmap getReverseBitmapById(int resId, Context context){
        Bitmap sourceBitmap= BitmapFactory.decodeResource(context.getResources(),resId);
        //绘制原图的下一半图片
        Matrix matrix=new Matrix();
        //倒影翻转
        matrix.setScale(1,-1);

        Bitmap inverseBitmap=Bitmap.createBitmap(sourceBitmap,0,sourceBitmap.getHeight()/2,sourceBitmap.getWidth(),sourceBitmap.getHeight()/3,matrix,false);
        //合成图片
        Bitmap groupbBitmap=Bitmap.createBitmap(sourceBitmap.getWidth(),sourceBitmap.getHeight()+sourceBitmap.getHeight()/3+60,sourceBitmap.getConfig());
        //以合成图片为画布
        Canvas gCanvas=new Canvas(groupbBitmap);
        //将原图和倒影图片画在合成图片上
        gCanvas.drawBitmap(sourceBitmap,0,0,null);
        gCanvas.drawBitmap(inverseBitmap,0,sourceBitmap.getHeight()+50,null);
        //添加遮罩
        Paint paint=new Paint();
        Shader.TileMode tileMode= Shader.TileMode.CLAMP;
        LinearGradient shader=new LinearGradient(0,sourceBitmap.getHeight()+50,0,
                groupbBitmap.getHeight(), Color.BLACK,Color.TRANSPARENT,tileMode);
        paint.setShader(shader);
        //这里取矩形渐变区和图片的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        gCanvas.drawRect(0,sourceBitmap.getHeight()+50,sourceBitmap.getWidth(),groupbBitmap.getHeight(),paint);
        return groupbBitmap;
    }


    /**
     * 截屏
     *
     * @return
     */
    public static Bitmap getScreen(View viewGroup) {
        Log.e("currentbitmap",viewGroup.getWidth()+"--"+viewGroup.getHeight());
        if (viewGroup.getWidth()==0){
            return null;
        }
        Bitmap bmp = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        int rowBytes = bmp.getRowBytes();
        bmp = null;

        if (rowBytes*viewGroup.getHeight()>=getAvailMemory()){
            return null;
        }
        bmp = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        viewGroup.draw(canvas);
        return bmp;
    }

    private static long getAvailMemory() {// 获取android当前可用内存大小
        return Runtime.getRuntime().maxMemory();
    }


    public static void layoutView(View v, int width, int height) {
        // validate view.width and view.height
        v.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED);
        // validate view.measurewidth and view.measureheight
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
    }


}
