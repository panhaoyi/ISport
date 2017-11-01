package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.IFindPasswordActivity;
import com.tcl.isport.iview.IRegisterActivity;
import com.tcl.isport.presenter.LoginPresenter;
import com.tcl.isport.presenter.RegisterPresenter;
import com.tcl.isport.util.UserUtil;

/**
 * Created by user on 17-8-21.
 */
public class FindPasswordActivity extends Activity implements IFindPasswordActivity, View.OnClickListener {
    //注册界面
    private EditText phoneNumber,verification, password;
    //获取验证码，跳转到登录界面
    private TextView login,getVerification;
    private Button change_password;
    private LoginPresenter loginPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_find_password);
        //隐藏虚拟按键
        MyApplication.hide(this,R.id.layout_find_password);
        phoneNumber = (EditText) findViewById(R.id.phonenumber_find_password);
        verification= (EditText) findViewById(R.id.verification_find_password);
        password = (EditText) findViewById(R.id.password_find_password);
        getVerification= (TextView) findViewById(R.id.get_verification_find_password);
        getVerification.setOnClickListener(this);
        change_password = (Button) findViewById(R.id.change_password_find_password);
        change_password.setOnClickListener(this);
        login= (TextView) findViewById(R.id.login_find_password);
        login.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password_find_password:
                //修改密码按钮
                attemptReset();
                break;
            case R.id.get_verification_find_password:
                //获取验证码
                showToast("正在获取验证码中...");
                attemptVerify();
                break;
            case R.id.login_find_password:
                //切换到登录界面
                Intent intent=new Intent(FindPasswordActivity.this,LoginActivity.class);
                finish();
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void attemptVerify(){
        String phone = phoneNumber.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(phone)) {
            showToast("手机号码为空！");
            focusView = phoneNumber;
            cancel = true;
        }else if (!UserUtil.checkValidPhoneNumber(phone)) {
            showToast("输入手机号码非法！");
            focusView = phoneNumber;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            focusView = verification;
            focusView.requestFocus();
            loginPresenter.getResetVerification(phone);
        }
    }

    private void attemptReset(){
        String verify = verification.getText().toString();
        String pwd = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(verify)) {
            showToast("验证码为空值！");
            focusView = verification;
            cancel = true;
        }else if (TextUtils.isEmpty(pwd)) {
            showToast("输入密码为空值！");
            focusView = password;
            cancel = true;
        }else if (!isPasswordValid(pwd)) {
            showToast("密码长度小于6位！");
            focusView = password;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            loginPresenter.resetPwd(verify, pwd);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    private void showToast(String content) {
        Toast.makeText(FindPasswordActivity.this, content, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void successVerificaton(boolean isFlag) {
        if (!isFlag) {
            showToast("获取验证码失败！");
        }
    }

    @Override
    public void successReset(boolean isFlag) {

        if (isFlag) {
            showToast("重置密码成功，返回登录界面！");
            Intent intent = new Intent(FindPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
            FindPasswordActivity.this.finish();
        } else {
            showToast("修改密码失败！");
        }
    }
}
