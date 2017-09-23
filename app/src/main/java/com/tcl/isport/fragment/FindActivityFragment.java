package com.tcl.isport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tcl.isport.activity.JoinActivityActivity;
import com.tcl.isport.R;

/**
 * Created by haoyi.pan on 17-9-22.
 */
public class FindActivityFragment extends Fragment implements OnClickListener {
    //主界面-运动圈-活动
    private View view;
    private TextView join_activity,new_activity,manage_activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_find_activity,container,false);
        join_activity= (TextView) view.findViewById(R.id.join_activity);
        join_activity.setOnClickListener(this);
        new_activity= (TextView) view.findViewById(R.id.new_activity);
        new_activity.setOnClickListener(this);
        manage_activity= (TextView) view.findViewById(R.id.manage_activity);
        manage_activity.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join_activity:
                //跳转参加活动
                Intent intent=new Intent(getActivity(), JoinActivityActivity.class);
                startActivity(intent);
                break;
            case R.id.new_activity:
                //跳转发布活动

                break;
            case R.id.manage_activity:
                //跳转管理活动

                break;
        }
    }
}
