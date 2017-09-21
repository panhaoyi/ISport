package com.tcl.isport.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.tcl.isport.IView.ISportActivity;
import com.tcl.isport.Presenter.SportActivityPresenter;
import com.tcl.isport.R;

/**
 * Created by user on 17-9-8.
 */
public class WalkActivity extends Activity implements View.OnClickListener,ISportActivity {
    //主界面-运动-健走-Go
    //开始/暂停/停止运动，计步计时记里程，拍照发话题

    private Button camera_walk, start_pause_walk, stop_walk;

    private SportActivityPresenter walkActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.layout_walk);

        //初始化view
        camera_walk = (Button) findViewById(R.id.camera_walk);
        camera_walk.setOnClickListener(this);
        start_pause_walk = (Button) findViewById(R.id.start_pause_walk);
        start_pause_walk.setOnClickListener(this);
        stop_walk = (Button) findViewById(R.id.stop_walk);
        stop_walk.setOnClickListener(this);
        walkActivityPresenter=new SportActivityPresenter(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //重置点击android返回按钮为返回上一个界面，不销毁当前的activity以便继续记录运动信息
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_walk:

                break;
            case R.id.start_pause_walk:

                break;
            case R.id.stop_walk:

                break;
            default:
                break;
        }
    }
}
