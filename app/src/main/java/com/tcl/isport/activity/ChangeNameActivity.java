package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-26.
 */
public class ChangeNameActivity extends Activity implements  View.OnClickListener,TextWatcher {
    private EditText name;
    private TextView cancel,confirm,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_name);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_change_name);

        name= (EditText) findViewById(R.id.name_change_name);
        name.addTextChangedListener(this);
        cancel= (TextView) findViewById(R.id.cancel_change_name);
        cancel.setOnClickListener(this);
        confirm= (TextView) findViewById(R.id.confirm_change_name);
        confirm.setOnClickListener(this);
        number= (TextView) findViewById(R.id.number_change_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_change_name:
                //取消更改
                finish();
                break;
            case R.id.confirm_change_name:
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
