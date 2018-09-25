package dev.baofeng.com.supermovie.view;
/**
 * Created by HuangYong on 2018/9/25.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import static android.graphics.Paint.FILTER_BITMAP_FLAG;

/**
 * @author Huangyong
 * @version 1.0
 * @title
 * @description
 * @company 北京奔流网络信息技术有线公司
 * @created 2018/9/25
 * @changeRecord [修改记录] <br/>
 * 2018/9/25 ：created
 */
public class PosterItemView extends AppCompatImageView {

    private Bitmap reverseBitmap;
    private Paint bitmapPaint;
    private TextPaint textPaint;
    private String mTitle;

    public PosterItemView(Context context) {
        this(context,null);
    }

    public PosterItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PosterItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setFlags(FILTER_BITMAP_FLAG);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);

    }

    public void setReverseImageBitmap(Bitmap bitmap){
        this.reverseBitmap = bitmap;
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (reverseBitmap!=null){
            canvas.drawBitmap(reverseBitmap,0,getMeasuredHeight(),bitmapPaint);
        }
        if (!TextUtils.isEmpty(mTitle)){
            canvas.drawText(mTitle,getMeasuredWidth()/2,getMeasuredHeight()+30,textPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top+50, right, bottom);
    }

    public void setTitleText(String downdtitle) {
        this.mTitle = downdtitle;
        invalidate();
    }
}
