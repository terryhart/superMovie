package dev.baofeng.com.supermovie.utils.imgload;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * creator huangyong
 * createTime 2019/2/22 下午2:58
 * path dev.baofeng.com.supermovie.utils.imgload
 * description:
 */
public class GlideImageLoader {


    private static RequestOptions mOptionsForSizeIgnore;

    public static void displayIgnoreSize(Context context, Uri uri, ImageView imageView, int ignorwidth, int ignoreheight) {

        mOptionsForSizeIgnore = new RequestOptions()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontTransform()
                .dontAnimate()
                .downsample(DownsampleStrategy.NONE); // Replacement for asIs()
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(mOptionsForSizeIgnore.override(ignorwidth, ignoreheight))
                .into(imageView);
    }

    public static void displayWithRadius(Context context, Uri uri, int radius, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        //加入圆角变换
        requestOptions.transform(new RoundedCornersTransformation(context, radius, 0));
        Glide.with(context)
                .load(uri)
                .apply(requestOptions)
                .into(imageView);
    }
}
