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
import com.tcl.isport.iview.IRegisterActivity;
import com.tcl.isport.presenter.RegisterPresenter;
import com.tcl.isport.util.UserUtil;

/**
 * Created by user on 17-8-21.
 */
public class FindPasswordActivity extends Activity implements View.OnClickListener {
    //注册界面
    private EditText phoneNumber,verification, password;
    //获取验证码，跳转到登录界面
    private TextView login,getVerification;
    private Button change_password;

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_password_find_password:
                //修改密码按钮

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

}
