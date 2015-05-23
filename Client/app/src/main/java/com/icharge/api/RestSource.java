package com.icharge.api;

import android.location.Location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icharge.beans.KnowBean;
import com.icharge.beans.KnowListBean;
import com.icharge.beans.LocationBean;
import com.icharge.beans.LocationListBean;
import com.icharge.utils.BusProvider;
import com.icharge.utils.Constants;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.JacksonConverter;

/**
 * Created by Jinsen on 15/5/23.
 */
public class RestSource {
    public static RestSource mInstance;
    private final IChargeServerAPI mAPI;

    private RestSource() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.API_HOST)
                .setConverter(new JacksonConverter(new ObjectMapper()))
                .build();
        mAPI = restAdapter.create(IChargeServerAPI.class);
    }

    public static RestSource getInstance() {
        if (mInstance == null) {
            mInstance = new RestSource();
        }
        return mInstance;
    }

    public void getArticles() {
        mAPI.getArticles(articlesCallback);
    }

    public void getChargers(String city) {
        mAPI.getChargers(city, chargersCallback);
    }

    public Callback articlesCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {
            KnowListBean bean = ((KnowListBean) o);
            List<KnowBean> list = bean.getList();
            BusProvider.getDefaultBus().post(list);
        }

        @Override
        public void failure(RetrofitError error) {
            parseError(error);
        }

    };

    public Callback chargersCallback = new Callback() {
        @Override
        public void success(Object o, Response response) {
            LocationListBean bean = ((LocationListBean) o);
            List<LocationBean> list = bean.getList();
            BusProvider.getDefaultBus().post(list);
        }

        @Override
        public void failure(RetrofitError error) {
            parseError(error);
        }
    };

    private void parseError(RetrofitError error) {
        System.out.println("[DEBUG] RestSource failure - URL : " + error.getUrl());
        System.out.println("[DEBUG] RestSource failure - Message : " + error.getMessage());
    }
}
