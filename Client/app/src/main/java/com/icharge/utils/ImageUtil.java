package com.icharge.utils;


import com.android.volley.cache.plus.SimpleImageLoader;

/**
 * Created by Jinsen on 15/5/5.
 */
public class ImageUtil {
    private static SimpleImageLoader mImageLoader;

    private ImageUtil(){}

    public static SimpleImageLoader getImageLoader() {
        if (mImageLoader != null) {
            return mImageLoader;
        } else {
            mImageLoader = new SimpleImageLoader(RequestProvider.getRequestQueue());
            return mImageLoader;
        }
    }

}
