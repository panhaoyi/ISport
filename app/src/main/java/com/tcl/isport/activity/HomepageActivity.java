package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.IHomepageActivity;

/**
 * Created by haoyi.pan on 17-9-27.
 */
public class HomepageActivity extends Activity implements View.OnClickListener,IHomepageActivity {
    //个人主页,主界面-我-个人信息/点击其他用户进入用户个人主页
    private ImageView back, add, photo;
    private TextView city, lv, distance, duration;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_homepage);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this, R.id.layout_homepage);

        back = (ImageView) findViewById(R.id.back_homepage);
        back.setOnClickListener(this);
        add = (ImageView) findViewById(R.id.add_homepage);
        add.setOnClickListener(this);
        photo = (ImageView) findViewById(R.id.photo_homepage);
        city = (TextView) findViewById(R.id.city_homepage);
        lv = (TextView) findViewById(R.id.lv_homepage);
        distance = (TextView) findViewById(R.id.distance_homepage);
        duration = (TextView) findViewById(R.id.duration_homepage);
        listView= (ListView) findViewById(R.id.listview_homepage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_homepage:
                finish();
                break;
            case R.id.add_homepage:

                break;
            default:
                break;
        }
    }

    @Override
    public void setPhoto() {

    }

    @Override
    public void setCity(String city) {
        this.city.setText(city);
    }

    @Override
    public void setLV(String lv) {
        this.lv.setText(lv);
    }

    @Override
    public void setDistance(String distance) {
        this.distance.setText(distance);
    }

    @Override
    public void setDuration(String duration) {
        this.duration.setText(duration);
    }
}
