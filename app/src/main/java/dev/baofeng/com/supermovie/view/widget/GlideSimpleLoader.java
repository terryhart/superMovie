package dev.baofeng.com.supermovie.view.widget;

import android.content.Context;
import android.net.Uri;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import byc.imagewatcher.ImageWatcher;

/**
 * creator huangyong
 * createTime 2019/2/19 下午11:07
 * path dev.baofeng.com.supermovie.view.widget
 * description:
 */
public class GlideSimpleLoader implements ImageWatcher.Loader {
    @Override
    public void load(Context context, Uri uri, final ImageWatcher.LoadCallback lc) {

        Glide.with(context).load(uri).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                lc.onResourceReady(resource);
            }
        });
    }
}
