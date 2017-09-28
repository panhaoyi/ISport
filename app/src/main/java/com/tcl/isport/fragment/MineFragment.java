package com.tcl.isport.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tcl.isport.activity.ActivityManageActivity;
import com.tcl.isport.activity.ContactUsActivity;
import com.tcl.isport.activity.FriendActivity;
import com.tcl.isport.activity.HomepageActivity;
import com.tcl.isport.activity.InformationActivity;
import com.tcl.isport.activity.HistoryActivity;
import com.tcl.isport.iview.IMineFragment;
import com.tcl.isport.R;

/**
 * Created by user on 17-9-4.
 */
public class MineFragment extends Fragment implements View.OnClickListener, IMineFragment {
    //主界面-我
    //photo默认从数据库读取头像
    private ImageView photo;
    //editInformation为编辑个人资料按钮,name和signatue从数据库读取加载
    private TextView editInformation, name, signature;
    private RelativeLayout homepage;
    private LinearLayout myMessage, friends, sportsHistory, manageActivity, myCollection, contactUs;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        //头像
        photo = (ImageView) view.findViewById(R.id.photo_mine);
        editInformation = (TextView) view.findViewById(R.id.edit_information_mine);
        editInformation.setOnClickListener(this);
        homepage= (RelativeLayout) view.findViewById(R.id.homepage_mine);
        homepage.setOnClickListener(this);
        name = (TextView) view.findViewById(R.id.name_mine);
        signature = (TextView) view.findViewById(R.id.signature_mine);
        myMessage = (LinearLayout) view.findViewById(R.id.my_message_mine);
        myMessage.setOnClickListener(this);
        friends = (LinearLayout) view.findViewById(R.id.friends_mine);
        friends.setOnClickListener(this);
        sportsHistory = (LinearLayout) view.findViewById(R.id.sports_history_mine);
        sportsHistory.setOnClickListener(this);
        manageActivity = (LinearLayout) view.findViewById(R.id.manage_activity_mine);
        manageActivity.setOnClickListener(this);
        myCollection = (LinearLayout) view.findViewById(R.id.my_collection_mine);
        myCollection.setOnClickListener(this);
        contactUs = (LinearLayout) view.findViewById(R.id.contact_us_mine);
        contactUs.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //点击跳转至相应界面
        switch (v.getId()) {
            case R.id.edit_information_mine:
                //跳转到编辑个人资料
                intent = new Intent(getActivity(), InformationActivity.class);
                startActivity(intent);
                break;
            case R.id.homepage_mine:
                //跳转到个人主页
                intent=new Intent(getActivity(), HomepageActivity.class);
                startActivity(intent);
                break;
            case R.id.my_message_mine:
                //跳转到我的消息

                break;
            case R.id.friends_mine:
                //跳转到好友列表
                intent=new Intent(getActivity(), FriendActivity.class);
                startActivity(intent);
                break;
            case R.id.sports_history_mine:
                //跳转到历史记录
                intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.manage_activity_mine:
                //跳转到活动管理
                intent=new Intent(getActivity(), ActivityManageActivity.class);
                startActivity(intent);
                break;
            case R.id.my_collection_mine:
                //跳转到我的收藏

                break;
            case R.id.contact_us_mine:
                //跳转到联系我们
                intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void setPhoto() {

    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setSignature(String signature) {
        this.signature.setText(signature);
    }
}
