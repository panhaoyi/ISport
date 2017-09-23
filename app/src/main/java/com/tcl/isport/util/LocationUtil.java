package com.tcl.isport.util;

import android.content.Context;
import android.os.Build;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.PolylineOptions;

/**
 * Created by lishui.lin on 17-9-20 20:20
 */

public class LocationUtil {

    public static float speed;
    public static String city;
    public static String poiName;

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
        return distance;
    }

    //获取当前速度
    public synchronized static void setLocationData(AMapLocation aMapLocation) {
        speed = aMapLocation.getSpeed();
        city = aMapLocation.getCity();
        poiName = aMapLocation.getPoiName();
    }

    public static String getManufacture(Context context) {
        return Build.MANUFACTURER;
    }


}
