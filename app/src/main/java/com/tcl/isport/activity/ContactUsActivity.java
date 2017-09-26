package com.tcl.isport.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-12.
 */
public class ContactUsActivity extends Activity implements View.OnClickListener {
    //主界面-我-联系我们
    private ImageView return_contact_us;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contact_us);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_contact_us);

        return_contact_us = (ImageView) findViewById(R.id.return_contact_us);
        return_contact_us.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //返回按钮
        switch (v.getId()) {
            case R.id.return_contact_us:
                finish();
                break;
            default:
                break;
        }
    }
}
