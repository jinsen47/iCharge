package com.icharge;

import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Environment;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.lbsapi.auth.LBSAuthManagerListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.BNaviEngineManager;
import com.baidu.navisdk.BaiduNaviManager;
import com.icharge.ui.map.MapFragment;

/**
 * Created by Jinsen on 15/5/22.
 */
public class ChargeApp extends Application {
    public static Context mContext;
    public LocationClient mLocationClient;
    public MyLocationListener mMyLocationListener;
    public TextView mLocationResult;
    public Vibrator mVibrator;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
    }

    public static Context getAppContext() {
        return mContext;
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        double La;
        double Lo;
        String city;


        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());

            La = location.getLatitude();
            Lo = location.getLongitude();
            city = location.getCity();
            // LatLng point = new LatLng(La,Lo);
            String point = Double.toString(La)+" "+Double.toString(Lo)+" "+ city;

            Message msg = new Message();
            msg.what = MapFragment.mapLocation;
            msg.obj=point;

            MapFragment.mHandler.sendMessage(msg);

            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\ndirection : ");
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append(location.getDirection());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
            }
            logMsg(sb.toString());
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }

    /**
     * 显示请求字符串
     * @param str
     */
    public void logMsg(String str) {
        try {
            if (mLocationResult != null)
                mLocationResult.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
