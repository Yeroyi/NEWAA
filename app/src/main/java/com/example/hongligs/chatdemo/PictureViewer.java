package com.example.hongligs.chatdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ImageView;

import com.example.hongligs.R;
import com.github.ielse.imagewatcher.ImageWatcherHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PictureViewer implements Application.ActivityLifecycleCallbacks {

    private static PictureViewer sInstance;

    private PictureViewer() {

    }

    public static SparseArray<ImageView> createSingleImageGroupList(ImageView imageView) {
        SparseArray<ImageView> imageViews = new SparseArray<>();
        imageViews.put(0, imageView);
        return imageViews;
    }


    public static synchronized PictureViewer getInstance() {
        if (sInstance == null) {
            sInstance = new PictureViewer();
        }
        return sInstance;
    }

    private ConcurrentHashMap<Activity, ImageWatcherHelper> mImageWatcherInstances = new ConcurrentHashMap<>();

    public void open(Context context, List<String> pictures, ImageView from, SparseArray<ImageView> imageGroupList) {
        if (!(context instanceof Activity)) {
            throw new IllegalArgumentException("You cannot use ImageWatcher without FragmentActivity");
        } else {
            ImageWatcherHelper imageWatcherHelper = mImageWatcherInstances.get(context);
            if (imageWatcherHelper == null) {
                imageWatcherHelper = ImageWatcherHelper.with((Activity) context, GlideSimpleLoader.instance())
                        .setErrorImageRes(R.drawable.ic_weibo_error);
                mImageWatcherInstances.put((Activity) context, imageWatcherHelper);
            }
            ArrayList<Uri> finalList = new ArrayList<>();
            for (String url : pictures) {
                finalList.add(Uri.parse(url));
            }
            imageWatcherHelper.show(from, imageGroupList, finalList);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        mImageWatcherInstances.remove(activity);
    }

    public boolean onBackPressed() {
        for (ImageWatcherHelper imageWatcherHelper : mImageWatcherInstances.values()) {
            if (imageWatcherHelper.handleBackPressed()) {
                return true;
            }
        }
        return false;
    }
}
