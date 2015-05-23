package com.icharge.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.icharge.ui.booking.BookingFragment;
import com.icharge.ui.knowledge.KnowFragment;
import com.icharge.ui.map.MapFragment;
import com.icharge.ui.profile.ProfileFragment;

/**
 * Created by Jinsen on 15/5/23.
 */
public class MainAdapter extends FragmentPagerAdapter {
    private static final String TAG = MainAdapter.class.getSimpleName();
    private String[] TITLES = {"地图","预约","知识库","我的"};
    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.d(TAG, "0");
                return MapFragment.newInstance();
            case 1:
                Log.d(TAG, "1");
                return BookingFragment.newInstance();
            case 2:
                Log.d(TAG, "2");
                return KnowFragment.newInstance();
            case 3:
                Log.d(TAG, "3");
                return ProfileFragment.newInstance();
            default:
                Log.d(TAG, "Exception");
                return null;
        }
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}
