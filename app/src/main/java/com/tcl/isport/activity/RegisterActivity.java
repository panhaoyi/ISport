package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.tcl.isport.R;
import com.tcl.isport.iview.IRegisterActivity;
import com.tcl.isport.presenter.RegisterPresenter;

/**
 * Created by user on 17-8-21.
 */
public class RegisterActivity extends Activity implements View.OnClickListener,IRegisterActivity {
    //注册界面
    private EditText phoneNumber,verification, password;
    //获取验证码，跳转到登录界面
    private TextView getVerification,login;
    private Button register;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        //隐藏虚拟按键
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_IMMERSIVE);
        phoneNumber = (EditText) findViewById(R.id.phonenumber_register);
        verification= (EditText) findViewById(R.id.verification_register);
        password = (EditText) findViewById(R.id.password_register);
        getVerification= (TextView) findViewById(R.id.get_verification_register);
        getVerification.setOnClickListener(this);
        register = (Button) findViewById(R.id.register_register);
        register.setOnClickListener(this);
        login= (TextView) findViewById(R.id.login_register);
        login.setOnClickListener(this);

        registerPresenter=new RegisterPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_verification_register:
                //点击获取验证码，服务器发送验证码到手机
//                registerPresenter.sendVerification();
//                Toast.makeText(this,"已发送验证码",Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_register:
                //注册
//                registerPresenter.register();
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
        return verification.getText().toString();
    }
}
