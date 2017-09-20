package com.tcl.isport.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.isport.IView.ILoginActivity;
import com.tcl.isport.R;
import com.tcl.isport.Util.DBOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements View.OnFocusChangeListener, View.OnClickListener,ILoginActivity {
    //程序入口，登录界面
    //帐号，密码输入框
    private EditText phonenumber, password;
    //登陆，注册，使用按钮
    private Button login;
    //快速登录，手机注册，忘记密码，trying为第三方登录文字，作为开发时入口方便测试
    private TextView quicklogin, register,forget_password;
    /////////开发完成后删除start////////
    private TextView trying;
    /////////开发完成后删除end////////
    private Intent intent;
    private String STORAGE_PERMISSION="android.permission.WRITE_EXTERNAL_STORAGE";
    private String PHONE_PERMISSION="android.permission.READ_PHONE_STATE";
    private String LOCATION_PERMISSION="android.permission.ACCESS_FINE_LOCATION";
    private String CONTACTS_PERMISSION="android.permission.GET_ACCOUNTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //使用SharedPreference保存app启动状态
        SharedPreferences preferences;
        preferences=getSharedPreferences("user",MODE_PRIVATE);
        //判断是否第一次启动
        boolean isFirst = preferences.getBoolean("isFirst",true);
        if(isFirst){
            //第一次启动，进入引导页，并将isFirst设为false
            Intent intent=new Intent(LoginActivity.this,WelcomeActivity.class);
            startActivity(intent);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirst",false);
            editor.apply();
        }
        //不是第一次启动，加载登录界面
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        phonenumber = (EditText) findViewById(R.id.phonenumber_login);
        phonenumber.setOnFocusChangeListener(this);
        password = (EditText) findViewById(R.id.password_login);
        password.setOnFocusChangeListener(this);
        login = (Button) findViewById(R.id.login_login);
        login.setOnClickListener(this);
        quicklogin= (TextView) findViewById(R.id.quicklogin_login);
        quicklogin.setOnClickListener(this);
        register = (TextView) findViewById(R.id.register_login);
        register.setOnClickListener(this);
        forget_password= (TextView) findViewById(R.id.forget_pasword);
        forget_password.setOnClickListener(this);
        /////////开发完成后删除start////////
        trying = (TextView) findViewById(R.id.trying_login);
        trying.setOnClickListener(this);
        /////////开发完成后删除end////////
        initPermission();
    }

    public void initPermission(){
        //动态请求权限
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this,STORAGE_PERMISSION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(STORAGE_PERMISSION);
        }
        if(ContextCompat.checkSelfPermission(this,PHONE_PERMISSION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(PHONE_PERMISSION);
        }
        if(ContextCompat.checkSelfPermission(this,LOCATION_PERMISSION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(LOCATION_PERMISSION);
        }
        if(ContextCompat.checkSelfPermission(this,CONTACTS_PERMISSION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(CONTACTS_PERMISSION);
        }
        if(!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),666);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //聚焦置空，焦点移除显示提示
        switch (v.getId()) {
            case R.id.phonenumber_login:
                if (phonenumber.hasFocus()&&phonenumber.getText().toString().equals("请输入手机号")) {
                    phonenumber.setText("");
                } else if (!phonenumber.hasFocus()&&phonenumber.getText().toString().equals("")) {
                    phonenumber.setText("请输入手机号");
                }
                break;
            case R.id.password_login:
                if (password.hasFocus()&&password.getText().toString().equals("请输入密码")) {
                    password.setText("");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else if (!password.hasFocus()&&password.getText().toString().equals("")) {
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
            case R.id.login_login:
                //登陆

                break;
            case R.id.quicklogin_login:
                //跳转到快速登录界面
                intent=new Intent(LoginActivity.this,LoginQuickActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.register_login:
                //点击注册，跳转到注册界面
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            /////////开发完成后删除start////////
            case R.id.trying_login:
                //点击第三方登录，跳转到主界面
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            /////////开发完成后删除end////////
            default:
                break;
        }
    }
}