package com.icharge.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Created by emosfet on 2015/4/25.
 */
public class PopDetailActivity extends Activity{

    private Button btn_jmp_navi, btn_jmp_detail;
    private TextView text_information;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupdetail);

        btn_jmp_navi = (Button) this.findViewById(R.id.jmp_navi);
        btn_jmp_detail = (Button) this.findViewById(R.id.jmp_detail);
        text_information = (TextView)findViewById(R.id.information);
        layout=(LinearLayout)findViewById(R.id.pop_layout);

        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        final String station_name = bundle.getString("station_name");
        final double station_latitude = bundle.getDouble("station_latitude");
        final double station_longitude = bundle.getDouble("station_longitude");
        final double my_latitude = bundle.getDouble("my_latitude");
        final double my_longitude = bundle.getDouble("my_longitude");
        text_information.setText(station_name);


        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });


        btn_jmp_navi.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(PopDetailActivity.this,RouteGuideActivity.class);
                Bundle bundle=new Bundle();
                //传递name参数为tinyphp

                bundle.putDouble("src_latitude",my_latitude);
                bundle.putDouble("src_longitude",my_longitude);

                bundle.putString("dest_name", station_name);
                bundle.putDouble("dest_latitude",station_latitude);
                bundle.putDouble("dest_longitude",station_longitude);

                intent.putExtras(bundle);

                startActivity(intent);
                finish();

            }
        });
        btn_jmp_detail.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }
    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity

}
