package com.tcl.isport.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tcl.isport.R;

/**
 * Created by haoyi.pan on 17-9-20.
 */
public class LoginQuickActivity extends Activity implements View.OnFocusChangeListener,View.OnClickListener {
    //快速登录界面
    private EditText phonenumber, verification;
    private TextView getVerification,password_login;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_quick);
        phonenumber= (EditText) findViewById(R.id.phonenumber_quick);
        phonenumber.setOnFocusChangeListener(this);
        verification= (EditText) findViewById(R.id.verification_quick);
        verification.setOnFocusChangeListener(this);
        getVerification= (TextView) findViewById(R.id.get_verification_quick);
        getVerification.setOnClickListener(this);
        password_login= (TextView) findViewById(R.id.login_by_password);
        password_login.setOnClickListener(this);
        login= (Button) findViewById(R.id.login_quick);
        login.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //聚焦置空，焦点移除显示提示
        switch (v.getId()) {
            case R.id.phonenumber_quick:
                if (phonenumber.hasFocus()&&phonenumber.getText().toString().equals("请输入手机号")) {
                    phonenumber.setText("");
                } else if (!phonenumber.hasFocus()&&phonenumber.getText().toString().equals("")) {
                    phonenumber.setText("请输入手机号");
                }
                break;
            case R.id.verification_quick:
                if (verification.hasFocus()&&verification.getText().toString().equals("请输入验证码")) {
                    verification.setText("");
                    verification.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else if (!verification.hasFocus()&&verification.getText().toString().equals("")) {
                    verification.setText("请输入验证码");
                    verification.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            default:
                break;
        }
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
