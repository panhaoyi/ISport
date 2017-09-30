package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.ISportActivity;
import com.tcl.isport.presenter.SportActivityPresenter;
import com.tcl.isport.R;

import java.util.Date;

public class RideActivity extends Activity implements View.OnClickListener,ISportActivity, View.OnLongClickListener  {

    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，拍照发话题

    private TextView distance_ride, speed_ride, duration_ride;
    private ImageView map_ride, camera_ride, start_pause_ride, stop_ride;
    private String start_pause = "pause";
    private SportActivityPresenter rideActivityPresenter;

    private Intent intent;

    //给开始定时和暂停计时的判断
    private boolean isStart = false;
    //倒计时是否已经取消
    private boolean isCancelCountDown = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ride);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this, R.id.layout_ride);

        //初始化view
        distance_ride = (TextView) findViewById(R.id.distance_ride);
        speed_ride = (TextView) findViewById(R.id.speed_ride);
        duration_ride = (TextView) findViewById(R.id.duration_ride);
        map_ride = (ImageView) findViewById(R.id.map_ride);
        map_ride.setOnClickListener(this);
        camera_ride = (ImageView) findViewById(R.id.camera_ride);
        camera_ride.setOnClickListener(this);
        start_pause_ride = (ImageView) findViewById(R.id.start_pause_ride);
        start_pause_ride.setOnClickListener(this);

        stop_ride = (ImageView) findViewById(R.id.stop_ride);
        stop_ride.setOnLongClickListener(this);
        rideActivityPresenter=new SportActivityPresenter(this);

        //启动服务，在点击事件设置一个flag判断是否首次启动服务
        rideActivityPresenter.startLocationService(this);
        rideActivityPresenter.bindLocationService(this);
        //启动倒计时，当一直没有点击开始运动，则停止定位
//        rideActivityPresenter.startCountDown();

    }

    private void rideGo() {
//        startExercise();
        //启动定时器
        rideActivityPresenter.startTime();
        isStart = true;
        rideActivityPresenter.setTimeRun(isStart);
    }

    private void ridePause() {
        rideActivityPresenter.pauseTime();
        isStart = false;
        rideActivityPresenter.setTimeRun(isStart);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //禁用Android的返回按钮
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//           
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_ride:
                intent=new Intent(RideActivity.this,MapActivity.class);
//                //将当前Activity的class名字通过intent传到MapActivity以便于返回
//                intent.putExtra("className",this.getClass().getName());
//                //设置flag使activity不会被销毁
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.camera_ride:

                break;
            case R.id.start_pause_ride:
                //点击开始/暂停,切换图标并开始/暂停运动
                if (start_pause.equals("pause")) {
                    start_pause_ride.setImageResource(R.drawable.bt_start);

                    ridePause();

                    start_pause = "start";
                } else {
                    start_pause_ride.setImageResource(R.drawable.bt_pause);

                    rideGo();

                    start_pause = "pause";
                }
                break;
            default:
                break;
        }
    }

    //倒计时，当用户一直不点击开始超过12秒，停止监听位置变化，节省电量
    private void startExercise(){
        if (rideActivityPresenter.getLocationState()) {
            if (!isCancelCountDown){
                rideActivityPresenter.cancelCountDown();
            }
        } else {
            rideActivityPresenter.startLocationSearch();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.stop_ride :
                rideActivityPresenter.unbindLocationService(this);
                rideActivityPresenter.stopLocationService(this);
                rideActivityPresenter.stopTime();

                rideActivityPresenter.saveSportData(this);
                this.finish();
                break;
        }
        return true;
    }


    @Override
    public void setDistance(String distance) {
        distance_ride.setText(distance);
    }

    @Override
    public void setSpeed(String speed) {
        speed_ride.setText(speed);
    }

    @Override
    public void setDuration(String duration) {
        duration_ride.setText(duration);
    }

    @Override
    public void setStep(int step) {
        //ride模式没有步数step
    }

    @Override
    public String getDistance() {
        return distance_ride.getText().toString();
    }

    @Override
    public long getDuration() {
        return  rideActivityPresenter.convertStrToLong(duration_ride.getText().toString());
    }

    @Override
    public String getSpeed() {
        return duration_ride.getText().toString();
    }

    @Override
    public int getStep() {
        return -1;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止服务和计时器
        rideActivityPresenter.unbindLocationService(this);
        rideActivityPresenter.stopLocationService(this);
        rideActivityPresenter.stopTime();
    }
}
