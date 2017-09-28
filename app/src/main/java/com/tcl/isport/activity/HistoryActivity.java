package com.tcl.isport.activity;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.adapter.MyArrayAdapter;
import com.tcl.isport.application.MyApplication;

/**
 * Created by user on 17-9-12.
 */
public class HistoryActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private ImageView back;
    private Spinner spinner;
    private static String[] arrayString=null;
    private MyArrayAdapter arrayAdapter;
    private TextView day,week,month,today,distance,step,step_km,times,duration,speed,consume;
    private LinearLayout selectedDay,selectedWeek,selectedMonth;

    //主界面-我-历史记录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_history);

        back= (ImageView) findViewById(R.id.back_history);
        back.setOnClickListener(this);
        spinner= (Spinner) findViewById(R.id.spinner_history);
        //初始化spinner显示的数据
        arrayString=new String[]{"健走","跑步","骑行"};
        //spinner_text改变spinner默认样式
        arrayAdapter=new MyArrayAdapter(this,this,R.layout.spinner_text,arrayString);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        day= (TextView) findViewById(R.id.day_history);
        day.setOnClickListener(this);
        selectedDay= (LinearLayout) findViewById(R.id.selected_day_history);
        week= (TextView) findViewById(R.id.week_history);
        week.setOnClickListener(this);
        selectedWeek= (LinearLayout) findViewById(R.id.selected_week_history);
        month= (TextView) findViewById(R.id.month_history);
        month.setOnClickListener(this);
        selectedMonth= (LinearLayout) findViewById(R.id.selected_month_history);

        distance= (TextView) findViewById(R.id.distance_history);
        step_km= (TextView) findViewById(R.id.step_km_history);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_history:
                finish();
                break;
            case R.id.day_history:
                day.setTextColor(Color.parseColor("#ffffff"));
                selectedDay.setVisibility(View.VISIBLE);
                week.setTextColor(Color.parseColor("#9b9b9b"));
                selectedWeek.setVisibility(View.GONE);
                month.setTextColor(Color.parseColor("#9b9b9b"));
                selectedMonth.setVisibility(View.GONE);
                break;
            case R.id.week_history:
                day.setTextColor(Color.parseColor("#9b9b9b"));
                selectedDay.setVisibility(View.GONE);
                week.setTextColor(Color.parseColor("#ffffff"));
                selectedWeek.setVisibility(View.VISIBLE);
                month.setTextColor(Color.parseColor("#9b9b9b"));
                selectedMonth.setVisibility(View.GONE);
                break;
            case R.id.month_history:
                day.setTextColor(Color.parseColor("#9b9b9b"));
                selectedDay.setVisibility(View.GONE);
                week.setTextColor(Color.parseColor("#9b9b9b"));
                selectedWeek.setVisibility(View.GONE);
                month.setTextColor(Color.parseColor("#ffffff"));
                selectedMonth.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                distance.setVisibility(View.VISIBLE);
                step_km.setText("(步)");
                break;
            case 1:
                distance.setVisibility(View.VISIBLE);
                step_km.setText("(步)");
                break;
            case 2:
                distance.setVisibility(View.GONE);
                step_km.setText("(公里)");
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
