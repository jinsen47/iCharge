package com.icharge.api;

import com.icharge.beans.KnowListBean;
import com.icharge.beans.LocationListBean;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Jinsen on 15/5/23.
 */
public interface IChargeServerAPI {

    @GET("/api/articles")
    void getArticles(Callback<KnowListBean> response);

    @GET("/api/chargers/{city}")
    void getChargers(@Path("city") String city, Callback<LocationListBean> response);
}
