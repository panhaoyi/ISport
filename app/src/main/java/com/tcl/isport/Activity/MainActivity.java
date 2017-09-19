package com.tcl.isport.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tcl.isport.Adapter.FragmentAdapter;
import com.tcl.isport.Fragment.FindFragment;
import com.tcl.isport.Fragment.HomeFragment;
import com.tcl.isport.Fragment.MineFragment;
import com.tcl.isport.Fragment.SportFragment;
import com.tcl.isport.R;
import com.tcl.isport.UI.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button item_home, item_sport, item_find, item_mine;
    private HomeFragment homeFragment;
    private SportFragment sportFragment;
    private FindFragment findFragment;
    private MineFragment mineFragment;
    private List<Fragment> lf;
    private NoScrollViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager_main);
        viewPager.setCanScroll(false);
        homeFragment = new HomeFragment();
        sportFragment = new SportFragment();
        findFragment = new FindFragment();
        mineFragment = new MineFragment();
        lf = new ArrayList<Fragment>();
        lf.add(homeFragment);
        lf.add(sportFragment);
        lf.add(findFragment);
        lf.add(mineFragment);
        item_home = (Button) findViewById(R.id.item_home);
        item_sport = (Button) findViewById(R.id.item_sport);
        item_find = (Button) findViewById(R.id.item_find);
        item_mine = (Button) findViewById(R.id.item_mine);
        item_home.setOnClickListener(this);
        item_sport.setOnClickListener(this);
        item_find.setOnClickListener(this);
        item_mine.setOnClickListener(this);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), lf);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(fragmentAdapter);
        //主界面默认置于首页
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        //点击切换fragment
        switch (v.getId()) {
            case R.id.item_home:
                viewPager.setCurrentItem(0);
                changeColor(0);
                break;
            case R.id.item_sport:
                viewPager.setCurrentItem(1);
                changeColor(1);
                break;
            case R.id.item_find:
                viewPager.setCurrentItem(2);
                changeColor(2);
                break;
            case R.id.item_mine:
                viewPager.setCurrentItem(3);
                changeColor(3);
                break;
            default:
                break;
        }
    }

    public void changeColor(int position) {
        //区分选中的按钮
        switch (position) {
            case 0:
                item_home.setBackgroundColor(Color.parseColor("#00FF00"));
                item_sport.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_find.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_mine.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 1:
                item_home.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_sport.setBackgroundColor(Color.parseColor("#00FF00"));
                item_find.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_mine.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 2:
                item_home.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_sport.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_find.setBackgroundColor(Color.parseColor("#00FF00"));
                item_mine.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case 3:
                item_home.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_sport.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_find.setBackgroundColor(Color.parseColor("#FFFFFF"));
                item_mine.setBackgroundColor(Color.parseColor("#00FF00"));
                break;
            default:
                break;
        }
    }

}