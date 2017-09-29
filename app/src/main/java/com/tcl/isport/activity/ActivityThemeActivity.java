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
import android.widget.Toast;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class ActivityThemeActivity extends Activity implements  View.OnClickListener,TextWatcher {
    //主界面-社交-活动-发布活动-活动主题
    private EditText theme;
    private TextView cancel,tag,confirm,number;
    private String type;
    private int textNum=24;

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
        tag= (TextView) findViewById(R.id.tag_activity_theme);
        confirm= (TextView) findViewById(R.id.confirm_activity_theme);
        confirm.setOnClickListener(this);
        number= (TextView) findViewById(R.id.number_activity_theme);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        if("活动地点".equals(type)){
            textNum=30;
            number.setText("0/"+textNum);
            tag.setText(type);
        }
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
                if(!"".equals(theme.getText().toString())&&"活动主题".equals(type)) {
                    Intent data = new Intent();
                    data.putExtra("theme", theme.getText().toString());
                    setResult(1, data);
                    finish();
                }else if(!"".equals(theme.getText().toString())&&"活动地点".equals(type)) {
                    Intent data = new Intent();
                    data.putExtra("location", theme.getText().toString());
                    setResult(6, data);
                    finish();
                }else{
                    Toast.makeText(this,"请输入主题!",Toast.LENGTH_SHORT).show();
                }
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
        if (s.length()>textNum){
            s.delete(textNum,s.length());
        }
        else{
            number.setText(s.length()+"/"+textNum);
        }
    }
}
