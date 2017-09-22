package com.tcl.isport.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.isport.IView.ISportActivity;
import com.tcl.isport.Presenter.SportActivityPresenter;
import com.tcl.isport.R;

/**
 * Created by user on 17-9-8.
 */
public class RideActivity extends Activity implements View.OnClickListener,ISportActivity {
    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，拍照发话题

    private TextView distance_ride,speed_ride,duration_ride;
    private ImageView map_ride;
    private Button camera_ride, start_pause_ride, stop_ride;

    private SportActivityPresenter rideActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ride);

        //初始化view
        distance_ride= (TextView) findViewById(R.id.distance_ride);
        speed_ride= (TextView) findViewById(R.id.speed_ride);
        duration_ride= (TextView) findViewById(R.id.duration_ride);
        map_ride= (ImageView) findViewById(R.id.map_ride);
        map_ride.setOnClickListener(this);
        camera_ride = (Button) findViewById(R.id.camera_ride);
        camera_ride.setOnClickListener(this);
        start_pause_ride = (Button) findViewById(R.id.start_pause_ride);
        start_pause_ride.setOnClickListener(this);
        stop_ride = (Button) findViewById(R.id.stop_ride);
        stop_ride.setOnClickListener(this);
        rideActivityPresenter=new SportActivityPresenter(this);
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
                Intent intent=new Intent(RideActivity.this,MapActivity.class);
                //将当前Activity的class名字通过intent传到MapActivity以便于返回
                intent.putExtra("className",this.getClass().getName());
                //设置flag使activity不会被销毁
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.camera_ride:

                break;
            case R.id.start_pause_ride:

                break;
            case R.id.stop_ride:

                break;
            default:
                break;
        }
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
}
