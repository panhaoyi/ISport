package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class ActivityTimeActivity extends Activity implements View.OnClickListener, TextWatcher {
    //主界面-社交-活动-发布活动-活动主题
    private EditText time;
    private TextView cancel, tag, confirm, number;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_theme);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this, R.id.layout_activity_theme);

        time = (EditText) findViewById(R.id.theme_activity_theme);
        time.addTextChangedListener(this);
        time.setHint("时间格式如:2017-09-01 13:01:00");
        cancel = (TextView) findViewById(R.id.cancel_activity_theme);
        cancel.setOnClickListener(this);
        tag = (TextView) findViewById(R.id.tag_activity_theme);
        confirm = (TextView) findViewById(R.id.confirm_activity_theme);
        confirm.setOnClickListener(this);
        number = (TextView) findViewById(R.id.number_activity_theme);
        number.setText("0/19");
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        tag.setText(type);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_activity_theme:
                //取消更改
                finish();
                break;
            case R.id.confirm_activity_theme:
                //确定更改
//                Pattern pattern = Pattern.compile("^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$");
                Pattern pattern = Pattern.compile("(\\d{2}|\\d{4})(?:\\-)?([0]{1}\\d{1}|[1]{1}[0-2]{1})(?:\\-)?([0-2]{1}\\d{1}|[3]{1}[0-1]{1})(?:\\s)?([0-1]{1}\\d{1}|[2]{1}[0-3]{1})(?::)?([0-5]{1}\\d{1})(?::)?([0-5]{1}\\d{1})");
                Matcher matcher = pattern.matcher(time.getText().toString());
                if ("活动时间".equals(type)&&matcher.matches()) {
                    Intent data = new Intent();
                    data.putExtra("time", time.getText().toString());
                    setResult(5, data);
                    finish();
                }else if ("截止时间".equals(type)&&matcher.matches()) {
                    Intent data = new Intent();
                    data.putExtra("deadline", time.getText().toString());
                    setResult(7, data);
                    finish();
                }else if ("出生日期".equals(type)&&matcher.matches()) {
                    Intent data = new Intent();
                    data.putExtra("deadline", time.getText().toString());
                    setResult(10, data);
                    finish();
                }else {
                    Toast.makeText(this, "请输入正确的时间!", Toast.LENGTH_SHORT).show();
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
        if (s.length() > 19) {
            s.delete(19, s.length());
        } else {
            number.setText(s.length() + "/19");
        }
    }
}
