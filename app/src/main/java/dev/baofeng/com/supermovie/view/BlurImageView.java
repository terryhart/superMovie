package dev.baofeng.com.supermovie.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.huangyong.downloadlib.utils.BlurUtil;

import java.util.ArrayList;

import static android.graphics.Paint.FILTER_BITMAP_FLAG;


public class BlurImageView extends View {


    private Paint bitmapPaint;
    private Bitmap forgroundBitmap;
    private Bitmap blurBitmap;
    private RectF forRectF;
    private TextPaint textPaint;
    private String shortDescStr;
    private int baseLine;
    private RectF destRectF;

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
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,12,context.getResources().getDisplayMetrics()));

        baseLine = 320;
        forRectF = new RectF(0,0,0,0);
        destRectF = new RectF(0,0,0,0);
    }


    public void setImageUrl(String url){

    }
    public void setImageBitmap(Bitmap bitmap){
        this.forgroundBitmap = bitmap;
        if (forgroundBitmap!=null){
            this.blurBitmap = BlurUtil.getBlurBitmap(4, 4, forgroundBitmap);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (blurBitmap!=null){
            forRectF.right = getMeasuredWidth();
            forRectF.bottom = getMeasuredHeight();
            canvas.drawBitmap(blurBitmap,null,forRectF,bitmapPaint);
        }
        //canvas.scale(1.2f,1.2f,0,0);
//        if (forgroundBitmap!=null){
//            destRectF.right =320;
//            destRectF.bottom=460;
//
//            canvas.translate(getMeasuredWidth()*0.5f-forgroundBitmap.getWidth()*0.5f,140);
//            canvas.drawBitmap(forgroundBitmap,null,destRectF,bitmapPaint);
//
//        }

    }


}
