package com.icharge.ui.map;

import com.icharge.ChargeApp;

import com.baidu.navisdk.BNaviEngineManager;
import com.icharge.activity.PoiSearchActivity;
import com.icharge.activity.PopDetailActivity;
import com.icharge.activity.ZoomControlsView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import com.icharge.activity.R;

import com.baidu.mapapi.map.*;
import com.baidu.mapapi.map.BaiduMap.*;
import com.baidu.mapapi.model.LatLng;

//import com.example.test.LocationApplication;
//定位所需jar包
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.location.LocationClientOption.LocationMode;

import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.navisdk.BaiduNaviManager;
import com.icharge.api.RestSource;
import com.icharge.beans.LocationBean;
import com.icharge.utils.BusProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jinsen on 15/5/23.
 */
public class MapFragment extends Fragment implements
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
    private LocationMode tempMode = LocationMode.Hight_Accuracy;
    private String tempcoor="bd09ll";

    private static Marker marker_self = null;
//    private Marker[] marker_charge_station = new Marker[5];
    private PoiOverlay search_overlay = null;

//    private int count_charge_station = 0;
    private int num_charge_station=0;
    private boolean charge_station_found = false;
    private boolean mIsEngineInitSuccess = false;

    static private double my_latitude=0;
    static private double my_longitude=0;
    static String city_all = null;
//    String[] GeoCodeKey = new String[64];
    private int load_Index = 0;

    private RestSource mRestSource;
    private List<LocationBean> mLocsList;
    private List<Marker> mMarkers;


    public MapFragment(){}

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    private BNaviEngineManager.NaviEngineInitListener mNaviEngineInitListener = new BNaviEngineManager.NaviEngineInitListener() {
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
                    //LatLng data = (LatLng)msg.obj;
                    String getdata =(String)msg.obj;
                    String[] data = getdata.split(" ");

                    if (marker_self!=null){
                        marker_self.remove();
                    }

                    my_latitude=Double.parseDouble(data[0]);
                    my_longitude=Double.parseDouble(data[1]);
                    city_all=data[2];

                    LatLng point = new LatLng(my_latitude,my_longitude);

                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.icon_myself);
                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap);
                    //在地图上添加Marker，并显示
                    marker_self =(Marker) mBaiduMap.addOverlay(option);

                    //定义地图状态
                    MapStatus mMapStatus = new MapStatus.Builder()
                            .target(point)
                            .zoom(12)
                            .build();
                    //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
                    MapStatusUpdate mMapStatusUpdate;
                    //mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                    mMapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(point,14);
                    //改变地图状态
                    mBaiduMap.animateMapStatus(mMapStatusUpdate);
                    mLocationClient.stop();

                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_map, container, false);
        mRestSource = RestSource.getInstance();
        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BaiduNaviManager.getInstance().initEngine(getActivity(), getSdcardDir(),
                mNaviEngineInitListener, new LBSAuthManagerListener() {
                    @Override
                    public void onAuthResult(int status, String msg) {
                        String str = null;
                        if (0 == status) {
                            str = "key校验成功!";
                        } else {
                            str = "key校验失败, " + msg;
                        }
                        Toast.makeText(getActivity(), str,
                                Toast.LENGTH_LONG).show();
                    }
                });

        initView();
        setListener();

    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getDefaultBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getDefaultBus().unregister(this);
    }

    private void initView() {

        //定位服务客户端
        mLocationClient = ((ChargeApp)getActivity().getApplication()).mLocationClient;
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        mMapView = (MapView)getView(). findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);//隐藏缩放控件
        mBaiduMap.setBuildingsEnabled(true);//设置显示楼体

        btn_start_loc = (Button)getView().findViewById(R.id.start_loc);
        btn_search_sta = (Button)getView().findViewById(R.id.search_station);
        btn_start_search = (Button)getView().findViewById(R.id.start_search);
        btn_zoom_in = (Button)getView().findViewById(R.id.zoom_in);
        btn_zoom_out = (Button)getView().findViewById(R.id.zoom_out);

//        GeoCodeKey[0]= "杭州市余杭区西溪湿地龙舌嘴入口";
//        GeoCodeKey[1]= "杭州市西湖区西斗门路3号";
//        GeoCodeKey[2]= "杭州市江干区景昙路18-26号";
//        GeoCodeKey[3]= "杭州市下城区绍兴路365号";
//        GeoCodeKey[4]= "杭州市上城区延安路98号";

        InitLocation();//定位监听接口初始化

        mLocationClient.start();
    }
    private void setListener() {

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
                    Toast.makeText(getActivity(), "已经放至最大！", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity(), "已经缩至最小！", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), city_all ,Toast.LENGTH_LONG).show();
                String city = "杭州";

//                // Geo搜索
//                for(int i=0;i<5;i++){
//                    // 初始化搜索模块，注册事件监听
//                    mSearch = GeoCoder.newInstance();
//                    mSearch.setOnGetGeoCodeResultListener(MapFragment.this);
//                    // 发起搜索
//                    mSearch.geocode(new GeoCodeOption().city(city).address(GeoCodeKey[i]));
//
//                }
                mRestSource.getChargers(city);
            }
        });

        btn_start_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), PoiSearchActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {

                if (marker == marker_self) {
                    String str = String.format("纬度：%f 经度：%f",
                            marker.getPosition().latitude, marker.getPosition().longitude);
                    Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
                } else {
                    int pos = isChargeStation(marker);
                    if (pos == -1) {
                        Log.d("MapFragment", "Marker invalid");
                        return false;
                    }
                    String str = mLocsList.get(pos).getName();
//                    String str = String.format(GeoCodeKey[num_charge_station]);

                    Intent intent = new Intent(getActivity(), PopDetailActivity.class);
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

    public void onEventMainThread(List<LocationBean> list) {
        mLocsList = list;
        mMarkers = new ArrayList<>();
        for (int i = 0; i < mLocsList.size(); i++) {
            mSearch = GeoCoder.newInstance();
            mSearch.setOnGetGeoCodeResultListener(MapFragment.this);

            mSearch.geocode(new GeoCodeOption().city("杭州").address(mLocsList.get(i).getLocation()));
        }
    }

    //地理编码请求结果返回
    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }

        Marker tempMarker = (Marker)mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
            .icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.charge_slow_free)));
        mMarkers.add(tempMarker);

        //mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result.getLocation()));
    }

    //反地理编码请求结果返回，不需要所以没有写
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
    }


    public void onGetPoiResult(PoiResult result) {
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(getActivity(), "未找到结果", Toast.LENGTH_LONG)
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
            Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getActivity(), result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
                    .show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode){
            case 2:
                String result = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据
                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

                //EditText editCity = (EditText) findViewById(R.id.city);
                String city = "杭州";
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
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    private int isChargeStation(Marker marker){
        for (int i = 0; i < mMarkers.size(); i++) {
            if (marker.equals(mMarkers.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
