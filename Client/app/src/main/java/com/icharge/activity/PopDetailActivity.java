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
import android.util.Log;
import java.io.File;
import java.net.URISyntaxException;
import android.widget.Toast;

import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams;

/**
 * Created by emosfet on 2015/4/25.
 */
public class PopDetailActivity extends Activity{

    private Button btn_jmp_navi, btn_jmp_detail;
    private TextView text_information;
    private TextView mTitle;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupdetail);

        btn_jmp_navi = (Button) this.findViewById(R.id.jmp_navi);
        btn_jmp_detail = (Button) this.findViewById(R.id.jmp_detail);
        text_information = (TextView)findViewById(R.id.information);
        layout=(LinearLayout)findViewById(R.id.pop_layout);
        mTitle = ((TextView) findViewById(R.id.title));

        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        final String station_name = bundle.getString("station_name");
        final double station_latitude = bundle.getDouble("station_latitude");
        final double station_longitude = bundle.getDouble("station_longitude");
        final double my_latitude = bundle.getDouble("my_latitude");
        final double my_longitude = bundle.getDouble("my_longitude");
        final String station_loc = bundle.getString("staticon_loc");
        text_information.setText(station_loc);
        mTitle.setText(station_name);



        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });


        btn_jmp_navi.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub

                launchNavigator2(my_latitude, my_longitude,station_name, station_latitude, station_longitude);
                /*
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
                */
                finish();

            }
        });
        btn_jmp_detail.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    Intent intent;
                    String temp="intent://map/direction?origin=latlng:"+Double.toString(my_latitude)+","+Double.toString(my_longitude)+"|name:我家&destination="+station_name+"&mode=driving®ion=杭州&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
                    //intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&referer=Autohome|GasStation#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                    intent = Intent.getIntent(temp);
                    if(isInstallByread("com.baidu.BaiduMap")){
                        startActivity(intent); //启动调用
                        Log.e("GasStation", "百度地图客户端已经安装") ;
                    }else{
                        Log.e("GasStation", "没有安装百度地图客户端") ;
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
    * 判断是否安装目标应用
    * @param packageName 目标应用安装后的包名
    * @return 是否已安装目标应用
    */
    private boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    private void launchNavigator2(
            double my_latitude,double my_longitude,String dest_name, double dest_latitude,double dest_longitude){
        //这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
        BNaviPoint startPoint = new BNaviPoint(my_longitude,my_latitude,
                "我的位置", BNaviPoint.CoordinateType.BD09_MC);
        BNaviPoint endPoint = new BNaviPoint(dest_longitude,dest_latitude,
                dest_name, BNaviPoint.CoordinateType.BD09_MC);
        BaiduNaviManager.getInstance().launchNavigator(this,
                startPoint,                                      //起点（可指定坐标系）
                endPoint,                                        //终点（可指定坐标系）
                RoutePlanParams.NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME,       //算路方式
                true,                                            //真实导航
                BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略
                new BaiduNaviManager.OnStartNavigationListener() {                //跳转监听

                    @Override
                    public void onJumpToNavigator(Bundle configParams) {
                        Intent intent = new Intent(PopDetailActivity.this, NavigatorActivity.class);
                        intent.putExtras(configParams);
                        startActivity(intent);
                    }

                    @Override
                    public void onJumpToDownloader() {
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
