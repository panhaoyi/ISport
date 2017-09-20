package com.tcl.isport.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.isport.Bean.User;
import com.tcl.isport.R;
import com.tcl.isport.Util.DBOpenHelper;

/**
 * Created by user on 17-8-21.
 */
public class RegisterActivity extends Activity implements View.OnFocusChangeListener, View.OnClickListener {
    //注册界面
    private EditText phonenumber,verification, password;
    //获取验证码，跳转到登录界面
    private TextView getVerification,login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        phonenumber = (EditText) findViewById(R.id.phonenumber_register);
        phonenumber.setOnFocusChangeListener(this);
        verification= (EditText) findViewById(R.id.verification_register);
        verification.setOnFocusChangeListener(this);
        password = (EditText) findViewById(R.id.password_register);
        password.setOnFocusChangeListener(this);
        getVerification= (TextView) findViewById(R.id.get_verification_register);
        getVerification.setOnClickListener(this);
        register = (Button) findViewById(R.id.register_register);
        register.setOnClickListener(this);
        login= (TextView) findViewById(R.id.login_register);
        login.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //聚焦置空，焦点移除显示提示
        switch (v.getId()) {
            case R.id.phonenumber_register:
                if (phonenumber.hasFocus() && phonenumber.getText().toString().equals("请输入手机号")) {
                    phonenumber.setText("");
                } else if (!phonenumber.hasFocus() && phonenumber.getText().toString().equals("")) {
                    phonenumber.setText("请输入手机号");
                }
                break;
            case R.id.verification_register:
                if (verification.hasFocus() && verification.getText().toString().equals("请输入验证码")) {
                    verification.setText("");
                    verification.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else if (!verification.hasFocus() && verification.getText().toString().equals("")) {
                    verification.setText("请输入验证码");
                    verification.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            case R.id.password_register:
                if (password.hasFocus() && password.getText().toString().equals("请输入密码")) {
                    password.setText("");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else if (!password.hasFocus() && password.getText().toString().equals("")) {
                    password.setText("请输入密码");
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_verification_register:
                //点击获取验证码，服务器发送验证码到手机

                break;
            case R.id.register_register:
                //注册

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
}
