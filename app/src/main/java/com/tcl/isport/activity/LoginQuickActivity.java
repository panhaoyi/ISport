package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.ILoginQuickActivity;
import com.tcl.isport.presenter.LoginPresenter;
import com.tcl.isport.util.UserUtil;

/**
 * Created by haoyi.pan on 17-9-20.
 */
public class LoginQuickActivity extends Activity implements View.OnClickListener, ILoginQuickActivity {
    //快速登录界面
    private EditText phonenumber, verification;
    private TextView getVerification,password_login;
    private Button login;
    private LoginPresenter loginPresenter;

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

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_verification_quick:
                //发送验证码到手机
                attemptVerification();
                break;
            case R.id.login_by_password:
                //跳转到密码登录界面
                Intent intent=new Intent(LoginQuickActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.login_quick:
                //登录
                attemptLoginQuick();
                break;
            default:
                break;
        }
    }



    private void attemptVerification() {

        String phone = phonenumber.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)) {
            showLoginQuickToast("手机号码为空！");
            focusView = phonenumber;
            cancel = true;
        } else if (!UserUtil.checkValidPhoneNumber(phone)) {
            showLoginQuickToast("输入手机号码非法！");
            focusView = phonenumber;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            focusView = verification;
            focusView.requestFocus();
            //开始获取验证码
            loginPresenter.startLoginQuickVerification(phone);

        }
    }

    private void attemptLoginQuick() {
        String phone = phonenumber.getText().toString();
        String verify = verification.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)) {
            showLoginQuickToast("手机号码为空！");
            focusView = phonenumber;
            cancel = true;
        } else if (!UserUtil.checkValidPhoneNumber(phone)) {
            showLoginQuickToast("输入手机号码非法！");
            focusView = phonenumber;
            cancel = true;
        } else if (TextUtils.isEmpty(verify)) {
            showLoginQuickToast("请输入验证码！");
            focusView = verification;
            cancel = true;
        } else if (verify.length() < 6) {
            showLoginQuickToast("验证码非法！");
            focusView = verification;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            //开始通过验证码和手机号快速登录
            loginPresenter.statrtLoginQuick(phone,verify);
        }
    }
    private void showLoginQuickToast(String content) {
        Toast.makeText(LoginQuickActivity.this, content, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void successVerification() {

    }

    @Override
    public void failVerification() {
       showLoginQuickToast("获取验证码失败，此手机号未注册！");
    }

    @Override
    public void successLogin() {
        //登录成功，开始跳转主页面，并销毁此界面
        Intent intent = new Intent(LoginQuickActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void failLogin() {
        showLoginQuickToast("登录失败，手机号与验证码未匹配！");
    }

}
