package com.tcl.isport.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button login, register, trying;
    private DBOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Intent intent;
    private String STORAGE_PERMISSION="android.permission.WRITE_EXTERNAL_STORAGE";
    private String PHONE_PERMISSION="android.permission.READ_PHONE_STATE";
    private String LOCATION_PERMISSION="android.permission.ACCESS_FINE_LOCATION";
    private String CONTACTS_PERMISSION="android.permission.GET_ACCOUNTS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        databaseHelper = new DBOpenHelper(LoginActivity.this);
        db = databaseHelper.getReadableDatabase();

        phonenumber = (EditText) findViewById(R.id.phonenumber_login);
        phonenumber.setOnFocusChangeListener(this);
        password = (EditText) findViewById(R.id.password_login);
        password.setOnFocusChangeListener(this);
        login = (Button) findViewById(R.id.login_login);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.register_login);
        register.setOnClickListener(this);
        trying = (Button) findViewById(R.id.trying_login);
        trying.setOnClickListener(this);
        initPermission();
    }

    public void initPermission(){
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
                if (hasFocus && phonenumber.getText().toString().equals("请输入手机号")) {
                    phonenumber.setText("");
                } else if (!hasFocus && phonenumber.getText().toString().equals("")) {
                    phonenumber.setText("请输入手机号");
                }
                break;
            case R.id.password_login:
                if (hasFocus && password.getText().toString().equals("请输入密码")) {
                    password.setText("");
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else if (!hasFocus && password.getText().toString().equals("")) {
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
                if (phonenumber.getText().toString().equals("") || phonenumber.getText().toString().equals("请输入手机号")) {
                    Toast.makeText(LoginActivity.this, "请输入手机号!!", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("") || password.getText().toString().equals("请输入密码")) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    //手机号和密码不为空，登陆验证，登陆成功跳转到主界面并将用户信息存到application中
                    Cursor cursor = db.query("user", new String[]{"_id", "phonenumber", "password"}, "phonenumber=?"
                            , new String[]{phonenumber.getText().toString()}, null, null, null);
                    if (cursor.moveToFirst()) {
                        if (password.getText().toString().equals(cursor.getString(2))) {
                            new AlertDialog.Builder(LoginActivity.this).setTitle("信息").setMessage("登陆成功")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            //intent.putExtra("user",new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
                                            startActivity(intent);
                                        }
                                    }).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "密码错误!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "帐号不存在!!", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
                break;
            case R.id.register_login:
                //点击注册，跳转到注册页面
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.trying_login:
                //点击试用，跳转到主界面
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}