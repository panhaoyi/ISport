package com.tcl.isport.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    private LinearLayout item_home, item_sport, item_find, item_mine;
    private ImageView icon_home,icon_sport,icon_find,icon_mine;
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
        item_home = (LinearLayout) findViewById(R.id.item_home);
        item_sport = (LinearLayout) findViewById(R.id.item_sport);
        item_find = (LinearLayout) findViewById(R.id.item_find);
        item_mine = (LinearLayout) findViewById(R.id.item_mine);
        item_home.setOnClickListener(this);
        item_sport.setOnClickListener(this);
        item_find.setOnClickListener(this);
        item_mine.setOnClickListener(this);
        icon_home= (ImageView) findViewById(R.id.icon_home);
        icon_sport= (ImageView) findViewById(R.id.icon_sport);
        icon_find= (ImageView) findViewById(R.id.icon_find);
        icon_mine= (ImageView) findViewById(R.id.icon_mine);

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
                changeIcon(0);
                break;
            case R.id.item_sport:
                viewPager.setCurrentItem(1);
                changeIcon(1);
                break;
            case R.id.item_find:
                viewPager.setCurrentItem(2);
                changeIcon(2);
                break;
            case R.id.item_mine:
                viewPager.setCurrentItem(3);
                changeIcon(3);
                break;
            default:
                break;
        }
    }

    public void changeIcon(int position) {
        //区分选中的按钮
        switch (position) {
            case 0:
                icon_home.setImageResource(R.drawable.ic_selected_home);
                icon_sport.setImageResource(R.drawable.ic_unselected_sport);
                icon_find.setImageResource(R.drawable.ic_unselected_find);
                icon_mine.setImageResource(R.drawable.ic_unselected_mine);
                break;
            case 1:
                icon_home.setImageResource(R.drawable.ic_unselected_home);
                icon_sport.setImageResource(R.drawable.ic_selected_sport);
                icon_find.setImageResource(R.drawable.ic_unselected_find);
                icon_mine.setImageResource(R.drawable.ic_unselected_mine);
                break;
            case 2:
                icon_home.setImageResource(R.drawable.ic_unselected_home);
                icon_sport.setImageResource(R.drawable.ic_unselected_sport);
                icon_find.setImageResource(R.drawable.ic_selected_find);
                icon_mine.setImageResource(R.drawable.ic_unselected_mine);
                break;
            case 3:
                icon_home.setImageResource(R.drawable.ic_unselected_home);
                icon_sport.setImageResource(R.drawable.ic_unselected_sport);
                icon_find.setImageResource(R.drawable.ic_unselected_find);
                icon_mine.setImageResource(R.drawable.ic_selected_mine);
                break;
            default:
                break;
        }
    }

}