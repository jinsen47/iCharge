package com.icharge.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.icharge.ChargeApp;

/**
 * Created by Jinsen on 15/5/22.
 */
public class RequestProvider {
    private static RequestQueue requestQueue = null;
    private RequestProvider(){}

    public static RequestQueue getRequestQueue() {
        if (requestQueue != null) {
            return requestQueue;
        } else {
            requestQueue = Volley.newRequestQueue(ChargeApp.getAppContext());
            return requestQueue;
        }
    }
}
