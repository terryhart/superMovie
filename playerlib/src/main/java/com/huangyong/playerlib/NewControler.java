package com.huangyong.playerlib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.dueeeke.videocontroller.StandardVideoController;

/**
 * creator huangyong
 * createTime 2018/11/18 下午1:31
 * path com.huangyong.playerlib
 * description:
 */
public class NewControler extends StandardVideoController {


    private SpeedDialog speedView;
    private RectF rectF;
    private Paint rectPaint;
    private TextPaint textPaint;

    public NewControler(@NonNull Context context) {
        this(context,null);
    }

    public NewControler(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NewControler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //将全屏按钮隐藏并留出空白位置，好添加倍速调节按钮
        fullScreenButton.setImageResource(R.drawable.alpha_ba);

        rectF = new RectF(0,0,0,0);

        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(Color.WHITE);
        rectPaint.setDither(true);
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(2);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(30);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.WHITE);
        textPaint.setDither(true);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
         super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void show() {
        super.show();

    }

    @Override
    public void hide() {
        super.hide();

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        rectF.left=getMeasuredWidth()-120;
        rectF.top =getMeasuredHeight()-100;
        rectF.right = getMeasuredWidth()-20;
        rectF.bottom = getMeasuredHeight()-30;

        canvas.drawRoundRect(rectF,4,4,rectPaint);

        canvas.drawText("正常",getMeasuredWidth()-60,getMeasuredHeight()-54,textPaint);
    }
}
