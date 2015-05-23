package com.icharge.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import com.icharge.ChargeApp;

/**
 * Created by Jinsen on 15/5/24.
 */
public class ScreenHelper {
    private static final int WIDTH = 1;
    private static final int HEIGHT = 2;

    public static int getScreenWidth(Activity activity) {
        return getScreenParams(WIDTH, activity);
    }

    public static int getScreenHeight(Activity activity) {
        return getScreenParams(HEIGHT, activity);
    }

    private static int getScreenParams(int type, Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        } else {
            // TODO this is not correct, but we don't really care pre-kitkat
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        }
        if (type == WIDTH) {
            float width = metrics.widthPixels;
            Log.d("ScreenHelper", "Screen width = " + width);
            return (int) width;
        }

        if (type == HEIGHT) {
            float height = metrics.heightPixels;
            Log.d("ScreenHelper", "Screen height = " + height);
            return (int) height;
        }
        return 0;
    }

}
