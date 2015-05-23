package com.icharge.ui.profile;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.icharge.activity.R;

/**
 * Created by Administrator on 2015/4/25.
 */
public class MyBuildChargeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_build_charge);


    }
}
