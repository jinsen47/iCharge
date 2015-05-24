package com.icharge.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.icharge.activity.R;

/**
 * Created by Jinsen on 15/5/23.
 */
public class ProfileFragment extends Fragment {

    private RelativeLayout person_info;
    private RelativeLayout person_car;
    private RelativeLayout person_order;
    private RelativeLayout iwill_bulid;
    private RelativeLayout person_share;
    private RelativeLayout person_ac_order;
    public View.OnClickListener onclicklistener;

    public static ProfileFragment newInstance(){
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_profile, container, false);
        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
    }

    private void initView() {

        person_info = (RelativeLayout) getView().findViewById(R.id.person_info_detail);
        person_car = (RelativeLayout) getView().findViewById(R.id.person_car);
        person_order = (RelativeLayout) getView().findViewById(R.id.person_order);
        iwill_bulid = (RelativeLayout) getView().findViewById(R.id.iwill_bulid);
        person_share = (RelativeLayout) getView().findViewById(R.id.person_share);
        person_ac_order = (RelativeLayout) getView().findViewById(R.id.person_ac_order);
    }

    private void setListener() {

        onclicklistener = new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.person_info_detail:
                        Intent person_intent = new Intent();
                        person_intent.setClass(getActivity(), MyInfoActivity.class);
                        startActivity(person_intent);
                        break;
                    case R.id.person_car:
                        Intent car_intent = new Intent();
                        car_intent.setClass(getActivity(), MyCarActivity.class);
                        startActivity(car_intent);
                        break;
                    case R.id.person_order:
                        Intent order_intent = new Intent();
                        order_intent.setClass(getActivity(), MyOrderActivtiy.class);
                        startActivity(order_intent);
                        break;
                    case R.id.iwill_bulid:
                        Intent bulid_intent = new Intent();
                        bulid_intent.setClass(getActivity(), MyBuildChargeActivity.class);
                        startActivity(bulid_intent);
                        break;
                    case R.id.person_share:
                        Intent share_intent = new Intent();
                        share_intent.setClass(getActivity(), MyShareActivity.class);
                        startActivity(share_intent);
                        break;
                    case R.id.person_ac_order:
                        Intent ac_order = new Intent();
                        ac_order.setClass(getActivity(), MyAcceptOrderActivity.class);
                        startActivity(ac_order);
                        break;

                }
            }
        };
        person_info.setOnClickListener(onclicklistener);
        person_car.setOnClickListener(onclicklistener);
        person_order.setOnClickListener(onclicklistener);
        iwill_bulid.setOnClickListener(onclicklistener);
        person_share.setOnClickListener(onclicklistener);
        person_ac_order.setOnClickListener(onclicklistener);
    }
}
