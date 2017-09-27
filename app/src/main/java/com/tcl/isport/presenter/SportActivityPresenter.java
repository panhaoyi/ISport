package com.tcl.isport.presenter;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;


import com.tcl.isport.activity.RideActivity;
import com.tcl.isport.activity.RunActivity;
import com.tcl.isport.activity.WalkActivity;
import com.tcl.isport.imodel.ISportModel;
import com.tcl.isport.iview.ISportActivity;
import com.tcl.isport.model.RideModel;
import com.tcl.isport.model.RunModel;
import com.tcl.isport.model.WalkModel;
import com.tcl.isport.service.SportLocationService;
import com.tcl.isport.util.TimeCounter;

import static java.lang.Thread.sleep;

/**
 * Created by haoyi.pan on 17-9-18.
 */
public class SportActivityPresenter {

    //WalkActivity,RunActivity,RideActivity业务逻辑
    private ISportModel iSportModel;
    private ISportActivity iSportActivity;
    private SportLocationService.MyBinder mBinder;

    private boolean isBind = false;
    private boolean isRun = false;
    private boolean isStartRun = false;


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


    public void startLocationService(Context mContext) {
        mContext.startService(new Intent(mContext, SportLocationService.class));
    }

    public void stopLocationService(Context mContext) {
        mContext.stopService(new Intent(mContext, SportLocationService.class));
    }

    public void bindLocationService(Context mContext) {
        Intent intent = new Intent(mContext, SportLocationService.class);
        mContext.bindService(intent, connection, Service.BIND_AUTO_CREATE);
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
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    TimeCounter timeCounter ;
    FreshViewThread freshViewThread;
    public void startTime() {
        if (!isStartRun) {
            timeCounter = new TimeCounter();
            isStartRun = true;
            isRun = true;
            freshViewThread = new FreshViewThread();
        }
        timeCounter.startTime();
        freshViewThread.start();

    }

    public void pauseTime() {
        if (timeCounter != null) {
            timeCounter.pauseTime();
        }
    }

    public void stopTime() {
        if (timeCounter != null) {
            timeCounter.stopTime();
            isRun = false;
        }
    }

    public String getTime() {
        return timeCounter.getTime();
    }

    //Handler使其能够在主线程执行
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 12345:
                    iSportActivity.setDuration(getTime());
                    break;
            }

        }
    };
    //设置一个专门的线程更新
    public class FreshViewThread extends Thread {

        @Override
        public void run() {
            while (isRun){
                mHandler.sendEmptyMessage(12345);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
