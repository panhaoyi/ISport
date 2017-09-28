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
public class ActivityThemeActivity extends Activity implements  View.OnClickListener,TextWatcher {
    //主界面-社交-活动-发布活动-活动主题
    private EditText theme;
    private TextView cancel,confirm,number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_theme);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_activity_theme);

        theme= (EditText) findViewById(R.id.theme_activity_theme);
        theme.addTextChangedListener(this);
        cancel= (TextView) findViewById(R.id.cancel_activity_theme);
        cancel.setOnClickListener(this);
        confirm= (TextView) findViewById(R.id.confirm_activity_theme);
        confirm.setOnClickListener(this);
        number= (TextView) findViewById(R.id.number_activity_theme);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_activity_theme:
                //取消更改
                finish();
                break;
            case R.id.confirm_activity_theme:
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
        if (s.length()>24){
            s.delete(24,s.length());
        }
        else{
            number.setText(s.length()+"/24");
        }
    }
}
