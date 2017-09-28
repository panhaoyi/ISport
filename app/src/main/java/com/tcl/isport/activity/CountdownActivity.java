package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class CountdownActivity extends Activity {
    //开始运动倒计时3秒
    private TextView countdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_countdown);
        MyApplication.hide(this,R.id.layout_countdown);

        countdown= (TextView) findViewById(R.id.countdown);
        final CountDownTimer timer=new CountDownTimer(3100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每过一秒更改一次剩余时间
                countdown.setText((millisUntilFinished/1000)+"");
            }

            @Override
            public void onFinish() {
                //3秒结束跳转到对应的运动界面
                intentActivity();
            }
        };
        timer.start();
    }

    private void intentActivity(){
        Intent intent=getIntent();
        switch (intent.getStringExtra("sport")){
            case "walk":
                intent=new Intent(this,WalkActivity.class);
                finish();
                startActivity(intent);
                break;
            case "run":
                intent=new Intent(this,RunActivity.class);
                finish();
                startActivity(intent);
                break;
            case "ride":
                intent=new Intent(this,RideActivity.class);
                finish();
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
