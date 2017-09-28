package com.tcl.isport.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class FriendActivity extends Activity implements View.OnClickListener {
    //主界面-我-好友列表
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friend);
        MyApplication.hide(this,R.id.layout_friend);

        back= (ImageView) findViewById(R.id.back_friend);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_friend:
                finish();
                break;
            default:
                break;
        }
    }
}
