package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tcl.isport.adapter.MyPagerAdapter;
import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyi.pan on 17-9-20.
 */
public class WelcomeActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener, View.OnTouchListener {
    //首次启动欢迎页
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private View view1, view2;
    private TextView skip, next;
    //记录当前位于第几个页面，屏幕宽度
    private int currentItem, width;
    //记录手势按下及抬起时的x坐标点
    float xStart, xEnd;
    List<View> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_welcome);

        //获得屏幕宽度
        WindowManager windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
        //初始化view
        viewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
        list = new ArrayList<>();
        //获取2个欢迎页及其内部组件
        view1 = LayoutInflater.from(this).inflate(R.layout.layout_welcome1, null);
        skip = (TextView) view1.findViewById(R.id.skip);
        skip.setOnClickListener(this);
        view2 = LayoutInflater.from(this).inflate(R.layout.layout_welcome2, null);
        next = (TextView) view2.findViewById(R.id.next);
        next.setOnClickListener(this);
        list.add(view1);
        list.add(view2);
        //将view集合添加到适配器，设置适配器
        myPagerAdapter = new MyPagerAdapter(list);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.skip:
                turnToLogin();
                break;
            case R.id.next:
                turnToLogin();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //记录当前页面
        currentItem = position;
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //手势按下，记录x坐标
                xStart = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                //手势抬起，记录x坐标
                xEnd = event.getX();
                if (currentItem == list.size() - 1 && (xStart - xEnd) > width / 5) {
                    //当在最后一个欢迎页且往左滑动的x坐标距离大于屏幕的五分一时，跳转到登录界面
                    turnToLogin();
                }
                break;
        }
        return false;
    }

    private void turnToLogin() {
        //跳转到登录界面
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);
    }
}
