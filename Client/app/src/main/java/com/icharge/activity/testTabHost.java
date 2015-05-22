package com.icharge.activity;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/4/2.
 */

public class testTabHost extends TabActivity {

    private TabHost my_tabhost;
    private TabWidget my_tabwidget;
    private int i,k;
    private TextView tv;

    private String[] tabMenu = { "系统", "硬件", "操作"};
    private Intent intent0, intent1, intent2;
    private Intent[] intents = { intent0, intent1, intent2};
    private TabHost.TabSpec tabSpec0, tabSpec1, tabSpec2, tabSpec3;
    private TabHost.TabSpec[] tabSpecs = { tabSpec0, tabSpec1, tabSpec2, tabSpec3};

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.test_tabhost);


        my_tabhost = getTabHost();

        intent0 = new Intent(this, testActivity.class);
        intent1 = new Intent(this, testActivity2.class);
        intent2 = new Intent(this, testActivity2.class);

        tabSpec0 = my_tabhost.newTabSpec("system").setIndicator(tabMenu[0],null).
                setContent(intent0);
        tabSpec1 = my_tabhost.newTabSpec("hardware").setIndicator(tabMenu[1],null).
                setContent(intent1);
        tabSpec2 = my_tabhost.newTabSpec("operation").setIndicator(tabMenu[2],null).
                setContent(intent2);

        my_tabhost.addTab(tabSpec1);
        my_tabhost.addTab(tabSpec0);
        my_tabhost.addTab(tabSpec2);

        my_tabhost.setCurrentTab(1);

    }



}
