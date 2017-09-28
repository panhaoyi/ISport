package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class NewActivityActivity extends Activity implements View.OnClickListener {
    private ImageView back,cover;
    private RelativeLayout editTheme,editIntro,editDetail,editNumber,editTime,editLocation,editDeadline,editCover;
    private TextView number,time,location,deadline;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_new);
        MyApplication.hide(this,R.id.layout_new_activity);

        back= (ImageView) findViewById(R.id.back_new_activity);
        back.setOnClickListener(this);
        editTheme= (RelativeLayout) findViewById(R.id.edit_theme);
        editTheme.setOnClickListener(this);
        editIntro= (RelativeLayout) findViewById(R.id.edit_intro);
        editIntro.setOnClickListener(this);
        editDetail= (RelativeLayout) findViewById(R.id.edit_content);
        editDetail.setOnClickListener(this);
        editNumber= (RelativeLayout) findViewById(R.id.edit_number);
        editNumber.setOnClickListener(this);
        editTime= (RelativeLayout) findViewById(R.id.edit_time);
        editTime.setOnClickListener(this);
        editLocation= (RelativeLayout) findViewById(R.id.edit_location);
        editLocation.setOnClickListener(this);
        editDeadline= (RelativeLayout) findViewById(R.id.edit_deadline);
        editDeadline.setOnClickListener(this);
        editCover= (RelativeLayout) findViewById(R.id.edit_cover);
        editCover.setOnClickListener(this);
        location= (TextView) findViewById(R.id.location_new_activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_new_activity:
                finish();
                break;
            case R.id.edit_theme:
                intent=new Intent(this,ActivityThemeActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_intro:
                intent=new Intent(this,ActivityIntroActivity.class);
                startActivity(intent);
                break;
            case R.id.edit_content:
                intent=new Intent(this,ActivityContentActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

}
