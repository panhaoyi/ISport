package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.presenter.InformationActivityPresenter;

/**
 * Created by haoyi.pan on 17-9-26.
 */
public class ChangeNameActivity extends Activity implements  View.OnClickListener,TextWatcher {
    private EditText name;
    private TextView cancel,title,confirm,number;
    private int num=24;
    private String type;
    private InformationActivityPresenter informationActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_change_name);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_change_name);

        name= (EditText) findViewById(R.id.name_change_name);
        name.addTextChangedListener(this);
        cancel= (TextView) findViewById(R.id.cancel_change_name);
        cancel.setOnClickListener(this);
        title= (TextView) findViewById(R.id.title_information);
        confirm= (TextView) findViewById(R.id.confirm_change_name);
        confirm.setOnClickListener(this);
        number= (TextView) findViewById(R.id.number_change_name);
        informationActivityPresenter=new InformationActivityPresenter(this);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        if("所在城市".equals(type)){
            title.setText(type);
            num=10;
            number.setText("0/"+num);
            name.setHint("请输入所在省市");
        }else if("个性签名".equals(type)){
            title.setText(type);
            num=30;
            number.setText("0/"+num);
            name.setHint("请输入个性签名");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_change_name:
                //取消更改
                finish();
                break;
            case R.id.confirm_change_name:
                //确定更改
                if("所在城市".equals(type)){
                    //更改所在城市
                    if (!name.getText().toString().equals("")) {
                        informationActivityPresenter.changeCity(name.getText().toString());
                        finish();
                    }else{
                        Toast.makeText(this,"请输入所在省市!",Toast.LENGTH_SHORT).show();
                    }
                }else if("个性签名".equals(type)){
                    if (!name.getText().toString().equals("")) {
                        informationActivityPresenter.changeSignature(name.getText().toString());
                        finish();
                    }else{
                        Toast.makeText(this,"请输入个性签名!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //更改昵称
                    if (!name.getText().toString().equals("")) {
                        informationActivityPresenter.changeName(name.getText().toString());
                        finish();
                    }else{
                        Toast.makeText(this,"请输入新的昵称!",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length()>num){
            s.delete(num,s.length());
        }
        else{
            number.setText(s.length()+"/"+num);
        }
    }

}
