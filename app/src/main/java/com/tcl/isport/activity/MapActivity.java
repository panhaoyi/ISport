package com.tcl.isport.activity;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.service.SportLocationService;
import com.tcl.isport.util.LocationUtil;

import static java.lang.Thread.sleep;

/**
 * Created by haoyi.pan on 17-9-22.
 */
public class MapActivity extends Activity implements View.OnClickListener, AMap.OnMyLocationChangeListener {
    //运动地图轨迹界面
    private MapView mapView;
    protected AMap aMap;
    MyLocationStyle myLocationStyle;
    private Marker startMarker;
    private SportLocationService.MyBinder mBinder;
    private boolean isBind = true;

    Thread mapThread;
    //起点设置判断
//    private boolean isStartPoint = true;

    private final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    //运动地图轨迹界面
    private ImageView map_type, my_location, change_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);

//        隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this, R.id.layout_map);

        initView();
        mapView.onCreate(savedInstanceState);
        initParams();
        initListener();
        bindLocationService();

        //启动线程
        MapTraceThread mapTraceThread = new MapTraceThread();
        mapThread = new Thread(mapTraceThread);
        mapThread.start();
    }

    //绑定服务
    private void bindLocationService() {
        //绑定location服务
        Intent intent = new Intent(MapActivity.this, SportLocationService.class);
        this.bindService(intent, serviceConnectin, Service.BIND_AUTO_CREATE);

    }

    //解除绑定
    private void unbindLocationService() {
        if (isBind) {
            //避免多次解绑引起异常
            this.unbindService(serviceConnectin);
            isBind = false;
        }

    }

    ServiceConnection serviceConnectin = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (SportLocationService.MyBinder) service;
            addMarker(mBinder.getStartLatLng());
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //自动画轨迹
    Handler mapHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 23456:
                    mBinder.drawPolyLine(aMap);
                    break;
            }
        }
    };

    private class MapTraceThread implements Runnable {
        @Override
        public void run() {
            //设置一个中断线程
            while (!Thread.currentThread().isInterrupted()) {
                mapHandler.sendEmptyMessage(23456);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void initView() {
        mapView = (MapView) findViewById(R.id.map);
        map_type = (ImageView) findViewById(R.id.map_type);
        my_location = (ImageView) findViewById(R.id.my_location);
        change_view = (ImageView) findViewById(R.id.change_view);
    }

    private void initParams() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        setUiSettings();

        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(1000);
        myLocationStyle.strokeColor(STROKE_COLOR);
        myLocationStyle.radiusFillColor(FILL_COLOR);
        //定位模式,定位一次，并将其移到中心点位置
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);

        //夜景模式
        aMap.setMapType(AMap.MAP_TYPE_NIGHT);
        aMap.setMyLocationStyle(myLocationStyle);
        //显示定位蓝点
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(this);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }

    private void setUiSettings() {
        UiSettings uiSettings = aMap.getUiSettings();
        //设置定位按钮是否可见
        uiSettings.setMyLocationButtonEnabled(false);
        //中心的缩放
        uiSettings.setGestureScaleByMapCenter(true);

        uiSettings.setZoomControlsEnabled(false);

    }

    private void initListener() {
        map_type.setOnClickListener(this);
        my_location.setOnClickListener(this);
        change_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_type:

                //设置成卫星图，地图默认为夜景模式： aMap.setMapType(AMap.MAP_TYPE_NIGHT);
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.my_location:


                LocationUtil.setMapPoint(aMap);
                break;
            case R.id.change_view:
                //获取上一个Activity的名字判断是Walk/Run/Ride以返回到正确的Actiivty
                this.finish();
//                Intent intent = getIntent();
//                String className = intent.getStringExtra("className");
//                try {
//                    intent = new Intent(this, Class.forName(className));
//                    //设置flag使activity不会被销毁
//                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(intent);
//                    //
//                    this.finish();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                break;
            default:
                break;
        }
    }

    //添加marker起点
    private void addMarker(LatLng latLng) {
        if (startMarker != null && latLng == null) {
            return;
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_start_point_map)));
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(latLng);
        startMarker = aMap.addMarker(markerOptions);
//        startMarker.setTitle("起点");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        unbindLocationService();
        mapThread.interrupt();
        Log.e(LocationUtil.ISPORT_TAG, "MapActivity onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    //我的位置变化
    @Override
    public void onMyLocationChange(Location location) {

        if (aMap != null) {
            aMap.moveCamera(CameraUpdateFactory.newLatLng(LocationUtil.converLatLng(location)));
        }


    }


}
