package com.tcl.isport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.isport.activity.CountdownActivity;
import com.tcl.isport.activity.RunActivity;
import com.tcl.isport.iview.ISportFragment;
import com.tcl.isport.R;

/**
 * Created by haoyi.pan on 17-9-8.
 */
public class SportRunFragment extends Fragment implements View.OnClickListener,ISportFragment {
    //主界面-运动-跑步
    private ImageView route_run_sport;
    private Button start_run_sport;
    private TextView distance_run_sport,duration_run_sport,track_run_sport;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sport_run, container, false);
        route_run_sport = (ImageView) view.findViewById(R.id.route_run_sport);
        route_run_sport.setOnClickListener(this);
        start_run_sport = (Button) view.findViewById(R.id.start_run_sport);
        start_run_sport.setOnClickListener(this);
        distance_run_sport = (TextView) view.findViewById(R.id.distance_run_sport);
        duration_run_sport = (TextView) view.findViewById(R.id.duration_run_sport);
        track_run_sport = (TextView) view.findViewById(R.id.track_run_sport);
        track_run_sport.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.route_run_sport:
                //点击切换到显示路线

                break;
            case R.id.start_run_sport:
                //点击切换到runActivity开始运动
//                Intent intent = new Intent(getContext(), RunActivity.class);
                //切换到倒计时界面
                Intent intent = new Intent(getContext(), CountdownActivity.class);
                intent.putExtra("sport","run");
                //设置flag使activity不会被销毁
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
            case R.id.track_run_sport:
                //点击切换到显示历史轨迹

                break;
            default:
                break;
        }
    }

    @Override
    public void setDistance(String distance) {
        distance_run_sport.setText(distance);
    }

    @Override
    public void setDuration(String duration) {
        duration_run_sport.setText(duration);
    }
}
