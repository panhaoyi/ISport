package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.IRegisterActivity;
import com.tcl.isport.presenter.RegisterPresenter;
import com.tcl.isport.util.UserUtil;

/**
 * Created by user on 17-8-21.
 */
public class RegisterActivity extends Activity implements View.OnClickListener,IRegisterActivity {
    //注册界面
    private EditText phoneNumber,password_first, password;
    //获取验证码，跳转到登录界面
    private TextView login;
    private Button register;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        //隐藏虚拟按键
        MyApplication.hide(this,R.id.layout_register);
        phoneNumber = (EditText) findViewById(R.id.phonenumber_register);
        phoneNumber.setOnFocusChangeListener(onFocusChangeListener);
        password_first= (EditText) findViewById(R.id.password_register_first);
        password = (EditText) findViewById(R.id.password_register);
//        getVerification= (TextView) findViewById(R.id.get_verification_register);
//        getVerification.setOnClickListener(this);
        register = (Button) findViewById(R.id.register_register);
        register.setOnClickListener(this);
        login= (TextView) findViewById(R.id.login_register);
        login.setOnClickListener(this);

        registerPresenter=new RegisterPresenter(this);
    }

    //phoneNumber得到和失去焦点的处理
    View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                //得到焦点处理
            } else {
                //失去焦点处理
                if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    showRegisterToast("手机号码为空！");
                    return;
                }
                if (!UserUtil.checkValidPhoneNumber(phoneNumber.getText().toString())) {
                    showRegisterToast("输入手机号码非法！");
                    return;
                }

                //判断手机号码有没有被注册过
                registerPresenter.checkUser(phoneNumber.getText().toString());

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_register:
                //注册
                attemptRegister();
                break;
            case R.id.login_register:
                //切换到登录界面
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void attemptRegister(){

        String phone = phoneNumber.getText().toString();
        String pwd_first = password_first.getText().toString();
        String pwd = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)) {
            showRegisterToast("手机号码为空！");
            focusView = phoneNumber;
            cancel = true;
        }else if (!UserUtil.checkValidPhoneNumber(phone)) {
            showRegisterToast("输入手机号码非法！");
            focusView = phoneNumber;
            cancel = true;
        }else if (TextUtils.isEmpty(pwd_first)) {
            showRegisterToast("第一次密码为空值！");
            focusView = password_first;
            cancel = true;
        }else if (!isPasswordValid(pwd_first)) {
            showRegisterToast("第一次密码长度小于6位！");
            focusView = password_first;
            cancel = true;
        }else if (TextUtils.isEmpty(pwd)) {
            showRegisterToast("第二次输入密码为空值！");
            focusView = password;
            cancel = true;
        }else if (!isPasswordValid(pwd)) {
            showRegisterToast("第二次密码长度小于6位！");
            focusView = password;
            cancel = true;
        } else if (!TextUtils.equals(pwd_first, pwd)) {
            showRegisterToast("两次输入密码不一致！");
            focusView = password;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            registerPresenter.registerUser(phone, pwd);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    private void showRegisterToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
    @Override
    public String getPhoneNumber() {
        return phoneNumber.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public String getVerification() {
        return password_first.getText().toString();
    }

    @Override
    public void failCheck() {
        //手机号码被占用
        showRegisterToast("此手机号码已被注册！");
    }

    @Override
    public void successRegister() {
        //注册成功，结束此activity
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void failRegister() {
        showRegisterToast("注册失败！");
    }
}
