package dev.baofeng.com.supermovie.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import byc.imagewatcher.ImageWatcher;
import dev.baofeng.com.supermovie.R;

/**
 * creator huangyong
 * createTime 2019/2/19 下午11:07
 * path dev.baofeng.com.supermovie.view.widget
 * description:
 */
public class GlideSimpleLoader implements ImageWatcher.Loader {
    @Override
    public void load(Context context, Uri uri, final ImageWatcher.LoadCallback lc) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_dl_magnet_place_holder);
        Glide.with(context).load(uri).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                lc.onResourceReady(resource);
            }
        });
    }
}
