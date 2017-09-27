package com.tcl.isport.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-27.
 */
public class ActivityDetailActivity extends Activity {
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail);
        //隐藏虚拟按键,,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_activity_detail);
    }
}
