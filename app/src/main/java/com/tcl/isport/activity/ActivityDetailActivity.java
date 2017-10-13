package com.tcl.isport.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.tcl.isport.R;
import com.tcl.isport.adapter.MyArrayAdapter;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.bean.ActivityBean;
import com.tcl.isport.iview.IActivityDetailActivity;
import com.tcl.isport.presenter.ActivityDetailPresenter;
import com.tcl.isport.ui.MySpinner;
import com.tcl.isport.util.DateUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by haoyi.pan on 17-9-27.
 */
public class ActivityDetailActivity extends Activity implements IActivityDetailActivity, View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {
    private ImageView back, setting, cover, collect, photo;
    private MySpinner spinner;
    private MyArrayAdapter arrayAdapter;
    private TextView join_cancel, theme, countdown, intro, location, time, number, content, apply_number, deadline;
    private String activityId;
    private ActivityDetailPresenter activityDetailPresenter;
    private ActivityBean activityBean;
    private boolean isInit;
    private String joinActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_detail);
        //隐藏虚拟按键,,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this, R.id.layout_activity_detail);

        Intent intent = getIntent();
        activityId = intent.getStringExtra("objectId");
        activityDetailPresenter = new ActivityDetailPresenter(this, activityId);
        isInit=true;
        back = (ImageView) findViewById(R.id.back_activity_detail);
        back.setOnClickListener(this);
        setting = (ImageView) findViewById(R.id.setting_activity_detail);
        spinner = (MySpinner) findViewById(R.id.spinner_activity_detail);
        join_cancel = (TextView) findViewById(R.id.join_activity_detail);
        join_cancel.setOnClickListener(this);
        cover = (ImageView) findViewById(R.id.cover_activity_detail);
        theme = (TextView) findViewById(R.id.theme_activity_detail);
        countdown = (TextView) findViewById(R.id.countdown_activity_detail);
        collect = (ImageView) findViewById(R.id.collect_activity_detail);
        intro = (TextView) findViewById(R.id.intro_activity_detail);
        location = (TextView) findViewById(R.id.location_activity_detail);
        time = (TextView) findViewById(R.id.time_activity_detail);
        number = (TextView) findViewById(R.id.number_activity_detail);
        content = (TextView) findViewById(R.id.content_activity_detail);
        apply_number = (TextView) findViewById(R.id.apply_number_activity_detail);
        deadline = (TextView) findViewById(R.id.deadline_activity_detail);
        photo = (ImageView) findViewById(R.id.photo_activity_detail);

        arrayAdapter = new MyArrayAdapter(this, this, R.layout.spinner_text2, new String[]{"延期", "取消"});
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_activity_detail:
                finish();
                break;
            case R.id.join_activity_detail:
                if(join_cancel.getText().toString().equals("加入")){
                    new AlertDialog.Builder(this).setMessage("是否确定加入活动?").setNegativeButton("取消",null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activityDetailPresenter.joinActivity(activityBean.getObjectId());
                                }
                            }).show();
                }else{
                    new AlertDialog.Builder(this).setMessage("是否确定退出活动?").setNegativeButton("取消",null)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    activityDetailPresenter.quitActivity(joinActivityId);
                                }
                            }).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void setData(ActivityBean activityBean,int joinNumber) {
        this.activityBean = activityBean;
        theme.setText(activityBean.getTheme());
        intro.setText(activityBean.getIntro());
        content.setText(activityBean.getContent());
        countdown.setText(DateUtil.compareDate(activityBean.getTime(), 2));
        time.setText("活动时间: " + activityBean.getTime());
        deadline.setText("报名截止时间: " + activityBean.getDeadline());
        number.setText("人数规模: " + activityBean.getNumber());
        location.setText("活动地点: " + activityBean.getLocation());
        byte[] bytes = activityBean.getCover();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        cover.setImageBitmap(bitmap);
        apply_number.setText("当前报名人数: "+joinNumber);
        try {
            if (activityBean.getUserId().equals(AVUser.getCurrentUser().getObjectId())) {
                setting.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                join_cancel.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            join_cancel.setVisibility(View.GONE);
        }

    }

    @Override
    public void setData(ActivityBean activityBean,int joinNumber, String objectId) {
        //当前用户参与的活动
        this.activityBean = activityBean;
        theme.setText(activityBean.getTheme());
        intro.setText(activityBean.getIntro());
        content.setText(activityBean.getContent());
        countdown.setText(DateUtil.compareDate(activityBean.getTime(), 2));
        time.setText("活动时间: " + activityBean.getTime());
        deadline.setText("报名截止时间: " + activityBean.getDeadline());
        number.setText("人数规模: " + activityBean.getNumber());
        location.setText("活动地点: " + activityBean.getLocation());
        byte[] bytes = activityBean.getCover();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        cover.setImageBitmap(bitmap);
        apply_number.setText("当前报名人数: "+joinNumber);
        join_cancel.setText("退出");
        joinActivityId=objectId;
    }

    @Override
    public void refreshView(int operationCode) {
        onCreate(null);
        if (operationCode==1) {
            Toast.makeText(this, "延期成功!", Toast.LENGTH_SHORT).show();
        }else if(operationCode==2){
            Toast.makeText(this,"加入成功!",Toast.LENGTH_SHORT).show();
        }else if(operationCode==3){
            Toast.makeText(this,"退出成功!",Toast.LENGTH_SHORT).show();
        }
    }

    public float getRawSize(int unit, float value) {
        Resources res = this.getResources();
        return TypedValue.applyDimension(unit, value, res.getDisplayMetrics());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                if (isInit) {
                    isInit = false;
                    break;
                }
                final EditText inputServer = new EditText(this);
                inputServer.addTextChangedListener(this);
                inputServer.setHint("时间格式如:2017-09-01 13:01:00");
                inputServer.setFocusable(true);

                new AlertDialog.Builder(this).setTitle("活动延期")
                        .setView(inputServer).setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String inputTime = inputServer.getText().toString();
                                Pattern pattern = Pattern.compile("(\\d{2}|\\d{4})(?:\\-)?([0]{1}\\d{1}|[1]{1}[0-2]{1})(?:\\-)?([0-2]{1}\\d{1}|[3]{1}[0-1]{1})(?:\\s)?([0-1]{1}\\d{1}|[2]{1}[0-3]{1})(?::)?([0-5]{1}\\d{1})(?::)?([0-5]{1}\\d{1})");
                                Matcher matcher = pattern.matcher(inputTime);
                                if (matcher.matches()) {
                                    if (DateUtil.compareDate(activityBean.getTime(), inputTime)) {
                                        activityBean.setTime(inputTime);
                                        activityDetailPresenter.postpone(activityBean);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"输入时间早于原活动时间!",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(), "请输入正确的时间!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
                break;
            case 1:
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(this).setTitle("取消活动").setMessage("是否确定取消活动?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setNegativeButton("取消", null).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 19) {
            s.delete(19, s.length());
        } else {
            number.setText(s.length() + "/19");
        }
    }
}
