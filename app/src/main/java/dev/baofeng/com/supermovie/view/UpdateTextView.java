package dev.baofeng.com.supermovie.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.utils.PixUtil;

public class UpdateTextView extends android.support.v7.widget.AppCompatTextView {

    private Paint imgPaint;
    private Bitmap bitmap;
    private Rect imgRect;
    private float pixel;
    private float paddingTop;
    private boolean hasUpdate=false;

    public UpdateTextView(Context context) {
        this(context,null);
    }

    public UpdateTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UpdateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        pixel = PixUtil.convertDpToPixel(120, context);
        paddingTop = PixUtil.convertDpToPixel(14, context);
        imgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        imgPaint.setDither(true);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.conf_common_cell_new_icon);

        imgRect = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (hasUpdate){
            canvas.save();
            canvas.translate(pixel,paddingTop);
            canvas.drawBitmap(bitmap,null,imgRect,imgPaint);
            canvas.restore();
        }
    }

    public void setHasUpdate(boolean hasUpdate){
        this.hasUpdate = hasUpdate;
        invalidate();
    }
}
