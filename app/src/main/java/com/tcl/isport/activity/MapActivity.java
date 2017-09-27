package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.util.LocationUtil;

/**
 * Created by haoyi.pan on 17-9-22.
 */
public class MapActivity extends Activity implements View.OnClickListener{
    //运动地图轨迹界面
    private MapView mapView;
    protected AMap aMap;
    private MyLocationStyle myLocationStyle;

    //运动地图轨迹界面
    private ImageView map_type,my_location,change_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);

//        隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_map);

        initView();
        mapView.onCreate(savedInstanceState);
        initParams();

        initListener();
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
        myLocationStyle.interval(2000);
        //定位模式,定位一次，并将其移到中心点位置
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);

        aMap.setMyLocationStyle(myLocationStyle);
        //显示定位蓝点
        aMap.setMyLocationEnabled(true);
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

                break;
            case R.id.my_location:
                LocationUtil.setMapPoint(aMap);
                break;
            case R.id.change_view:
                //获取上一个Activity的名字判断是Walk/Run/Ride以返回到正确的Actiivty
                Intent intent = getIntent();
                String className = intent.getStringExtra("className");
                try {
                    intent = new Intent(this, Class.forName(className));
                    //设置flag使activity不会被销毁
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    //
                    this.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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


}
