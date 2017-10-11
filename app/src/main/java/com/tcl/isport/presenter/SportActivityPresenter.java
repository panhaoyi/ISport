package com.tcl.isport.presenter;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


import com.amap.api.maps.model.LatLng;
import com.tcl.isport.activity.RideActivity;
import com.tcl.isport.activity.RunActivity;
import com.tcl.isport.activity.WalkActivity;
import com.tcl.isport.bean.SportBean;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.iview.ISportActivity;
import com.tcl.isport.model.RideModel;
import com.tcl.isport.model.RunModel;
import com.tcl.isport.model.WalkModel;
import com.tcl.isport.service.SportLocationService;
import com.tcl.isport.service.StepService;
import com.tcl.isport.util.LocationUtil;
import com.tcl.isport.util.StepDetector;
import com.tcl.isport.util.TimeCounter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.lang.Thread.sleep;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class SportActivityPresenter {

    //WalkActivity,RunActivity,RideActivity业务逻辑
    private ISportModel iSportModel;
    private ISportActivity iSportActivity;
    private SportLocationService.MyBinder mBinder;
    private Binder binder;

    private boolean isBind = false;
    //线程运行状态
    public static boolean ThreadRun = true;
    private boolean isRun = false;
    //计时器是否开始运作
    private boolean isStartRun = false;
    //位置监听是否进行中
    private boolean isLocationRun = true;

    public SportActivityPresenter(){

    }

    /*added start by lishui.lin*/
    public SportActivityPresenter(ISportActivity view){
        //构造器通过参数拿到view实例化view接口，根据view的类型初始化model
        this.iSportActivity=view;
        if(view instanceof WalkActivity){
            iSportModel=new WalkModel();
        }
        else if(view instanceof RunActivity){
            iSportModel=new RunModel();
        }
        else if(view instanceof RideActivity){
            iSportModel=new RideModel();
        }
    }

    /*start add by haoyi.pan on 2017-9-30*/
    public void startStepService(Context mContext){

        mContext.startService(new Intent(mContext,StepService.class));

    }

    public void stopStepService(Context mContext){
        mContext.stopService(new Intent(mContext,StepService.class));
    }
    /*end add by haoyi.pan on 2017-9-30*/

    public void startLocationService(Context mContext) {
        mContext.startService(new Intent(mContext, SportLocationService.class));
        /*start add by haoyi.pan on 2017-9-30*/
//        mContext.startService(new Intent(mContext,StepService.class));
        /*end add by haoyi.pan on 2017-9-30*/
    }

    public void stopLocationService(Context mContext) {
        mContext.stopService(new Intent(mContext, SportLocationService.class));
        /*start add by haoyi.pan on 2017-9-30*/
//        mContext.stopService(new Intent(mContext,StepService.class));
        /*end add by haoyi.pan on 2017-9-30*/
    }

    public void bindLocationService(Context mContext) {
        Intent intent = new Intent(mContext, SportLocationService.class);
        mContext.bindService(intent, connection, Service.BIND_AUTO_CREATE);
//        /*start add by haoyi.pan on 2017-9-30*/
//        Intent intentStep=new Intent(mContext,StepService.class);
//        mContext.bindService(intentStep,connection,Service.BIND_AUTO_CREATE);
//        /*end add by haoyi.pan on 2017-9-30*/
    }

    public void unbindLocationService(Context mContext) {
        //解绑一次，多次解绑会崩溃
        if (isBind) {
            mContext.unbindService(connection);
            isBind = false;
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (SportLocationService.MyBinder) service;

            //连接成功则开始计时
            startTime();
            setTimeRun(true);

            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //使服务和计时器同步
    public void setTimeRun(boolean isFlag) {
        mBinder.setTimeRun(isFlag);
    }

    TimeCounter timeCounter ;
    FreshViewThread freshViewThread;
    public void startTime() {
        if (!isStartRun) {
            timeCounter = new TimeCounter();
            isStartRun = true;
            isRun = true;
            freshViewThread = new FreshViewThread();
            freshViewThread.start();
        }
        timeCounter.startTime();
        ThreadRun = true;

    }

    public void pauseTime() {
        if (timeCounter != null) {
            timeCounter.pauseTime();
            ThreadRun = false;
        }
    }

    public void stopTime() {
        if (timeCounter != null) {
            timeCounter.stopTime();
            isRun = false;
        }
    }

    public void startLocationSearch(){
        mBinder.startLocationSearch();
    }

    public void startCountDown() {
        countDownTimer.start();
    }

    public void cancelCountDown() {
        countDownTimer.cancel();
    }

    //设置一个倒计时，当用户在十秒内没有点击开始运动，则取消定位，避免电量损耗
    public boolean getLocationState() {
        return isLocationRun;
    }

    //倒计时，设置为12秒，4秒倒计时一次
    CountDownTimer countDownTimer = new CountDownTimer(12000,4000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            mBinder.stopLocationSearch();
            isLocationRun = false;
        }
    };

    private String getTime() {
        return timeCounter.getTime();
    }

    //获取公里数
    private String getKilometers() {
        return LocationUtil.getFriendlyLength(mBinder.getDistances());
    }
    private String getMinforKilos() {
        if (LocationUtil.speed > 0.15f) {
            return LocationUtil.getMinforKilos(LocationUtil.speed);
        }
        return "--'--";
    }
    private int getStepCount(){
        Log.v("SportActivityPresenter",StepDetector.CURRENT_STEP+"");
        return StepDetector.CURRENT_STEP;
    }
    //Handler使其能够在主线程执行
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 12345:
                    iSportActivity.setDuration(getTime());
                    iSportActivity.setDistance(getKilometers());
                    iSportActivity.setStep(getStepCount());
                    iSportActivity.setSpeed(getMinforKilos());
                    break;
            }

        }
    };
    //设置一个专门的线程更新
    public class FreshViewThread extends Thread {

        @Override
        public void run() {
            //线程不能死亡，同一名称线程无法再次创建
            while (isRun){
                if (ThreadRun){
                    mHandler.sendEmptyMessage(12345);
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*单位类型转换*/
    //String时间类型转为long类型
    public long convertStrToLong(String timeStr) {
        long totalSecond = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = null;
            date = sdf.parse(timeStr);
            calendar.setTime(date);
            if ((calendar.get(Calendar.SECOND)) != 0) {
                totalSecond += (calendar.get(Calendar.SECOND));
            }

            if ((calendar.get(Calendar.MINUTE)) != 0) {
                totalSecond += (calendar.get(Calendar.MINUTE))*60;
            }

            if ((calendar.get(Calendar.HOUR_OF_DAY)) != 0) {
                totalSecond += (calendar.get(Calendar.HOUR_OF_DAY))*3600;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return totalSecond;
    }

    /*单位类型转换*/
    //数据存储模块
    public void saveSportData(Context mContext) {

        //存储数据到服务器中
        SportBean sportBean = new SportBean();
        sportBean.setDistance(iSportActivity.getDistance());
        sportBean.setDuration(iSportActivity.getDuration());
        sportBean.setSpeed(iSportActivity.getSpeed());
        /*start add by haoyi.pan on 2017-9-30*/
        sportBean.setStep(iSportActivity.getStep());
        /*end add by haoyi.pan on 2017-9-30*/
        sportBean.setUserId("test leanCloud");
        iSportModel.saveSportData(mContext,sportBean);
    }
    /*added end by lishui.lin*/



}
