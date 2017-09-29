package com.tcl.isport.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.avos.avoscloud.AVQuery;
import com.tcl.isport.R;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class MyPopupWindow extends PopupWindow {
    private Context context;
    private View view;
    private TextView text1,text2,text3;
    private String option;

    public MyPopupWindow(Context context, View.OnClickListener onClickListener){
        this.context=context;
        this.view= LayoutInflater.from(context).inflate(R.layout.option_photo,null);
        setOutsideTouchable(true);
        view.setOnTouchListener(new View.OnTouchListener() {
            //监听触屏位置在弹窗外销毁弹窗
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height=view.findViewById(R.id.option_photo).getTop();
                int y= (int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        setContentView(view);
        text1= (TextView) view.findViewById(R.id.text1_option);
        text1.setOnClickListener(onClickListener);
        text2= (TextView) view.findViewById(R.id.text2_option);
        text2.setOnClickListener(onClickListener);
        text3= (TextView) view.findViewById(R.id.text3_option);
        text3.setOnClickListener(onClickListener);
        //弹窗高宽
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //弹窗可点击
        setFocusable(true);
        //实例化一个半透明的ColorDrawable
        ColorDrawable colorDrawable=new ColorDrawable(0xb0000000);
        //设置弹窗背景为半透明色
        setBackgroundDrawable(colorDrawable);
        //设置弹出动画style
        setAnimationStyle(R.style.take_photo_anim);
        onClickListener.onClick(view);
        view.setOnClickListener(onClickListener);
    }
    public void setOptionSex(){
        //设置弹出内容为性别(默认为选择更换头像方式)
        option="sex";
        text1.setText("男");
        text2.setText("女");
        text3.setVisibility(View.GONE);
    }

    public void setOptionNumber(){
        option="number";
        text1.setText("0-20人");
        text2.setText("0-50人");
        text3.setVisibility(View.VISIBLE);
        text3.setText("0-100人");
    }

    public void setOptionPhoto(){
        option="photo";
        text1.setText("相册选择");
        text2.setText("相机拍摄");
        text3.setVisibility(View.GONE);
    }

    public CharSequence getText1() {
        return text1.getText();
    }

    public CharSequence getText2() {
        return text2.getText();
    }
    public CharSequence getText3() {
        return text3.getText();
    }
    public String getOption(){
        return option;
    }

}
