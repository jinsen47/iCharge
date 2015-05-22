package com.icharge.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
/**
 * Created by emosfet on 2015/4/16.
 */
public class RouteGuideActivity extends Activity{

    private BNaviPoint mStartPoint = new BNaviPoint(116.30142, 40.05087,
            "百度大厦", BNaviPoint.CoordinateType.GCJ02);
    private BNaviPoint mEndPoint = new BNaviPoint(116.39750, 39.90882,
            "北京天安门", BNaviPoint.CoordinateType.GCJ02);
    private List<BNaviPoint> mViaPoints = new ArrayList<BNaviPoint>();
    private Button btn_add_via;
    private Button btn_start_navigation;
    private Button btn_start_navigation2;
    private TextView text_src_node;
    private TextView text_dest_node;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_routeguide);

        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        final double src_latitude = bundle.getDouble("src_latitude");
        final double src_longitude = bundle.getDouble("src_longitude");

        final String dest_name = bundle.getString("dest_name");
        final double dest_latitude = bundle.getDouble("dest_latitude");
        final double dest_longitude = bundle.getDouble("dest_longitude");


        btn_start_navigation = (Button)findViewById(R.id.strat_navigation);
        btn_start_navigation2 = (Button)findViewById(R.id.strat_navigation2);
        btn_add_via = (Button) findViewById(R.id.add_via);
        text_src_node = (TextView)findViewById(R.id.src_node);
        text_dest_node = (TextView)findViewById(R.id.dest_node);

        text_dest_node.setText("终点： "+ dest_name);

        btn_start_navigation.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mViaPoints.size() == 0) {
                    launchNavigator(src_latitude, src_longitude,dest_name, dest_latitude, dest_longitude);
                } else {
                    launchNavigatorViaPoints();
                }
            }
        });

        btn_start_navigation2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mViaPoints.size() == 0) {
                    launchNavigator2(src_latitude, src_longitude,dest_name, dest_latitude, dest_longitude);
                } else {
                    launchNavigatorViaPoints();
                }
            }
        });


        btn_add_via.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                addViaPoint();
            }
        });
    }

    private void addViaPoint() {
        EditText viaXEditText = (EditText) findViewById(R.id.et_via_x);
        EditText viaYEditText = (EditText) findViewById(R.id.et_via_y);
        double latitude = 0, longitude = 0;
        try {
            latitude = Integer.parseInt(viaXEditText.getText().toString());
            longitude = Integer.parseInt(viaYEditText.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        // 默认坐标系为GCJ
        BNaviPoint viaPoint = new BNaviPoint(longitude/1e5, latitude/1e5,
                "途经点" + (mViaPoints.size()+1));
        mViaPoints.add(viaPoint);
        Toast.makeText(this, "已添加途经点：" + viaPoint.getName(),
                Toast.LENGTH_SHORT).show();
        if (mViaPoints.size() >= 3) {
            btn_add_via.setEnabled(false);
        }
    }

    /**
     * 启动GPS导航. 前置条件：导航引擎初始化成功
     */
    private void launchNavigator(
            double my_latitude,double my_longitude,String dest_name, double dest_latitude,double dest_longitude){
        //这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
        BaiduNaviManager.getInstance().launchNavigator(this,
                my_latitude, my_longitude,"我的位置",
                dest_latitude, dest_longitude,dest_name,
                NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, 		 //算路方式
                true, 									   		 //真实导航
                BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略
                new OnStartNavigationListener() {				 //跳转监听

                    @Override
                    public void onJumpToNavigator(Bundle configParams) {
                        Intent intent = new Intent(RouteGuideActivity.this, NavigatorActivity.class);
                        intent.putExtras(configParams);
                        startActivity(intent);
                    }

                    @Override
                    public void onJumpToDownloader() {
                    }
                });
    }

    /**
     * 指定导航起终点启动GPS导航.起终点可为多种类型坐标系的地理坐标。
     * 前置条件：导航引擎初始化成功
     */
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
                NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME,       //算路方式
                true,                                            //真实导航
                BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略
                new OnStartNavigationListener() {                //跳转监听

                    @Override
                    public void onJumpToNavigator(Bundle configParams) {
                        Intent intent = new Intent(RouteGuideActivity.this, NavigatorActivity.class);
                        intent.putExtras(configParams);
                        startActivity(intent);
                    }

                    @Override
                    public void onJumpToDownloader() {
                    }
                });
    }

    /**
     * 增加一个或多个途经点，启动GPS导航.
     * 前置条件：导航引擎初始化成功
     */
    private void launchNavigatorViaPoints(){
        //这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
//        BNaviPoint startPoint = new BNaviPoint(113.97348, 22.53951, "白石洲");
//        BNaviPoint endPoint   = new BNaviPoint(113.92576, 22.48876, "蛇口");
//        BNaviPoint viaPoint1 = new BNaviPoint(113.94104, 22.54343, "国人通信大厦");
//        BNaviPoint viaPoint2 = new BNaviPoint(113.94863, 22.53873, "中国银行科技园支行");
        List<BNaviPoint> points = new ArrayList<BNaviPoint>();
        points.add(mStartPoint);
        points.addAll(mViaPoints);
        points.add(mEndPoint);
        BaiduNaviManager.getInstance().launchNavigator(this,
                points,                                          //路线点列表
                NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME,       //算路方式
                true,                                            //真实导航
                BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略
                new OnStartNavigationListener() {                //跳转监听

                    @Override
                    public void onJumpToNavigator(Bundle configParams) {
                        Intent intent = new Intent(RouteGuideActivity.this, NavigatorActivity.class);
                        intent.putExtras(configParams);
                        startActivity(intent);
                    }

                    @Override
                    public void onJumpToDownloader() {
                    }
                });
    }
}
