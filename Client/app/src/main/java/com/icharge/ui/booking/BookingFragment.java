package com.icharge.ui.booking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icharge.activity.R;

/**
 * Created by Jinsen on 15/5/23.
 */
public class BookingFragment extends Fragment{

    public static BookingFragment newInstance() {
        BookingFragment fragment = new BookingFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_booking, container, false);

        return rootview;
    }
}
