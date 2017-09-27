package com.tcl.isport.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-27.
 */
public class NewTopicActivity extends Activity implements TextWatcher,View.OnClickListener{
    private EditText content;
    private TextView cancel,confirm,number;
    private ImageView addPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_topic);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_new_topic);

        content= (EditText) findViewById(R.id.content_new_topic);
        content.addTextChangedListener(this);
        cancel= (TextView) findViewById(R.id.cancel_new_topic);
        cancel.setOnClickListener(this);
        confirm= (TextView) findViewById(R.id.confirm_new_topic);
        confirm.setOnClickListener(this);
        addPicture= (ImageView) findViewById(R.id.add_picture_new_topic);
        addPicture.setOnClickListener(this);
        number= (TextView) findViewById(R.id.number_new_topic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_new_topic:
                //取消更改
                finish();
                break;
            case R.id.confirm_new_topic:
                //确定更改
                break;
            case R.id.add_picture_new_topic:
                //点击选择图片
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
        if (s.length()>140){
            s.delete(140,s.length());
        }
        else{
            number.setText(s.length()+"/140");
        }
    }
}
