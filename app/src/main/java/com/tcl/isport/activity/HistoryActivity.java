package com.tcl.isport.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by user on 17-9-12.
 */
public class HistoryActivity extends Activity implements View.OnClickListener {
    //主界面-我-历史记录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_history);
    }

    @Override
    public void onClick(View v) {

    }
}
