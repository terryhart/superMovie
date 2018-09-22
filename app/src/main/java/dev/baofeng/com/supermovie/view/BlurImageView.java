package dev.baofeng.com.supermovie.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.huangyong.downloadlib.utils.BlurUtil;

import static android.graphics.Paint.FILTER_BITMAP_FLAG;


public class BlurImageView extends View {


    private Paint bitmapPaint;
    private Bitmap forgroundBitmap;
    private Bitmap blurBitmap;
    private RectF forRectF;

    public BlurImageView(Context context) {
        this(context,null);
    }

    public BlurImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BlurImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setFlags(FILTER_BITMAP_FLAG);
        forRectF = new RectF(0,0,0,0);
    }


    public void setImageUrl(String url){

    }
    public void setImageBitmap(Bitmap bitmap){
        this.forgroundBitmap = bitmap;
        this.blurBitmap = BlurUtil.getBlurBitmap(4, 4, forgroundBitmap);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (blurBitmap!=null){
            forRectF.right = getMeasuredWidth();
            forRectF.bottom = getMeasuredHeight();
            canvas.drawBitmap(blurBitmap,null,forRectF,bitmapPaint);
        }

        if (forgroundBitmap!=null){
            Log.e("bitmaparams",forgroundBitmap.getWidth()+"---"+forgroundBitmap.getHeight());
            Log.e("bitmaparams",getMeasuredWidth()+"---"+getMeasuredHeight());
            canvas.scale(1.2f,1.2f,0,getMeasuredHeight());
            canvas.drawBitmap(forgroundBitmap,100,
                    getMeasuredHeight()-forgroundBitmap.getHeight()-150,bitmapPaint);
        }
    }
}
