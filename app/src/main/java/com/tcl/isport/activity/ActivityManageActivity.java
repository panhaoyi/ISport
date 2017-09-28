package com.tcl.isport.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcl.isport.R;
import com.tcl.isport.adapter.FragmentAdapter;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.fragment.JoinActivityFragment;
import com.tcl.isport.fragment.PubActivityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class ActivityManageActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    //主界面-我-活动管理
    private ImageView back;
    private TextView pub,join;
    private LinearLayout selectPub,selectJoin;
    private PubActivityFragment pubActivityFragment;
    private JoinActivityFragment joinActivityFragment;
    private List<Fragment> lf;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.layout_activity_manage);
        MyApplication.hide(this,R.id.layout_activity_manage);

        back= (ImageView) findViewById(R.id.back_activity_manage);
        back.setOnClickListener(this);
        pub= (TextView) findViewById(R.id.item_pub);
        pub.setOnClickListener(this);
        join= (TextView) findViewById(R.id.item_join);
        join.setOnClickListener(this);
        selectPub= (LinearLayout) findViewById(R.id.selected_pub);
        selectJoin= (LinearLayout) findViewById(R.id.selected_join);
        pubActivityFragment=new PubActivityFragment();
        joinActivityFragment=new JoinActivityFragment();
        lf=new ArrayList<>();
        lf.add(pubActivityFragment);
        lf.add(joinActivityFragment);
        viewPager= (ViewPager) findViewById(R.id.viewpager_activity_manage);
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),lf);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }
    public void changeColor(int position) {
        //标识选中fragment
        switch (position) {
            case 0:
                pub.setTextColor(Color.parseColor("#ffffff"));
                selectPub.setVisibility(View.VISIBLE);
                join.setTextColor(Color.parseColor("#9b9b9b"));
                selectJoin.setVisibility(View.GONE);
                break;
            case 1:
                pub.setTextColor(Color.parseColor("#9b9b9b"));
                selectPub.setVisibility(View.GONE);
                join.setTextColor(Color.parseColor("#ffffff"));
                selectJoin.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_activity_manage:
                finish();
                break;
            case R.id.item_pub:
                viewPager.setCurrentItem(0);
                changeColor(0);
                break;
            case R.id.item_join:
                viewPager.setCurrentItem(1);
                changeColor(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
