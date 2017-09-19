package com.tcl.isport.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tcl.isport.Bean.User;
import com.tcl.isport.R;
import com.tcl.isport.Util.DBOpenHelper;

/**
 * Created by user on 17-8-21.
 */
public class RegisterActivity extends Activity implements View.OnFocusChangeListener, View.OnClickListener {
    //注册界面
    private EditText phonenumber, password1, password2;
    private Button register;
    //User实例，用来保存用户注册信息
    private User user;
    private DBOpenHelper databaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        databaseHelper = new DBOpenHelper(RegisterActivity.this);
        db = databaseHelper.getWritableDatabase();

        user = new User();

        phonenumber = (EditText) findViewById(R.id.phonenumber_register);
        phonenumber.setOnFocusChangeListener(this);
        password1 = (EditText) findViewById(R.id.password1_register);
        password1.setOnFocusChangeListener(this);
        password2 = (EditText) findViewById(R.id.password2_register);
        password2.setOnFocusChangeListener(this);
        register = (Button) findViewById(R.id.register_register);
        register.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        //聚焦置空，焦点移除显示提示
        switch (v.getId()) {
            case R.id.phonenumber_register:
                if (hasFocus && phonenumber.getText().toString().equals("请输入手机号")) {
                    phonenumber.setText("");
                } else if (!hasFocus && phonenumber.getText().toString().equals("")) {
                    phonenumber.setText("请输入手机号");
                }
                break;
            case R.id.password1_register:
                if (hasFocus && password1.getText().toString().equals("请输入密码")) {
                    password1.setText("");
                    password1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else if (!hasFocus && password1.getText().toString().equals("")) {
                    password1.setText("请输入密码");
                    password1.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            case R.id.password2_register:
                if (hasFocus && password2.getText().toString().equals("再次输入密码")) {
                    password2.setText("");
                    password2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else if (!hasFocus && password2.getText().toString().equals("")) {
                    password2.setText("再次输入密码");
                    password2.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注册验证
            case R.id.register_register:
                if (phonenumber.getText().toString().equals("") || phonenumber.getText().toString().equals("请输入手机号")) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号!!", Toast.LENGTH_SHORT).show();
                } else {
                    user.setPhonenumber(phonenumber.getText().toString());
                }
                if (password1.getText().toString().equals("") || password1.getText().toString().equals("请输入密码")) {
                    Toast.makeText(RegisterActivity.this, "请输入密码!!", Toast.LENGTH_SHORT).show();
                } else if (password2.getText().toString().equals("") || password1.getText().toString().equals("请再次输入密码")) {
                    Toast.makeText(RegisterActivity.this, "请再次输入密码!!", Toast.LENGTH_SHORT).show();
                } else if (!password1.getText().toString().equals(password2.getText().toString())) {
                    Log.println(Log.INFO, "RegisterActivity", password1 + "=" + password2);
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致!!", Toast.LENGTH_SHORT).show();
                } else {
                    user.setPassword(password1.getText().toString());
                    ContentValues cv = new ContentValues();
                    cv.put("phonenumber", phonenumber.getText().toString());
                    cv.put("password", password1.getText().toString());
                    db.insert("user", null, cv);
                    db.close();
                    new AlertDialog.Builder(RegisterActivity.this).setTitle("信息").setMessage("注册成功！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                }
                break;
            default:
                break;
        }
    }
}
