package com.tcl.isport.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class ActivityContentActivity extends Activity implements  View.OnClickListener,TextWatcher {
    //主界面-社交-活动-发布活动-活动简介
    private TextView cancel,confirm,number;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_content);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_activity_content);

        content= (EditText) findViewById(R.id.content_activity_content);
        content.addTextChangedListener(this);
        cancel= (TextView) findViewById(R.id.cancel_activity_content);
        cancel.setOnClickListener(this);
        confirm= (TextView) findViewById(R.id.confirm_activity_content);
        confirm.setOnClickListener(this);
        number= (TextView) findViewById(R.id.number_activity_content);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_activity_content:
                //取消更改
                finish();
                break;
            case R.id.confirm_activity_content:
                //确定更改
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length()>600){
            s.delete(600,s.length());
        }
        else{
            number.setText(s.length()+"/600");
        }
    }
}
