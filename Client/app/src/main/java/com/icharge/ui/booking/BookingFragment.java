package com.icharge.ui.booking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icharge.activity.R;
import com.icharge.api.RestSource;
import com.icharge.beans.LocationBean;
import com.icharge.beans.LocationListBean;
import com.icharge.utils.BusProvider;

import java.util.List;

/**
 * Created by Jinsen on 15/5/23.
 */
public class BookingFragment extends Fragment{
    private static final String TAG = BookingFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private List<LocationBean> mLocsList;
    private BookingAdapter mAdapter;

    private RestSource restSource;

    public static BookingFragment newInstance() {
        BookingFragment fragment = new BookingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_booking, container, false);
        restSource = RestSource.getInstance();
        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getDefaultBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getDefaultBus().unregister(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = ((RecyclerView) getView().findViewById(R.id.recyclerView));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        restSource.getChargers("杭州");
    }

    public void onEventMainThread(LocationListBean bean) {
        mLocsList = bean.getList();
        mAdapter = new BookingAdapter(getActivity(), mLocsList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
