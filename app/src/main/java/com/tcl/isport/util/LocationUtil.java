package com.tcl.isport.util;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.tcl.isport.fragment.HomeWalkFragment;
import com.tcl.isport.model.WalkModel;
import com.tcl.isport.presenter.HomeFragmentPresenter;

import java.text.DecimalFormat;

/**
 * Created by lishui.lin on 17-9-20 20:20
 */

public class LocationUtil {

    public static LocationUtil instance;

    public static String ISPORT_TAG = "ISportTag";

    public static float speed;
    public static String city = "";
    public static String poiName;

    public static LatLng latLngPoint = new LatLng(0, 0);


    private LocationUtil(){

    }

    //单例模式
    public static LocationUtil getInstance() {
        if (instance == null) {
            instance = new LocationUtil();
        }

        return instance;
    }

    //定位类型LocationType
    /*
    * LOCATION_TYPE SUCCESS 定位成功  0
    * LOCATION_TYPE CELL 基站定位  6
    * LOCATION_TYPE FIX_CACHE 缓存定位结果  4
    * LOCATION_TYPE GPS  GPS定位结果  1
    * LOCATION_TYPE OFFLINE 离线定位结果  8
    * LOCATION_TYPE SAME_REQ 定位网络请求低于1秒  2
    * LOCATION_TYPE WIFI wifi定位结果  5
    * */
    //计算两点坐标的距离
    public synchronized static float getLocationDistance(PolylineOptions polylineOptions){
        float distance = 0.0f;
        if (polylineOptions.getPoints() != null && polylineOptions.getPoints().size() > 1) {
            int length = polylineOptions.getPoints().size();
            for (int i =0; i < length-1; i++) {
                distance += AMapUtils.calculateLineDistance(polylineOptions.getPoints().get(i),
                        polylineOptions.getPoints().get(i+1));
            }
        }
//        Log.e(LocationUtil.ISPORT_TAG, "--" + distance);
        return distance;
    }

    //将米转换为公里
    public synchronized static String getFriendlyLength(float lenMeter) {

        float dis = lenMeter / 1000;
        DecimalFormat fnum = new DecimalFormat("#0.00");
        String dstr = fnum.format(dis);
        return dstr;

    }

    //转换为分钟/公里
    public static String getMinforKilos(float speed) {

        //通过数学计算，可以知道 m/s 转换为  min/km   等于 50/3 约等于16.667
        float dis = speed * (50/3);
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String dstr = fnum.format(dis);
        return dstr;
    }

    //在定位服务中设置当前速度的值
    public synchronized static void setLocationData(AMapLocation aMapLocation) {
        speed = aMapLocation.getSpeed();
        city = aMapLocation.getCity();
        poiName = aMapLocation.getPoiName();
        latLngPoint = converLatLng(aMapLocation);
    }

    //设置地图移到哪个点上
    public static void setMapPoint(AMap aMap) {
        //如果不用服务的定位点，可以使用定位蓝点进行中心点移动
        if (aMap != null) {
            aMap.moveCamera(CameraUpdateFactory.newLatLng(latLngPoint));
        }
    }

    //转换为LatLng坐标格式
    public static LatLng converLatLng(AMapLocation aMapLocation) {

        double latitude = aMapLocation.getLatitude();
        double longitude = aMapLocation.getLongitude();
        return new LatLng(latitude, longitude);
    }
    public static String getManufacture(Context context) {
        return Build.MANUFACTURER;
    }



}
