package com.tcl.isport.activity;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.github.mikephil.charting.charts.LineChart;
import com.tcl.isport.R;
import com.tcl.isport.adapter.MyArrayAdapter;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.IHistoryActivity;
import com.tcl.isport.presenter.HistoryActivityPresenter;
import com.tcl.isport.util.LocationUtil;
import com.tcl.isport.util.SportUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 17-9-12.
 */
public class HistoryActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener,
        IHistoryActivity {
    private ImageView back;
    private Spinner spinner;
    private static String[] arrayString = null;
    private MyArrayAdapter arrayAdapter;
    private TextView day, week, month, today, distance, step, step_km, times, duration, speed, consume;
    private TextView today_history, times_history, duration_history, speed_history, consume_history, step_history;
    private LinearLayout selectedDay, selectedWeek, selectedMonth;
    //历史记录图表声明
    private LineChart mine_history_chart;
    private HistoryActivityPresenter walkHistoryActivityPresenter;
    private HistoryActivityPresenter runHistoryActivityPresenter;
    private HistoryActivityPresenter rideHistoryActivityPresenter;
    private int dateType = 1;  //默认按日显示数据
    private int sportType = 1; //默认健走
    private String[] sportStr = new String[]{"", "健走", "跑步", "骑行"};

    //主界面-我-历史记录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_history);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this, R.id.layout_history);

        /*start add by lishui.lin on 2017-10-10*/
        initView();
        initListener();
        initData();

        walkHistoryActivityPresenter = new HistoryActivityPresenter(this, 1);
        runHistoryActivityPresenter = new HistoryActivityPresenter(this, 2);
        rideHistoryActivityPresenter = new HistoryActivityPresenter(this, 3);
        /*end add by lishui.lin on 2017-10-10*/
    }

    /*start add by lishui.lin on 2017-10-10*/
    private void initView() {
        back = (ImageView) findViewById(R.id.back_history);
        spinner = (Spinner) findViewById(R.id.spinner_history);
        day = (TextView) findViewById(R.id.day_history);
        selectedDay = (LinearLayout) findViewById(R.id.selected_day_history);
        week = (TextView) findViewById(R.id.week_history);
        selectedWeek = (LinearLayout) findViewById(R.id.selected_week_history);
        month = (TextView) findViewById(R.id.month_history);
        selectedMonth = (LinearLayout) findViewById(R.id.selected_month_history);
        step_km = (TextView) findViewById(R.id.step_km_history);

        today_history = (TextView) findViewById(R.id.today_history);
        distance = (TextView) findViewById(R.id.distance_history);
        step_history = (TextView) findViewById(R.id.step_history);
        times_history = (TextView) findViewById(R.id.times_history);
        duration_history = (TextView) findViewById(R.id.duration_history);
        speed_history = (TextView) findViewById(R.id.speed_history);
        consume_history = (TextView) findViewById(R.id.consume_history);

        mine_history_chart = (LineChart) findViewById(R.id.mine_history_chart);
    }

    private void initListener() {
        back.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        day.setOnClickListener(this);
        week.setOnClickListener(this);
        month.setOnClickListener(this);
    }

    private void initData() {
        //初始化spinner显示的数据
        arrayString = new String[]{"健走", "跑步", "骑行"};
        //spinner_text改变spinner默认样式
        arrayAdapter = new MyArrayAdapter(this, this, R.layout.spinner_text, arrayString);
        spinner.setAdapter(arrayAdapter);

        today_history.setText(SportUtil.getTodayDate() + "," + sportStr[sportType] + "数据：");
    }


    //日、周选项切换
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

                dateType = 1;
                today_history.setText(SportUtil.getTodayDate() + "," + sportStr[sportType] + "数据：");
                if (sportType == 1) {
                    walkHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 1);
                }
                if (sportType == 2) {
                    runHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 1);
                }
                if (sportType == 3) {
                    rideHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 1);
                }
                break;
            case R.id.week_history:
                day.setTextColor(Color.parseColor("#9b9b9b"));
                selectedDay.setVisibility(View.GONE);
                week.setTextColor(Color.parseColor("#ffffff"));
                selectedWeek.setVisibility(View.VISIBLE);
                month.setTextColor(Color.parseColor("#9b9b9b"));
                selectedMonth.setVisibility(View.GONE);

                dateType = 2;
                today_history.setText("本周历史运动数据统计：");
                if (sportType == 1) {
                    walkHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 2);
                }
                if (sportType == 2) {
                    runHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 2);
                }
                if (sportType == 3) {
                    rideHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 2);
                }

                break;
            case R.id.month_history:
                day.setTextColor(Color.parseColor("#9b9b9b"));
                selectedDay.setVisibility(View.GONE);
                week.setTextColor(Color.parseColor("#9b9b9b"));
                selectedWeek.setVisibility(View.GONE);
                month.setTextColor(Color.parseColor("#ffffff"));
                selectedMonth.setVisibility(View.VISIBLE);

                dateType = 3;
                today_history.setText("本月历史运动数据统计：");
                if (sportType == 1) {
                    walkHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 3);
                }
                if (sportType == 2) {
                    runHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 3);
                }
                if (sportType == 3) {
                    rideHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 3);
                }
                break;
        }
    }

    //健走、跑步、骑行选择
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                distance.setVisibility(View.VISIBLE);
                step_km.setText("(步)");

                sportType = 1;
                if (dateType == 1) {
                    today_history.setText(SportUtil.getTodayDate() + "," + sportStr[sportType] + "数据：");
                }
                walkHistoryActivityPresenter.getMineHistoryChart(mine_history_chart,dateType);
                //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
                MyApplication.hide(this, R.id.layout_history);
//                if (dateType == 2) {
//                    today_history.setText("本周历史运动数据统计：");
//                }
//                if (dateType == 3) {
//                    today_history.setText("本月历史运动数据统计：");
//                }
                break;
            case 1:
                distance.setVisibility(View.VISIBLE);
                step_km.setText("(步)");

                sportType = 2;
                if (dateType == 1) {
                    today_history.setText(SportUtil.getTodayDate() + "," + sportStr[sportType] + "数据：");
                }
                runHistoryActivityPresenter.getMineHistoryChart(mine_history_chart,dateType);
                //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
                MyApplication.hide(this, R.id.layout_history);
//                if (dateType == 2) {
//                    today_history.setText("本周历史运动数据统计：");
//                }
//                if (dateType == 3) {
//                    today_history.setText("本月历史运动数据统计：");
//                }
                break;
            case 2:
                distance.setVisibility(View.GONE);
                step_km.setText("(公里)");

                sportType = 3;
                if (dateType == 1) {
                    today_history.setText(SportUtil.getTodayDate() + "," + sportStr[sportType] + "数据：");
                }
                rideHistoryActivityPresenter.getMineHistoryChart(mine_history_chart,dateType);
                //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
                MyApplication.hide(this, R.id.layout_history);
//                if (dateType == 2) {
//                    today_history.setText("本周历史运动数据统计：");
//                }
//                if (dateType == 3) {
//                    today_history.setText("本月历史运动数据统计：");
//                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        walkHistoryActivityPresenter.getMineHistoryChart(mine_history_chart, 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void setSteps(String steps) {
        if (!steps.isEmpty()) {
            step_history.setText(steps);
        }

    }

    @Override
    public void setDistance(String distance) {
        if (!distance.isEmpty()) {
            this.distance.setText(distance + "公里");
        }

    }

    @Override
    public void setTimes(int times) {
        if (times > -1) {
            times_history.setText(String.valueOf(times));
        }

    }

    @Override
    public void setDuration(String duration) {
        if (!duration.isEmpty()) {
            duration_history.setText(duration);
        }

    }

    @Override
    public void setAvgSpeed(String speed) {
        if (!speed.isEmpty()) {
            speed_history.setText(speed);
        }

    }

    @Override
    public void setConsume(String consume) {
        if (!consume.isEmpty()) {
            consume_history.setText(consume);
        }

    }

     /*end add by lishui.lin on 2017-10-10*/
}
