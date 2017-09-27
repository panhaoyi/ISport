package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by user on 17-9-8.
 */
public class WalkActivity extends Activity implements View.OnClickListener,ISportActivity {
    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，拍照发话题

    private TextView distance_walk,speed_walk,duration_walk;
    private ImageView map_walk,camera_walk, start_pause_walk, stop_walk;
    private String start_pause = "pause";
    private SportActivityPresenter walkActivityPresenter;
    private Intent intent;

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
        camera_walk = (ImageView) findViewById(R.id.camera_walk);
        camera_walk.setOnClickListener(this);
        start_pause_walk = (ImageView) findViewById(R.id.start_pause_walk);
        start_pause_walk.setOnClickListener(this);
        stop_walk = (ImageView) findViewById(R.id.stop_walk);
        stop_walk.setOnClickListener(this);
        walkActivityPresenter=new SportActivityPresenter(this);
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
                intent.putExtra("className",this.getClass().getName());
                //设置flag使activity不会被销毁
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.camera_walk:
                intent=new Intent(WalkActivity.this,CameraActivity.class);
                startActivity(intent);
                break;
            case R.id.start_pause_walk:
                //点击开始/暂停,切换图标并开始/暂停运动
                if (start_pause.equals("pause")){
                    start_pause_walk.setImageResource(R.drawable.bt_start);
                    start_pause="start";
                }
                else{
                    start_pause_walk.setImageResource(R.drawable.bt_pause);
                    start_pause="pause";
                }
                break;
            case R.id.stop_walk:

                break;
            default:
                break;
        }
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
}
