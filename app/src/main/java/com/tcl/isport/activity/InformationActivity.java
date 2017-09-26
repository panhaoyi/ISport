package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-26.
 */
public class InformationActivity extends Activity implements OnClickListener {
    //主界面-我-资料编辑
    private ImageView back;
    private RelativeLayout changePhoto,changeName,changeSex,changeBirth,changeCity,changeSignature;
    private TextView name,sex,birth,city,signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_information);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_information);

        back= (ImageView) findViewById(R.id.back_information);
        back.setOnClickListener(this);
        changePhoto= (RelativeLayout) findViewById(R.id.changePhoto_information);
        changePhoto.setOnClickListener(this);
        changeName= (RelativeLayout) findViewById(R.id.changeName_information);
        changeName.setOnClickListener(this);
        changeSex= (RelativeLayout) findViewById(R.id.changeSex_information);
        changeSex.setOnClickListener(this);
        changeBirth= (RelativeLayout) findViewById(R.id.changeBirth_information);
        changeBirth.setOnClickListener(this);
        changeCity= (RelativeLayout) findViewById(R.id.changeCity_information);
        changeCity.setOnClickListener(this);
        changeSignature= (RelativeLayout) findViewById(R.id.changeSignature_information);
        changeSignature.setOnClickListener(this);
        name= (TextView) findViewById(R.id.name_information);
        sex= (TextView) findViewById(R.id.sex_information);
        birth= (TextView) findViewById(R.id.birth_information);
        city= (TextView) findViewById(R.id.city_information);
        signature= (TextView) findViewById(R.id.signature_information);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_information:
                finish();
                break;
            case R.id.changePhoto_information:
                break;
            case R.id.changeName_information:
                Intent intent=new Intent(this,ChangeNameActivity.class);
                startActivity(intent);
                break;
            case R.id.changeSex_information:
                break;
            case R.id.changeBirth_information:
                break;
            case R.id.changeCity_information:
                break;
            case R.id.changeSignature_information:
                break;

        }
    }
}
