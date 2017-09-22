package com.tcl.isport.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tcl.isport.R;

/**
 * Created by haoyi.pan on 17-9-22.
 */
public class MapActivity extends Activity implements View.OnClickListener {
    //运动地图轨迹界面
    private ImageView map_type,my_location,change_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_map);
        map_type= (ImageView) findViewById(R.id.map_type);
        map_type.setOnClickListener(this);
        my_location= (ImageView) findViewById(R.id.my_location);
        my_location.setOnClickListener(this);
        change_view= (ImageView) findViewById(R.id.change_view);
        change_view.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.map_type:

                break;
            case R.id.my_location:

                break;
            case R.id.change_view:
                //获取上一个Activity的名字判断是Walk/Run/Ride以返回到正确的Actiivty
                Intent intent = getIntent();
                String className=intent.getStringExtra("className");
                try{
                    intent=new Intent(this, Class.forName(className));
                    //设置flag使activity不会被销毁
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
