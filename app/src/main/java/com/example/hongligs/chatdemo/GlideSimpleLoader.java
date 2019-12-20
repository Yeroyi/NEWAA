package com.example.hongligs.chatdemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.hongligs.R;
import com.github.ielse.imagewatcher.ImageWatcher;


public class GlideSimpleLoader implements ImageWatcher.Loader {

    private GlideSimpleLoader() {
    }

    private static GlideSimpleLoader sInstance;

    public static synchronized GlideSimpleLoader instance() {
        if (sInstance == null) {
            sInstance = new GlideSimpleLoader();
        }
        return sInstance;
    }

    @Override
    public void load(Context context, Uri uri, final ImageWatcher.LoadCallback loadCallback) {
        Glide.with(context).load(uri)
                .placeholder(R.drawable.ic_weibo_error)
                .error(R.drawable.ic_weibo_error)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        loadCallback.onResourceReady(resource);
                    }

                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {
                        loadCallback.onLoadStarted(placeholder);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        loadCallback.onLoadFailed(errorDrawable);
                    }
                });
    }
}
