package com.tcl.isport.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tcl.isport.adapter.FragmentAdapter;
import com.tcl.isport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-9-4.
 */
public class FindFragment extends Fragment implements View.OnClickListener,ViewPager.OnPageChangeListener {
    //主界面-运动圈
    private View view;
    private TextView item_friend, item_activity, item_topic;
    private LinearLayout friend,activity,topic;
    private FindActivityFragment findActivityFragment;
    private FindFriendFragment findFriendFragment;
    private FindTopicFragment findTopicFragment;
    private List<Fragment> lf;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_find);
        findFriendFragment = new FindFriendFragment();
        findActivityFragment = new FindActivityFragment();
        findTopicFragment = new FindTopicFragment();
        lf = new ArrayList<>();
        lf.add(findFriendFragment);
        lf.add(findActivityFragment);
        lf.add(findTopicFragment);
        item_friend = (TextView) view.findViewById(R.id.item_friend);
        item_activity = (TextView) view.findViewById(R.id.item_activity);
        item_topic = (TextView) view.findViewById(R.id.item_topic);
        item_friend.setOnClickListener(this);
        item_activity.setOnClickListener(this);
        item_topic.setOnClickListener(this);
        friend= (LinearLayout) view.findViewById(R.id.selected_friend);
        activity= (LinearLayout) view.findViewById(R.id.selected_activity);
        topic= (LinearLayout) view.findViewById(R.id.selected_topic);
        fragmentAdapter = new FragmentAdapter(getFragmentManager(), lf);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(this);
        return view;
    }
    public void changeColor(int position) {
        //标识选中fragment
        switch (position) {
            case 0:
                item_friend.setTextColor(Color.parseColor("#ffffff"));
                item_friend.setTextSize(16);
                friend.setVisibility(View.VISIBLE);
                item_activity.setTextColor(Color.parseColor("#9b9b9b"));
                item_activity.setTextSize(14);
                activity.setVisibility(View.GONE);
                item_topic.setTextColor(Color.parseColor("#9b9b9b"));
                item_topic.setTextSize(14);
                topic.setVisibility(View.GONE);
                break;
            case 1:
                item_friend.setTextColor(Color.parseColor("#9b9b9b"));
                item_friend.setTextSize(14);
                friend.setVisibility(View.GONE);
                item_activity.setTextColor(Color.parseColor("#ffffff"));
                item_activity.setTextSize(16);
                activity.setVisibility(View.VISIBLE);
                item_topic.setTextColor(Color.parseColor("#9b9b9b"));
                item_topic.setTextSize(14);
                topic.setVisibility(View.GONE);
                break;
            case 2:
                item_friend.setTextColor(Color.parseColor("#9b9b9b"));
                item_friend.setTextSize(14);
                friend.setVisibility(View.GONE);
                item_activity.setTextColor(Color.parseColor("#9b9b9b"));
                item_activity.setTextSize(14);
                activity.setVisibility(View.GONE);
                item_topic.setTextColor(Color.parseColor("#ffffff"));
                item_topic.setTextSize(16);
                topic.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        //点击切换fragment
        switch (v.getId()) {
            case R.id.item_friend:
                viewPager.setCurrentItem(0);
                changeColor(0);
                break;
            case R.id.item_activity:
                viewPager.setCurrentItem(1);
                changeColor(1);
                break;
            case R.id.item_topic:
                viewPager.setCurrentItem(2);
                changeColor(2);
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
