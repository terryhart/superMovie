package dev.baofeng.com.supermovie.https;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import okhttp3.OkHttpClient;


/**
 * author           Alpha58
 * time             2017/1/5 22:57
 * desc	            ${TODO}
 * <p>
 * upDateAuthor     $Author$
 * upDate           $Date$
 * upDateDesc       ${TODO}
 */
public class OkHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull GlideUrl glideUrl, int width, int height, @NonNull Options options) {
        return null;
    }

    @Override
    public boolean handles(@NonNull GlideUrl glideUrl) {
        return false;
    }


}
