package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.isport.iview.ISportActivity;
import com.tcl.isport.presenter.SportActivityPresenter;
import com.tcl.isport.R;


public class RunActivity extends Activity implements View.OnClickListener,ISportActivity {
    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，拍照发话题

    private TextView distance_run,speed_run,duration_run;
    private ImageView map_run;
    private Button camera_run, start_pause_run, stop_run;

    private SportActivityPresenter runActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_run);

        //初始化view
        distance_run= (TextView) findViewById(R.id.distance_run);
        speed_run= (TextView) findViewById(R.id.speed_run);
        duration_run= (TextView) findViewById(R.id.duration_run);
        map_run= (ImageView) findViewById(R.id.map_run);
        map_run.setOnClickListener(this);
        camera_run = (Button) findViewById(R.id.camera_run);
        camera_run.setOnClickListener(this);
        start_pause_run = (Button) findViewById(R.id.start_pause_run);
        start_pause_run.setOnClickListener(this);
        stop_run = (Button) findViewById(R.id.stop_run);
        stop_run.setOnClickListener(this);
        runActivityPresenter=new SportActivityPresenter(this);
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
                Intent intent=new Intent(RunActivity.this,MapActivity.class);
                //将当前Activity的class名字通过intent传到MapActivity以便于返回
                intent.putExtra("className",this.getClass().getName());
                //设置flag使activity不会被销毁
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.camera_run:

                break;
            case R.id.start_pause_run:

                break;
            case R.id.stop_run:

                break;
            default:
                break;
        }
    }

    @Override
    public void setDistance(String distance) {
        distance_run.setText(distance+"");
    }

    @Override
    public void setSpeed(String speed) {
        speed_run.setText(speed);
    }

    @Override
    public void setDuration(String duration) {
        duration_run.setText(duration);
    }
}
