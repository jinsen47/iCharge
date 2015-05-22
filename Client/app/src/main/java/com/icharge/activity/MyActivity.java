package com.icharge.activity;

import android.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.map.BaiduMap.*;
import com.baidu.mapapi.model.LatLng;

//import com.example.test.LocationApplication;
//定位所需jar包
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.location.LocationClientOption.LocationMode;

import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.ui.routeguide.subview.L;
import com.baidu.navisdk.util.verify.BNKeyVerifyListener;


public class MyActivity extends Activity implements
        OnGetGeoCoderResultListener ,OnGetPoiSearchResultListener{

    public final static int mapLocation =1;
    public static BaiduMap mBaiduMap = null;
    private MapView mMapView = null;
    private ZoomControlsView zcvZomm;//缩放控件
    private static GeoCoder mSearch = null; // 搜索模块
    private PoiSearch mPoiSearch = null;

    private Button btn_start_loc = null;
    private Button btn_search_sta = null;
    private Button btn_start_search = null;
    private Button btn_zoom_in = null;
    private Button btn_zoom_out = null;


    private static LocationClient mLocationClient;
    private TextView LocationResult;
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor="bd09ll";

    private static Marker marker_self = null;
    private Marker[] marker_charge_station = new Marker[5];
    private PoiOverlay search_overlay = null;

    private int count_charge_station = 0;
    private int num_charge_station=0;
    private boolean charge_station_found = false;
    private boolean mIsEngineInitSuccess = false;

    static private double my_latitude=0;
    static private double my_longitude=0;
    String[] GeoCodeKey = new String[64];
    private int load_Index = 0;

    //导航初始化函数
    private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
        public void engineInitSuccess() {
            mIsEngineInitSuccess = true;
        }
        public void engineInitStart() {
        }
        public void engineInitFail() {
        }
    };

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    public static Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case mapLocation:
                    //完成主界面更新,拿到数据
                    LatLng data = (LatLng)msg.obj;

                    if (marker_self!=null){
                        marker_self.remove();
                    }

                    my_latitude=data.latitude;
                    my_longitude=data.longitude;

                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_myself);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(data)
                            .icon(bitmap);
                    //在地图上添加Marker，并显示
                    marker_self =(Marker) mBaiduMap.addOverlay(option);

                    //定义地图状态
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(data)
                            .zoom(12)
                            .build();
                    //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                    MapStatusUpdate mMapStatusUpdate;
                    //mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mMapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(data,14);
                    //改变地图状态
                    mBaiduMap.animateMapStatus(mMapStatusUpdate);
                    mLocationClient.stop();


                    break;
                default:
                    break;
            }
        }

    };
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        //导航初始化同时校验key
        BaiduNaviManager.getInstance().initEngine(this, getSdcardDir(),
                mNaviEngineInitListener, new LBSAuthManagerListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        String str = null;
                        if (0 == status) {
                            str = "key校验成功!";
                        } else {
                            str = "key校验失败, " + msg;
                        }
                        Toast.makeText(MyActivity.this, str,
                                Toast.LENGTH_LONG).show();
                    }
                });

        //定位服务客户端
        mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);//隐藏缩放控件
        mBaiduMap.setBuildingsEnabled(true);//设置显示楼体

        //zcvZomm=(ZoomControlsView) findViewById(R.id.zcv_zoom);
        //zcvZomm.setMapView(mMapView);

        GeoCodeKey[0]= "北京市昌平区回龙观欧德宝汽车城（瑞风）专卖店";
        GeoCodeKey[1]= "北京市海淀区中关村东路1号";
        GeoCodeKey[2]= "北京市海淀区学院路37号北京航空航天大学天行网球馆";
        GeoCodeKey[3]= "朝阳区东大桥路9号";
        GeoCodeKey[4]= "朝阳区建国门日坛路秀水南街17号";

        InitLocation();//定位监听接口初始化
        InitSearchStation();//充电站定位初始化

        mLocationClient.start();

        //LocationResult = (TextView)findViewById(R.id.TestText);
        //((LocationApplication)getApplication()).mLocationResult = LocationResult;

        btn_start_loc = (Button)findViewById(R.id.start_loc);
        btn_search_sta = (Button)findViewById(R.id.search_station);
        btn_start_search = (Button)findViewById(R.id.start_search);
        btn_zoom_in = (Button)findViewById(R.id.zoom_in);
        btn_zoom_out = (Button)findViewById(R.id.zoom_out);

        btn_zoom_in.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                float zoomLevel = mBaiduMap.getMapStatus().zoom;
                if(zoomLevel<=19){
                    MapStatusUpdateFactory.zoomIn();
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomIn());
                    btn_zoom_out.setEnabled(true);
                }
                else{
                    Toast.makeText(MyActivity.this, "已经放至最大！", Toast.LENGTH_SHORT).show();
                    btn_zoom_in.setEnabled(false);
                }
            }
        });
        btn_zoom_out.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                float zoomLevel = mBaiduMap.getMapStatus().zoom;
                if(zoomLevel>4){
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.zoomOut());
                    btn_zoom_in.setEnabled(true);
                }
                else{
                    btn_zoom_out.setEnabled(false);
                    Toast.makeText(MyActivity.this, "已经缩至最小！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_start_loc.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mLocationClient.start();
            }
        });

        btn_search_sta.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //EditText editCity = (EditText) findViewById(R.id.city);
                //EditText editGeoCodeKey = (EditText) findViewById(R.id.geocodekey);
                String city = "北京";
                count_charge_station=0;

                // Geo搜索
                for(int i=0;i<5;i++){
                    // 初始化搜索模块，注册事件监听
                    mSearch = GeoCoder.newInstance();
                    mSearch.setOnGetGeoCodeResultListener(MyActivity.this);
                    // 发起搜索
                    mSearch.geocode(new GeoCodeOption().city(city).address(GeoCodeKey[i]));

                }
            }
        });

        //启动搜索界面
        btn_start_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(MyActivity.this, PoiSearchActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {

                if (marker == marker_self) {
                    String str = String.format("纬度：%f 经度：%f",
                            marker.getPosition().latitude, marker.getPosition().longitude);
                    Toast.makeText(MyActivity.this, str,
                            Toast.LENGTH_LONG).show();
                }
                else if (IsChargeStation(marker)) {
                    charge_station_found = false;
                    String str = String.format(GeoCodeKey[num_charge_station]);

                    Intent intent = new Intent(MyActivity.this, PopDetailActivity.class);
                    Bundle bundle = new Bundle();
                    //传递name参数为tinyphp
                    bundle.putString("station_name", str);
                    bundle.putDouble("station_latitude", marker.getPosition().latitude);
                    bundle.putDouble("station_longitude", marker.getPosition().longitude);
                    bundle.putDouble("my_latitude", my_latitude);
                    bundle.putDouble("my_longitude", my_longitude);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mSearch.destroy();
        mMapView.onDestroy();
        mPoiSearch.destroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    //地理编码请求结果返回
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MyActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if(count_charge_station%2==0){
            marker_charge_station[count_charge_station]=(Marker)mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.charge_slow_free)));
        }
        else{
            marker_charge_station[count_charge_station]=(Marker)mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.charge_slow_full)));
        }
        //mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));
        count_charge_station++;
    }

    //反地理编码请求结果返回，不需要所以没有写
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
    }

    public void onGetPoiResult(PoiResult result) {
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(MyActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            //mBaiduMap.clear();
            if (search_overlay!=null){
                search_overlay.removeFromMap();
                mBaiduMap.removeMarkerClickListener(search_overlay);
            }
            search_overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(search_overlay);
            search_overlay.setData(result);
            search_overlay.addToMap();
            search_overlay.zoomToSpan();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(MyActivity.this, strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(MyActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(MyActivity.this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }

    //定位初始化函数
    private void InitLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//设置定位模式
        option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
        int span=1000;
/*		try {
			span = Integer.valueOf(frequence.getText().toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
*/
        option.setScanSpan(span);//设置发起定位请求的间隔时间为1000ms
        option.setIsNeedAddress(false);
        mLocationClient.setLocOption(option);
    }

    private void InitSearchStation(){
    }

    private boolean IsChargeStation(Marker marker){
        for (int i = 0; i < marker_charge_station.length; i++) {
            if (marker == marker_charge_station[i]) {
                charge_station_found = true;
                num_charge_station = i;
                break;
            }
        }
        return charge_station_found;

    }

    /**
     * 为了得到传回的数据，必须在前一级Activity中重写onActivityResult方法
     * requestCode 请求码，即调用startActivityForResult()传递过去的值
     * resultCode 结果码，结果码用于标识返回数据来自哪个新Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode){
            case 2:
                String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
                Toast.makeText(MyActivity.this, result, Toast.LENGTH_LONG).show();

                //EditText editCity = (EditText) findViewById(R.id.city);
                String city = "北京";
                mPoiSearch.searchInCity((new PoiCitySearchOption())
                        .city(city)
                        .keyword(result)
                        .pageCapacity(1)
                        .pageNum(load_Index));
                break;
            default:
                break;
        }
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {

            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));

            // }
            return true;
        }
    }

}
