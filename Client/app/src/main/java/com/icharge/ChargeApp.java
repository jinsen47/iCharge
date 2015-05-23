package com.icharge;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jinsen on 15/5/22.
 */
public class ChargeApp extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
}
