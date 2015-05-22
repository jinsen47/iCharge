package com.icharge.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2015/4/1.
 */
public class PersonInfoActivity  extends Activity {


    private LinearLayout person_info;
    private LinearLayout person_car;
    private LinearLayout person_order;
    private LinearLayout iwill_bulid;
    private LinearLayout person_share;
    private LinearLayout person_ac_order;
    public View.OnClickListener onclicklistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info);
        initView();
        setListener();


    }

    private void setListener() {

        onclicklistener =new View.OnClickListener() {
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.person_info_detail:
                        Intent person_intent= new Intent();
                        person_intent.setClass(PersonInfoActivity.this,MyInfoActivity.class);
                        startActivity(person_intent);
                        break;
                    case R.id.person_car:
                        Intent car_intent= new Intent();
                        car_intent.setClass(PersonInfoActivity.this,MyCarActivity.class);
                        startActivity(car_intent);
                        break;
                    case R.id.person_order:
                        Intent order_intent= new Intent();
                        order_intent.setClass(PersonInfoActivity.this,MyOrderActivtiy.class);
                        startActivity(order_intent);
                        break;
                    case R.id.iwill_bulid:
                        Intent bulid_intent= new Intent();
                        bulid_intent.setClass(PersonInfoActivity.this,MyBuildChargeActivity.class);
                        startActivity(bulid_intent);
                        break;
                    case R.id.person_share:
                        Intent share_intent= new Intent();
                        share_intent.setClass(PersonInfoActivity.this,MyShareActivity.class);
                        startActivity(share_intent);
                        break;
                    case R.id.person_ac_order:
                        Intent ac_order= new Intent();
                        ac_order.setClass(PersonInfoActivity.this,MyAcceptOrderActivity.class);
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

    private void initView() {

        person_info = (LinearLayout) findViewById(R.id.person_info_detail);
        person_car = (LinearLayout) findViewById(R.id.person_car);
        person_order = (LinearLayout) findViewById(R.id.person_order);
        iwill_bulid = (LinearLayout) findViewById(R.id.iwill_bulid);
        person_share = (LinearLayout) findViewById(R.id.person_share);
        person_ac_order = (LinearLayout) findViewById(R.id.person_ac_order);



    }


}
