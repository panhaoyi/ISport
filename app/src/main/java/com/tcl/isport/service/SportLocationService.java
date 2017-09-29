package com.tcl.isport.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.tcl.isport.delegate.WifiAutoCloseDelegate;
import com.tcl.isport.idelegate.IWifiAutoCloseDelegate;
import com.tcl.isport.util.LocationUtil;
import com.tcl.isport.util.NetUtil;
import com.tcl.isport.util.PathSmoothTool;
import com.tcl.isport.util.PowerManagerUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishui.lin
 * 生命周期 ：onCreate--onStartCommand--onBind--onUnbind--onRebind
 *  启动服务，绑定服务，解除绑定，停止服务
 */
public class SportLocationService extends Service {

    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LatLng startLatLng;

    //以前的点
    private LatLng oldLatlng = new LatLng(0, 0);
    //位置更新的标记点
    private LatLng newLatlng = new LatLng(1, 1);
    List<LatLng> polylineLatlngs = new ArrayList<>();
    //    List<LatLng> totalLatlngLists = new ArrayList<>();
    //实时定位，存储轨迹线
    private List<Polyline> runningPolylineList = new ArrayList<>();
    //实时定位展示运动轨迹
    private PolylineOptions polylineOptions = new PolylineOptions();

    //定时器暂停判断
    private boolean isTimeRun = false;
    //起点设置判断
    private boolean isStartPoint = true;

    PathSmoothTool pathSmoothTool = null;
    /**
     * 处理息屏关掉wifi的delegate类
     */
    private IWifiAutoCloseDelegate mWifiAutoCloseDelegate = new WifiAutoCloseDelegate();

    /**
     * 记录是否需要对息屏关掉wifi的情况进行处理
     */
    private boolean mIsWifiCloseable = false;

    public SportLocationService() {
    }

    private MyBinder mBinder = new MyBinder();

    public class MyBinder extends Binder {
        //动态获取location的运行状况
//        public LatLng getCurrentLatlng() {
//            return latLng;
//        }
        //处理精确度、速度和距离，此时可以用上BroadcastReceiver
        //显示轨迹
        public void drawPolyLine(AMap aMap) {
            if (polylineOptions.getPoints() != null && polylineOptions.getPoints().size() > 1) {
                polylineOptions.width(10).color(Color.argb(128, 255, 255, 255));
                aMap.addPolyline(polylineOptions);
            }
        }
        //清除轨迹
        public void clearLineOnMap() {
            int length = polylineOptions.getPoints().size();
            if (polylineOptions != null && length > 0) {
                Log.e("polylineOptions", "--delete size" + length);
                polylineOptions.getPoints().clear();
            }
        }

        //计算当前行进距离
        public float getDistances(){
            return LocationUtil.getLocationDistance(polylineOptions);
        }

        //定时器状态设置
        public void setTimeRun(boolean isRun) {
            isTimeRun = isRun;
        }

        public LatLng getStartLatLng() {
            if (startLatLng != null) {
                return startLatLng;
            }
            return null;
        }
        //开始定位
        public void startLocationSearch() {
            startLocation();
        }
        //停止定位
        public void stopLocationSearch() {
            stopLocation();
        }
    }

    //初次调用service
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        Log.e("SportLocationService", "----onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (mWifiAutoCloseDelegate.isUseful(getApplicationContext())) {
            mIsWifiCloseable = true;
            mWifiAutoCloseDelegate.initOnServiceStarted(getApplicationContext());
        }
        startLocation();

        setUp();
        return START_STICKY;
    }

    private void setUp() {
        //设置卡尔曼滤波
        pathSmoothTool = new PathSmoothTool();
        //设置滤波强度为3（1-5）
        pathSmoothTool.setIntensity(3);

        //设置polyline的参数,具体配置可以调节
        polylineOptions.useGradient(true).transparency(0.5f).color(Color.GREEN);
    }
    /**
     * 启动定位
     */
    void startLocation() {
        stopLocation();

        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(this.getApplicationContext());
        }

        mLocationOption = new AMapLocationClientOption();
        //设置地图精确度
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 使用连续
        mLocationOption.setOnceLocation(false);
        mLocationOption.setLocationCacheEnable(false);
        // 每1秒定位一次
        mLocationOption.setInterval(1 * 1000);
        // 地址信息
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(locationListener);
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    void stopLocation() {
        if (null != mLocationClient) {
            mLocationClient.stopLocation();
        }
    }

    //中转坐标点声明
    List<LatLng> tempLatLngs = new ArrayList<>();

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //发送结果
            sendLocation(aMapLocation);

            if (!mIsWifiCloseable) {
                return;
            }

            if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
                mWifiAutoCloseDelegate.onLocateSuccess(getApplicationContext(), PowerManagerUtil.getInstance().isScreenOn(getApplicationContext()), NetUtil.getInstance().isMobileAva(getApplicationContext()));
            } else {
                mWifiAutoCloseDelegate.onLocateFail(getApplicationContext(), aMapLocation.getErrorCode(), PowerManagerUtil.getInstance().isScreenOn(getApplicationContext()), NetUtil.getInstance().isWifiCon(getApplicationContext()));
            }

        }

        private void sendLocation(AMapLocation aMapLocation) {

            if (null == aMapLocation) {
                //如果定位失败，可以进行处理
                Log.e("SportLocationService", "not data for location now");
            } else {
                if (aMapLocation.getErrorCode() == 0){
                    //考虑GPS

                    if (aMapLocation.getAccuracy() < 30) {//尽量减少定位距离的误差,在室内，不开启GPS的准确度更高
                        // 根据精确度来区分使用轨迹纠偏，特别时在室内和室外的两种情况
//                        Log.e("GPS status", "--" + aMapLocation.getLocationType()+
//                                "--"+aMapLocation.getGpsAccuracyStatus()+"=="+aMapLocation.getAccuracy()
//                        +"--"+polylineOptions.isUseGradient());
                        LocationUtil.setLocationData(aMapLocation);
                        if (isTimeRun) {

                            oldLatlng = converLatLng(aMapLocation);

                            //判断重复点
                            if (newLatlng == oldLatlng) {
                                return;
                            } else {
                                newLatlng = oldLatlng;
                            }

                            //起点判断
                            if (isStartPoint) {
                                startLatLng = newLatlng;
                                isStartPoint = false;
                            }
                            tempLatLngs.add(newLatlng);
                            //轨迹纠偏
                            if (tempLatLngs.size() > 3) {
                                filterPoints();
                            }
                        }
                    }
                } else {
                    //定位错误码，对定位失败进行判断处理
                }

            }

        }

    };


    private void filterPoints() {
        //添加新轨迹之前，先把之前的轨迹清空，不然最后地图会很卡
        clearPreLine();
        getValidPoints();
    }

    //获取有效数据坐标点
    private void getValidPoints() {
        //使用轨迹线路的卡尔曼滤波方式获取轨迹线坐标
        polylineLatlngs = pathSmoothTool.kalmanFilterPath(tempLatLngs);
        if (polylineLatlngs.size() > 0) {
            polylineOptions.addAll(polylineLatlngs);
        }
        tempLatLngs.clear();

    }


    //记录轨迹时，清空上一次的线条,polylineOptions依然存在
    private void clearPreLine() {
        if (polylineLatlngs != null && polylineLatlngs.size() > 0) {
            polylineLatlngs.clear();
        }
    }

    //清除用户的实时画出的用户轨迹
//    private void clearLineOnMap() {
//        int length = polylineOptions.getPoints().size();
//        if (polylineOptions != null && length > 0) {
////            for (int i =0; i < length; i++) {
////                polylineOptions.getPoints().remove(i);
////            }
//            polylineOptions.getPoints().clear();
//        }
//    }
    //转换为LatLng坐标格式
    private LatLng converLatLng(AMapLocation aMapLocation) {

        double latitude = aMapLocation.getLatitude();
        double longitude = aMapLocation.getLongitude();
        return new LatLng(latitude, longitude);
    }


    //解绑服务时，先调用onUnbind，再接着调用onDestroy
    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("SportLocationService", "----onUnbind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        stopLocation();
        Log.e("SportLocationService", "----onDestroy");
        super.onDestroy();
    }
}
