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


public class RunActivity extends Activity implements View.OnClickListener,ISportActivity, View.OnLongClickListener {

    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，拍照发话题

    private TextView distance_run, speed_run, duration_run,step_run;
    private ImageView map_run, camera_run, start_pause_run, stop_run;
    private String start_pause = "pause";
    private SportActivityPresenter runActivityPresenter;
    private Intent intent;

    //给开始定时和暂停计时的判断
    private boolean isStart = false;
    //倒计时是否已经取消
    private boolean isCancelCountDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_run);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this, R.id.layout_run);

        //初始化view
        distance_run = (TextView) findViewById(R.id.distance_run);
        speed_run = (TextView) findViewById(R.id.speed_run);
        duration_run = (TextView) findViewById(R.id.duration_run);
        step_run = (TextView) findViewById(R.id.step_run);
        map_run = (ImageView) findViewById(R.id.map_run);
        map_run.setOnClickListener(this);
        camera_run = (ImageView) findViewById(R.id.camera_run);
        camera_run.setOnClickListener(this);
        start_pause_run = (ImageView) findViewById(R.id.start_pause_run);
        start_pause_run.setOnClickListener(this);
        stop_run = (ImageView) findViewById(R.id.stop_run);
        stop_run.setOnLongClickListener(this);
        runActivityPresenter=new SportActivityPresenter(this);

        //启动服务，在点击事件设置一个flag判断是否首次启动服务
        runActivityPresenter.startLocationService(this);
        runActivityPresenter.bindLocationService(this);
        runActivityPresenter.startStepService(this);
        //启动倒计时，当一直没有点击开始运动，则停止定位
//        runActivityPresenter.startCountDown();
    }

    private void runGo() {
//        startExercise();
        //启动定时器
        runActivityPresenter.startTime();
        isStart = true;
        runActivityPresenter.setTimeRun(isStart);
    }

    private void runPause() {
        runActivityPresenter.pauseTime();
        isStart = false;
        runActivityPresenter.setTimeRun(isStart);
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
            case R.id.map_run:
                intent=new Intent(RunActivity.this,MapActivity.class);
                startActivity(intent);
                break;
            case R.id.camera_run:

                break;
            case R.id.start_pause_run:
                //点击开始/暂停,切换图标并开始/暂停运动,同时有个倒计时判断用户行为
                if (start_pause.equals("pause")) {
                    start_pause_run.setImageResource(R.drawable.bt_start);

                    runPause();

                    start_pause = "start";
                } else {
                    start_pause_run.setImageResource(R.drawable.bt_pause);

                    runGo();

                    start_pause = "pause";
                }
                break;
            default:
                break;
        }
    }

    //倒计时，当用户一直不点击开始超过12秒，停止监听位置变化，节省电量
    private void startExercise(){
        if (runActivityPresenter.getLocationState()) {
            if (!isCancelCountDown){
                runActivityPresenter.cancelCountDown();
            }
        } else {
            runActivityPresenter.startLocationSearch();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.stop_run :
                runActivityPresenter.unbindLocationService(this);
                runActivityPresenter.stopLocationService(this);
                runActivityPresenter.stopStepService(this);
                runActivityPresenter.stopTime();

                runActivityPresenter.saveSportData(this);
                this.finish();
                break;
        }
        return true;
    }
    @Override
    public void setDistance(String distance) {
        distance_run.setText(distance);
    }

    @Override
    public void setSpeed(String speed) {
        speed_run.setText(speed);
    }

    @Override
    public void setDuration(String duration) {
        duration_run.setText(duration);
    }

    @Override
    public String getDistance() {
        return distance_run.getText().toString();
    }

    /*start add by haoyi.pan on 2017-9-30*/
    @Override
    public void setStep(int step) {
        step_run.setText(""+step);
    }
    /*end add by haoyi.pan on 2017-9-30*/

    @Override
    public long getDuration() {
        return  runActivityPresenter.convertStrToLong(duration_run.getText().toString());
    }

    /*start add by haoyi.pan on 2017-9-30*/
    @Override
    public int getStep() {
        return Integer.valueOf(step_run.getText().toString());
    }
    /*end add by haoyi.pan on 2017-9-30*/

    @Override
    public String getSpeed() {
        return duration_run.getText().toString();
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
        runActivityPresenter.unbindLocationService(this);
        runActivityPresenter.stopLocationService(this);
        runActivityPresenter.stopStepService(this);
        runActivityPresenter.stopTime();
    }

}
