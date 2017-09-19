package com.tcl.isport.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.tcl.isport.IView.ISportActivity;
import com.tcl.isport.Presenter.SportActivityPresenter;
import com.tcl.isport.R;

/**
 * Created by user on 17-9-8.
 */
public class RunActivity extends Activity implements View.OnClickListener,ISportActivity {
    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，显示运动轨迹地图，拍照发话题

    // 百度地图控件
    private MapView mapView;
    // 百度地图对象
    private BaiduMap baiduMap;
    private MapStatusUpdate mapStatusUpdate;
    //返回，切换卫星/平面地图，拍照按钮，暂停按钮，停止按钮
    private Button return_run, settings_run, camera_run, start_pause_run, stop_run;

    private SportActivityPresenter runActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.layout_run);
        /*
         *start，以下内容需更改为显示运动轨迹的地图
         */
        mapView = (MapView) findViewById(R.id.bmapView_run);
        baiduMap = mapView.getMap();
        LatLng latLng = new LatLng(23.0153000000, 114.2058000000);
        mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng, 15);
        baiduMap.setMapStatus(mapStatusUpdate);
        baiduMap.setTrafficEnabled(true);
        baiduMap.setBuildingsEnabled(true);
        baiduMap.setIndoorEnable(true);
        baiduMap.setMyLocationEnabled(true);
        /*
         *end
         */
        //初始化view
        settings_run = (Button) findViewById(R.id.settings_run);
        settings_run.setOnClickListener(this);
        return_run = (Button) findViewById(R.id.return_run);
        return_run.setOnClickListener(this);
        camera_run = (Button) findViewById(R.id.camera_run);
        camera_run.setOnClickListener(this);
        start_pause_run = (Button) findViewById(R.id.start_pause_run);
        start_pause_run.setOnClickListener(this);
        stop_run = (Button) findViewById(R.id.stop_run);
        stop_run.setOnClickListener(this);
        runActivityPresenter=new SportActivityPresenter(this);
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
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重置点击android返回按钮为返回上一个界面，不销毁当前的activity以便继续记录运动信息
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_run:
                //切换卫星图或平面图
                if (baiduMap.getMapType() == BaiduMap.MAP_TYPE_NORMAL) {
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                } else {
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }
                break;
            case R.id.return_run:
                //返回上一个界面，不销毁当前的activity以便继续记录运动信息
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.camera_run:

                break;
            case R.id.start_pause_run:

                break;
            case R.id.stop_run:

                break;
            default:
                break;
        }
    }
}
