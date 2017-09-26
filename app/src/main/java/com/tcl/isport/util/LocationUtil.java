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
import com.amap.api.maps.AMapUtils;
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

/**
 * Created by lishui.lin on 17-9-20 20:20
 */

public class LocationUtil {

    public static LocationUtil instance;

    public static String ISPORT_TAG = "ISportTag";
    private AMapLocationClient aMapLocationClient = null;
    private AMapLocationClientOption aMapLocationClientOption = null;
    private String weather = "";
    private Context mContext;
    private HomeFragmentPresenter homeFragmentPresenter;

    public static float speed;
    public static String city = "";
    public static String poiName;


    private LocationUtil(Context mContext, HomeFragmentPresenter homeFragmentPresenter){
        this.mContext = mContext;
        this.homeFragmentPresenter = homeFragmentPresenter;
    }

    private LocationUtil(){

    }

    //单例模式
    public static LocationUtil getInstance(Context mContext, HomeFragmentPresenter homeFragmentPresenter) {
        if (instance == null) {
            instance = new LocationUtil(mContext, homeFragmentPresenter);
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
        return distance;
    }

    //获取当前速度
    public synchronized static void setLocationData(AMapLocation aMapLocation) {
        speed = aMapLocation.getSpeed();
        city = aMapLocation.getCity();
        poiName = aMapLocation.getPoiName();
    }

    //获取天气
    public String getWeatherData() {
        Log.e(LocationUtil.ISPORT_TAG, "weather:" + weather);
        return weather;
    }

    public void initLocatin() {
        aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 地址信息
        aMapLocationClientOption.setNeedAddress(true);
        // 使用连续
        aMapLocationClientOption.setOnceLocation(false);
        //设置单次定位
//        aMapLocationClientOption.setOnceLocationLatest(true);
        aMapLocationClientOption.setLocationCacheEnable(false);
        if (aMapLocationClient == null) {
            aMapLocationClient = new AMapLocationClient(mContext);

        }
        //设置定位回调监听
        aMapLocationClient.setLocationListener(locationListener);
        //绑定option参数
        aMapLocationClient.setLocationOption(aMapLocationClientOption);

        startLocation();
    }

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                //有AMapLocationListener结果时
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功
                    city = aMapLocation.getCity();
                    //初始化天气
                    initWeather(city);
                    Log.e(LocationUtil.ISPORT_TAG, city);
                    //此时停止定位
                    stopLocation();
                } else {
                    Log.e(LocationUtil.ISPORT_TAG, "无成功定位数据");
                }
            } else {
                Log.e(LocationUtil.ISPORT_TAG, "定位失败");
            }
        }
    };

    private void initWeather(String city) {
        //city获取数据成功，此时开始获取天气情况
        //类型为当前的实时天气
        WeatherSearchQuery mQuery = new WeatherSearchQuery(city,
                WeatherSearchQuery.WEATHER_TYPE_LIVE);
        //默认是
        WeatherSearch mWeatherSearch = new WeatherSearch(mContext);
        mWeatherSearch.setOnWeatherSearchListener(onWeatherSearchListener);
        mWeatherSearch.setQuery(mQuery);

        mWeatherSearch.searchWeatherAsyn();//异步搜索
    }

    //查询天气的回调方法
    WeatherSearch.OnWeatherSearchListener onWeatherSearchListener = new WeatherSearch.OnWeatherSearchListener() {
        @Override
        public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
            if (i == AMapException.CODE_AMAP_SUCCESS) {
                if (localWeatherLiveResult != null && localWeatherLiveResult.getLiveResult() != null) {
                    LocalWeatherLive weatherLive = localWeatherLiveResult.getLiveResult();

                    weather = weatherLive.getWeather();

                    homeFragmentPresenter.setWeather(weather);
                    Log.e(LocationUtil.ISPORT_TAG, weatherLive.getWeather());
                }
            }

        }

        @Override
        public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

        }
    };


    private void startLocation() {
        //启动定位
        aMapLocationClient.startLocation();
    }

    private void stopLocation() {

        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
        }

    }

    public interface IWeather {
        void setWeather(String weather);
    }
    public static String getManufacture(Context context) {
        return Build.MANUFACTURER;
    }



}
