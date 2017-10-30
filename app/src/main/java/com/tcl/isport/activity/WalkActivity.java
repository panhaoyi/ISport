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
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.bean.SportBean;
import com.tcl.isport.iview.ISportActivity;
import com.tcl.isport.presenter.SportActivityPresenter;
import com.tcl.isport.R;
import com.tcl.isport.util.LocationUtil;

import java.util.Date;

/**
 * Created by user on 17-9-8.
 */
public class WalkActivity extends Activity implements View.OnClickListener,ISportActivity, View.OnLongClickListener {
    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，拍照发话题

    private TextView distance_walk,speed_walk,duration_walk,step_walk;
    private ImageView map_walk,camera_walk, start_pause_walk, stop_walk;
    private String start_pause = "pause";
    private SportActivityPresenter walkActivityPresenter;

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

        step_walk = (TextView) findViewById(R.id.step_walk);

        map_walk= (ImageView) findViewById(R.id.map_walk);
        map_walk.setOnClickListener(this);
        camera_walk = (ImageView) findViewById(R.id.camera_walk);
        camera_walk.setOnClickListener(this);
        start_pause_walk = (ImageView) findViewById(R.id.start_pause_walk);
        start_pause_walk.setOnClickListener(this);
        stop_walk = (ImageView) findViewById(R.id.stop_walk);
        stop_walk.setOnClickListener(this);
        stop_walk.setOnLongClickListener(this);

        walkActivityPresenter=new SportActivityPresenter(this);

        //启动服务，在点击事件设置一个flag判断是否首次启动服务
        walkActivityPresenter.startLocationService(this);
        walkActivityPresenter.bindLocationService(this);
        walkActivityPresenter.startStepService(this);
        //启动倒计时，当一直没有点击开始运动，则停止定位
        //walkActivityPresenter.startCountDown();

    }

    private void walkGo() {
//        startExercise();
        //启动定时器
        walkActivityPresenter.startTime();
        isStart = true;
        walkActivityPresenter.setTimeRun(isStart);
    }

    private void walkPause() {
        walkActivityPresenter.pauseTime();
        isStart = false;
        walkActivityPresenter.setTimeRun(isStart);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        禁用Android的返回按钮
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this,"请长按结束退出本次运动!",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_walk:
                Intent intent=new Intent(WalkActivity.this,MapActivity.class);
                startActivity(intent);
                break;
            case R.id.camera_walk:
//                intent=new Intent(WalkActivity.this,CameraActivity.class);
//                startActivity(intent);
                break;
            case R.id.start_pause_walk:

                //开始计时要给时间让定位服务初始化，同时有个倒计时判断用户行为
                //点击开始/暂停,切换图标并开始/暂停运动
                if (start_pause.equals("pause")){
                    start_pause_walk.setImageResource(R.drawable.bt_start);

                    walkPause();

                    start_pause="start";
                }
                else{
                    start_pause_walk.setImageResource(R.drawable.bt_pause);

                    walkGo();

                    start_pause="pause";
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
                walkActivityPresenter.stopStepService(this);
                walkActivityPresenter.stopTime();

                walkActivityPresenter.saveSportData(this);
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

    /*start add by haoyi.pan on 2017-9-30*/
    @Override
    public void setStep(int step) {
        step_walk.setText(""+step);
    }
    /*end add by haoyi.pan on 2017-9-30*/


    @Override
    public String getDistance() {
        return distance_walk.getText().toString();
    }

    @Override
    public long getDuration() {
        return walkActivityPresenter.convertStrToLong(duration_walk.getText().toString());
    }

    @Override
    public String getSpeed() {
        return speed_walk.getText().toString();
    }

    /*start add by haoyi.pan on 2017-9-30*/
    @Override
    public int getStep() {
        return Integer.valueOf(step_walk.getText().toString());
    }
    /*end add by haoyi.pan on 2017-9-30*/

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止服务和计时器
        walkActivityPresenter.unbindLocationService(this);
        walkActivityPresenter.stopLocationService(this);
        walkActivityPresenter.stopStepService(this);
        walkActivityPresenter.stopTime();
    }


}
