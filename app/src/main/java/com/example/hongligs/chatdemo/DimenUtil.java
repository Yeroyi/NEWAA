package com.example.hongligs.chatdemo;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DimenUtil {
    private static DisplayMetrics sDisplayMetrics;
    private static int sScreenWidthInPixel;
    private static int sScreenHeightInPixel;
    private static boolean sInitialized;

    public DimenUtil() {
    }

    private static void init(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        sDisplayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(sDisplayMetrics);
        sScreenWidthInPixel = sDisplayMetrics.widthPixels;
        sScreenHeightInPixel = sDisplayMetrics.heightPixels;
    }

    private static void checkInit(Context context) {
        if (!sInitialized) {
            init(context);
            sInitialized = true;
        }

    }

    public static float getScreenDensity(Context context) {
        checkInit(context);
        return sDisplayMetrics.density;
    }

    public static int getScreenWidthInPixel(Context context) {
        checkInit(context);
        return sScreenWidthInPixel;
    }

    public static int getScreenHeightInPixel(Context context) {
        checkInit(context);
        return sScreenHeightInPixel;
    }

    public static float dp2Px(Context context, float dpValue) {
        checkInit(context);
        return TypedValue.applyDimension(1, dpValue, sDisplayMetrics);
    }

    public static float sp2Px(Context context, float spValue) {
        checkInit(context);
        return TypedValue.applyDimension(2, spValue, sDisplayMetrics);
    }

    public static int px2dp(Context context, float pxValue) {
        checkInit(context);
        return (int) (pxValue / sDisplayMetrics.density + 0.5F);
    }

    public static int px2sp(Context context, float pxValue) {
        checkInit(context);
        return (int) (pxValue / sDisplayMetrics.scaledDensity + 0.5F);
    }
}
