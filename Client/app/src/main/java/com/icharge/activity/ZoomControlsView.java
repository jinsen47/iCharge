package com.icharge.activity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
/**
 * Created by emosfet on 2015/4/30.
 */
public class ZoomControlsView extends LinearLayout implements OnClickListener{
    private Button btn_zoom_in;//放大按钮
    private Button btn_zoom_out;//缩小按钮
    private BaiduMap baiduMap;//百度地图对象控制器
    private MapView mapView;
    private MapStatus mapStatus;//百度地图状态
    private float minZoomLevel;//地图最小级别
    private float maxZoomLevel;//地图最大级别

    public ZoomControlsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomControlsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    /**
     * 初始化
     */
    private void init(){
        //获取布局视图
        View view = LayoutInflater.from(getContext()).inflate(R.layout.zoom_controls_in_out, null);
        //获取放大按钮
        btn_zoom_in=(Button) view.findViewById(R.id.btn_zoom_in);
        //获取缩小按钮
        btn_zoom_out=(Button) view.findViewById(R.id.btn_zoom_out);
        //设置点击事件
        btn_zoom_in.setOnClickListener(this);
        btn_zoom_out.setOnClickListener(this);
        //添加View
        mapView.addView(view);
    }

    @Override
    public void onClick(View v) {
        this.mapStatus=this.baiduMap.getMapStatus();//获取地图状态
        switch (v.getId()) {
            case R.id.btn_zoom_in:
                //改变地图状态
                this.baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mapStatus.zoom+1));
                controlZoomShow();//改变缩放按钮
                break;
            case R.id.btn_zoom_out:
                //改变地图状态
                this.baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mapStatus.zoom-1));
                controlZoomShow();//改变缩放按钮
                break;
            default:
                break;
        }
        //重新获取状态
        mapStatus=this.baiduMap.getMapStatus();
    }

    /**
     * 设置Map视图
     * @param mapView
     */
    public void setMapView(MapView mapView){
        //获取百度地图控制器
        this.baiduMap=mapView.getMap();
        //设置地图手势事件
        this.baiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);
        //获取百度地图最大最小级别
        maxZoomLevel=baiduMap.getMaxZoomLevel();
        minZoomLevel=baiduMap.getMinZoomLevel();
        controlZoomShow();//改变缩放按钮
    }

    /**
     * 控制缩放图标显示
     */
    private void controlZoomShow(){
        //获取当前地图状态
        float zoom=this.baiduMap.getMapStatus().zoom;
        //如果当前状态大于等于地图的最大状态，则放大按钮则失效
        if(zoom>=maxZoomLevel){
            btn_zoom_in.setBackgroundResource(R.drawable.zoom_in_press);
            btn_zoom_in.setEnabled(false);
        }else{
            btn_zoom_in.setBackgroundResource(R.drawable.zoom_selector_in);
            btn_zoom_in.setEnabled(true);
        }

        //如果当前状态小于等于地图的最小状态，则缩小按钮失效
        if(zoom<=minZoomLevel){
            btn_zoom_out.setBackgroundResource(R.drawable.zoom_out_press);
            btn_zoom_out.setEnabled(false);
        }else{
            btn_zoom_out.setBackgroundResource(R.drawable.zoom_selector_out);
            btn_zoom_out.setEnabled(true);
        }
    }
    /**
     * 地图状态改变相关接口实现
     */
    BaiduMap.OnMapStatusChangeListener onMapStatusChangeListener=new BaiduMap.OnMapStatusChangeListener() {

        /**
         * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
         * @paramstatus 地图状态改变开始时的地图状态
         */
        @Override
        public void onMapStatusChangeStart(MapStatus arg0) {

        }

        /**
         * 地图状态变化结束
         * @paramstatus 地图状态改变结束时的地图状态
         */
        @Override
        public void onMapStatusChangeFinish(MapStatus arg0) {

        }
        /**
         * 地图状态变化中
         * @paramstatus 当前地图状态
         */
        @Override
        public void onMapStatusChange(MapStatus arg0) {
            controlZoomShow();
        }
    };
}

