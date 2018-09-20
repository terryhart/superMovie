package com.huangyong.downloadlib.view;
/**
 * Created by HuangYong on 2018/9/20.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

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

    private Paint paint;
    private RectF rectF;
    private int totalProgress;
    private int progress =0;
    private float rectWidth = 0;

    public ProgressImageView(Context context) {
        this(context,null);
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#3b07f7df"));
        paint.setStyle(Paint.Style.FILL);
        rectF = new RectF(0,0,rectWidth,0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        totalProgress = MeasureSpec.getSize(widthMeasureSpec);
        rectF.bottom =  MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(totalProgress, (int) rectF.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    public void setProgress(int progress){
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        this.rectWidth =  (this.progress*1.0f/100)*totalProgress;
        Log.e("zonggongchang",""+rectWidth);
        rectF.right = rectWidth;
        canvas.drawRoundRect(rectF,0,0,paint);
    }
}
