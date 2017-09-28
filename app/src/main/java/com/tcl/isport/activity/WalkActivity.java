package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.ISportActivity;
import com.tcl.isport.presenter.SportActivityPresenter;
import com.tcl.isport.R;
import com.tcl.isport.util.LocationUtil;

/**
 * Created by user on 17-9-8.
 */
public class WalkActivity extends Activity implements View.OnClickListener,ISportActivity, View.OnLongClickListener {
    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，拍照发话题

    private TextView distance_walk,speed_walk,duration_walk;
    private ImageView map_walk;
    private Button camera_walk, start_pause_walk, stop_walk;

    private SportActivityPresenter walkActivityPresenter;
    private Intent intent;

    //给开始定时和暂停计时的判断
    private boolean isStart = false;
    //倒计时是否已经取消
    private boolean isCancelCountDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_walk);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_walk);

        //初始化view
        distance_walk= (TextView) findViewById(R.id.distance_walk);
        speed_walk= (TextView) findViewById(R.id.speed_walk);
        duration_walk= (TextView) findViewById(R.id.duration_walk);
        map_walk= (ImageView) findViewById(R.id.map_walk);
        map_walk.setOnClickListener(this);
        camera_walk = (Button) findViewById(R.id.camera_walk);
        camera_walk.setOnClickListener(this);
        start_pause_walk = (Button) findViewById(R.id.start_pause_walk);
        start_pause_walk.setOnClickListener(this);
        stop_walk = (Button) findViewById(R.id.stop_walk);
        stop_walk.setOnLongClickListener(this);

        walkActivityPresenter=new SportActivityPresenter(this);

        //启动服务，在点击事件设置一个flag判断是否首次启动服务
        walkActivityPresenter.startLocationService(this);
        walkActivityPresenter.bindLocationService(this);
        //启动倒计时，当一直没有点击开始运动，则停止定位
        walkActivityPresenter.startCountDown();
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
            case R.id.map_walk:
                intent=new Intent(WalkActivity.this,MapActivity.class);
                //将当前Activity的class名字通过intent传到MapActivity以便于返回
//                intent.putExtra("className",this.getClass().getName());
                //设置flag使activity不会被销毁
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.camera_walk:
//                intent=new Intent(WalkActivity.this,CameraActivity.class);
//                startActivity(intent);
                break;
            case R.id.start_pause_walk:
                //开始计时要给时间让定位服务初始化，同时有个倒计时判断用户行为
                if (!isStart) {
                    startExercise();
                    //启动定时器
                    walkActivityPresenter.startTime();
                    isStart = true;
                    walkActivityPresenter.setTimeRun(isStart);
                } else {
                    walkActivityPresenter.pauseTime();
                    isStart = false;
                    walkActivityPresenter.setTimeRun(isStart);
                }

                break;
            default:
                break;
        }
    }

    //倒计时，当用户一直不点击开始超过12秒，停止监听位置变化，节省电量
    private void startExercise(){
        if (walkActivityPresenter.getLocationState()) {
            if (!isCancelCountDown){
                walkActivityPresenter.cancelCountDown();
            }
        } else {
            walkActivityPresenter.startLocationSearch();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.stop_walk :
                walkActivityPresenter.unbindLocationService(this);
                walkActivityPresenter.stopLocationService(this);
                walkActivityPresenter.stopTime();
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public void setDistance(String distance) {
        distance_walk.setText(distance);
    }

    @Override
    public void setSpeed(String speed) {
        speed_walk.setText(speed);
    }

    @Override
    public void setDuration(String duration) {
        duration_walk.setText(duration);
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
        walkActivityPresenter.unbindLocationService(this);
        walkActivityPresenter.stopLocationService(this);
        walkActivityPresenter.stopTime();
    }


}
