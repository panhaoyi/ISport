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

import com.tcl.isport.activity.WalkActivity;
import com.tcl.isport.iView.ISportFragment;
import com.tcl.isport.presenter.SportFragmentPresenter;
import com.tcl.isport.R;

/**
 * Created by haoyi.pan on 17-9-8.
 */
public class SportWalkFragment extends Fragment implements View.OnClickListener,ISportFragment {
    //主界面-运动-跑步
    private ImageView route_walk_sport;
    private Button start_walk_sport;
    private TextView distance_walk_sport,duration_walk_sport,track_walk_sport;
    private SportFragmentPresenter sportWalkPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sport_walk, container, false);
        route_walk_sport = (ImageView) view.findViewById(R.id.route_walk_sport);
        route_walk_sport.setOnClickListener(this);
        start_walk_sport = (Button) view.findViewById(R.id.start_walk_sport);
        start_walk_sport.setOnClickListener(this);
        distance_walk_sport = (TextView) view.findViewById(R.id.distance_walk_sport);
        duration_walk_sport = (TextView) view.findViewById(R.id.duration_walk_sport);
        track_walk_sport = (TextView) view.findViewById(R.id.track_walk_sport);
        track_walk_sport.setOnClickListener(this);

        sportWalkPresenter=new SportFragmentPresenter(this);
        sportWalkPresenter.loadData();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.route_walk_sport:
                //点击切换到显示路线

                break;
            case R.id.start_walk_sport:
                //点击切换到WalkActivity开始运动
                Intent intent = new Intent(getContext(), WalkActivity.class);
                startActivity(intent);
                break;
            case R.id.track_walk_sport:
                //点击切换到显示历史轨迹

                break;
            default:
                break;
        }
    }

    @Override
    public void setDistance(String distance) {
        distance_walk_sport.setText(distance);
    }

    @Override
    public void setDuration(String duration) {
        duration_walk_sport.setText(duration);
    }
}
