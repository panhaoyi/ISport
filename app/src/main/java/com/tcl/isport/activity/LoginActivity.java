package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.ILoginActivity;
import com.tcl.isport.R;
import com.tcl.isport.presenter.LoginPresenter;
import com.tcl.isport.util.UserUtil;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener, ILoginActivity {
    //程序入口，登录界面
    //帐号，密码输入框
    private EditText phonenumber, password;
    //登陆，注册，使用按钮
    private Button login;
    //快速登录，手机注册，忘记密码，trying为第三方登录文字，作为开发时入口方便测试
    private TextView quicklogin, register, forget_password;
    private LoginPresenter loginPresenter;
    private Intent intent;
    private String STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private String PHONE_PERMISSION = "android.permission.READ_PHONE_STATE";
    private String LOCATION_PERMISSION = "android.permission.ACCESS_FINE_LOCATION";
    private String CONTACTS_PERMISSION = "android.permission.GET_ACCOUNTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //使用SharedPreference保存app启动状态
        SharedPreferences preferences;
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        //判断是否第一次启动
        boolean isFirst = preferences.getBoolean("isFirst", true);
        if (isFirst) {
            //第一次启动，进入引导页，并将isFirst设为false
            Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
        }
        //不是第一次启动，加载登录界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this, R.id.layout_login);

        phonenumber = (EditText) findViewById(R.id.phonenumber_login);
        password = (EditText) findViewById(R.id.password_login);
        login = (Button) findViewById(R.id.login_login);
        login.setOnClickListener(this);
        quicklogin = (TextView) findViewById(R.id.quicklogin_login);
        quicklogin.setOnClickListener(this);
        register = (TextView) findViewById(R.id.register_login);
        register.setOnClickListener(this);
        forget_password = (TextView) findViewById(R.id.forget_pasword);
        forget_password.setOnClickListener(this);
        initPermission();

        loginPresenter = new LoginPresenter(this);
        //如果已经登录成功，则不用再登录！
        if (AVUser.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        }
    }

    public void initPermission() {
        //动态请求权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, STORAGE_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(STORAGE_PERMISSION);
        }
        if (ContextCompat.checkSelfPermission(this, PHONE_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(PHONE_PERMISSION);
        }
        if (ContextCompat.checkSelfPermission(this, LOCATION_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(LOCATION_PERMISSION);
        }
        if (ContextCompat.checkSelfPermission(this, CONTACTS_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(CONTACTS_PERMISSION);
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 666);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                //登陆
                attemptLogin();
                break;
            case R.id.quicklogin_login:
                //跳转到快速登录界面
                intent = new Intent(LoginActivity.this, LoginQuickActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.register_login:
                //点击注册，跳转到注册界面
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void attemptLogin() {
        String phone = phonenumber.getText().toString();
        String pwd = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)) {
            showLoginToast("手机号码为空！");
            focusView = phonenumber;
            cancel = true;
        }else if (!UserUtil.checkValidPhoneNumber(phone)) {
            showLoginToast("输入手机号码非法！");
            focusView = phonenumber;
            cancel = true;
        }else if (TextUtils.isEmpty(pwd)) {
            showLoginToast("输入密码为空值！");
            focusView = password;
            cancel = true;
        }else if (!isPasswordValid(pwd)) {
            showLoginToast("密码长度小于6位！");
            focusView = password;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            loginPresenter.startLogin(phone, pwd);
        }

    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    @Override
    public void successLogin() {
        //登录成功，开始跳转主页面，并销毁此界面
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void failLogin() {

        showLoginToast("登录失败，手机号或密码错误！");
    }

    private void showLoginToast(String content) {
        Toast.makeText(LoginActivity.this, content, Toast.LENGTH_SHORT).show();
    }
}