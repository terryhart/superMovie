package dev.baofeng.com.supermovie.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import dev.baofeng.com.supermovie.R;
import dev.baofeng.com.supermovie.domain.MovieInfo;
import dev.baofeng.com.supermovie.utils.BlurUtil;

/**
 * Created by huangyong on 2018/2/11.
 */

public class VPadapter extends android.support.v4.view.PagerAdapter {
    private MovieInfo list;
    private Context context;

    public VPadapter(MovieInfo list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.getData().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.vp_item,null);
        ImageView vpImg = (ImageView) view.findViewById(R.id.vp_img);
        Glide.with(context).load(list.getData().get(position).getDownimgurl()).into(vpImg);
        container.addView(view);
        return view;
    }
    public interface getCurrentBitmap{
        void getBitmap(Bitmap bitmap);
    }
    private getCurrentBitmap listener;
    public void setOngetListener( getCurrentBitmap listener){
        this.listener = listener;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    public static Bitmap drawableToBitmap(Drawable drawable) {

        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);

        return bitmap;
    }
}
