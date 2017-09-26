package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-20.
 */
public class LoginQuickActivity extends Activity implements View.OnClickListener {
    //快速登录界面
    private EditText phonenumber, verification;
    private TextView getVerification,password_login;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_quick);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_login_quick);

        phonenumber= (EditText) findViewById(R.id.phonenumber_quick);
        verification= (EditText) findViewById(R.id.verification_quick);
        getVerification= (TextView) findViewById(R.id.get_verification_quick);
        getVerification.setOnClickListener(this);
        password_login= (TextView) findViewById(R.id.login_by_password);
        password_login.setOnClickListener(this);
        login= (Button) findViewById(R.id.login_quick);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_verification_quick:
                //发送验证码到手机

                break;
            case R.id.login_by_password:
                //跳转到密码登录界面
                Intent intent=new Intent(LoginQuickActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.login_quick:
                //登录

                break;
            default:
                break;
        }
    }
}
