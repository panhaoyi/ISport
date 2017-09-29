package com.tcl.isport.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
import com.tcl.isport.presenter.HomeFragmentPresenter;

/**
 * Created by lishui.lin on 17-9-27 08:40
 */

public class WeatherUtil {

    private AMapLocationClient aMapLocationClient = null;
    private AMapLocationClientOption aMapLocationClientOption = null;
    private String weather = "";
    private String city = "";
    private Context mContext;
    private HomeFragmentPresenter homeFragmentPresenter;

    public WeatherUtil(Context mContext, HomeFragmentPresenter homeFragmentPresenter){
        this.mContext = mContext;
        this.homeFragmentPresenter = homeFragmentPresenter;
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
                    Log.e(LocationUtil.ISPORT_TAG, "无定位数据");
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
}
