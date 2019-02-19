package com.huangyong.downloadlib.view;
/**
 * Created by HuangYong on 2018/9/20.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huangyong.downloadlib.R;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/20
 * @changeRecord [修改记录] <br/>
 * 2018/9/20 ：created
 */
public class ProgressImageView extends AppCompatImageView {

    private Paint bitmapPaint;
    private Bitmap playBitmap;
    private boolean playIconHide=false;

    public void setFinished(boolean finished) {
        this.FINISHED = finished;
        invalidate();
    }

    private boolean FINISHED = false;
    private float start =0;
    private RectF test;

    public void setTaskStatu(boolean pause) {
        this.PAUSE = pause;
        invalidate();
    }

    private  boolean PAUSE = false;
    private Paint paint;
    private RectF rectF;
    private int totalProgress;
    private int progress =0;
    private float rectWidth = 0;
    private Bitmap pauseBitmap;
    private Bitmap startBitmap;
    private RectF dstRect;
    private int mHeight=0;
    private int mwidth=0;

    public ProgressImageView(Context context) {
        this(context,null);
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#9A272727"));
        paint.setStyle(Paint.Style.FILL);
        rectF = new RectF(start,0,0,0);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);




        pauseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.download_item_pause_icon_press_style2);
        startBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.download_item_resume_icon_press_style2);
//        pauseBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.download_item_retry_icon_press_style2);
        playBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.download_item_play_icon_press_style2);
        dstRect = new RectF(0,0,0,0);
        test = new RectF(0,0,130,130);


    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mwidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        totalProgress= mwidth;
        setMeasuredDimension(mwidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dstRect.left=mwidth/2-20;
        dstRect.top =mHeight/2-20;
        dstRect.right = mwidth/2+20;
        dstRect.bottom = mHeight+20;
    }

    public void setProgress(int progress){
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.start = (this.progress*1.0f/100)*totalProgress;
        Log.e("zonggongchang",mHeight+"--"+getMeasuredWidth());
        rectF.right = getMeasuredWidth();
        rectF.left = start;
        rectF.bottom = mHeight;
        canvas.drawRoundRect(rectF,0,0,paint);

        if (FINISHED&&!playIconHide){
            canvas.drawBitmap(playBitmap,getMeasuredWidth()/2-playBitmap.getWidth()/2,
                    getMeasuredHeight()/2-playBitmap.getHeight()/2,bitmapPaint);
        }else {
            if (PAUSE){
                canvas.drawBitmap(pauseBitmap,getMeasuredWidth()/2-pauseBitmap.getWidth()/2,
                        getMeasuredHeight()/2-pauseBitmap.getHeight()/2,bitmapPaint);
            }else {
                canvas.drawBitmap(startBitmap,getMeasuredWidth()/2-startBitmap.getWidth()/2,
                        getMeasuredHeight()/2-startBitmap.getHeight()/2,bitmapPaint);
            }
        }
    }

    public void setPlayIconHide(boolean b) {
        this.playIconHide = b;
        invalidate();
    }
}
