package com.tcl.isport.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcl.isport.Activity.ContactUsActivity;
import com.tcl.isport.Activity.SportsHistoryActivity;
import com.tcl.isport.R;

/**
 * Created by user on 17-9-4.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    //主界面-我
    //photo默认从数据库读取头像，点击头像可以更改头像，editData为编辑个人资料按钮
    private ImageView photo, editData;
    //name和signatue从数据库读取加载
    private TextView name, signature, systemMessage, sportsHistory, myCollection, contactUs, changeAccount;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        //头像
        photo = (ImageView) view.findViewById(R.id.photo_mine);
        photo.setOnClickListener(this);
        editData = (ImageView) view.findViewById(R.id.edit_data_mine);
        editData.setOnClickListener(this);
        name = (TextView) view.findViewById(R.id.name_mine);
        signature = (TextView) view.findViewById(R.id.signature_mine);
        systemMessage = (TextView) view.findViewById(R.id.system_message_mine);
        systemMessage.setOnClickListener(this);
        sportsHistory = (TextView) view.findViewById(R.id.sports_history_mine);
        sportsHistory.setOnClickListener(this);
        myCollection = (TextView) view.findViewById(R.id.my_collection_mine);
        myCollection.setOnClickListener(this);
        contactUs = (TextView) view.findViewById(R.id.contact_us_mine);
        contactUs.setOnClickListener(this);
        changeAccount = (TextView) view.findViewById(R.id.change_account_mine);
        changeAccount.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //点击跳转至相应界面
        switch (v.getId()) {
            case R.id.photo_mine:
                //点击从图库选择图片更换头像
                break;
            case R.id.edit_data_mine:

                break;
            case R.id.system_message_mine:

                break;
            case R.id.sports_history_mine:
                intent = new Intent(getActivity(), SportsHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.my_collection_mine:

                break;
            case R.id.contact_us_mine:
                intent = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent);
                break;
            case R.id.change_account_mine:

                break;
            default:
                break;
        }
    }
}
