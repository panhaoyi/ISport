package com.tcl.isport.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tcl.isport.R;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class MyPopupWindow extends PopupWindow {
    private Context context;
    private View view;
    private TextView text1,text2;

    public MyPopupWindow(Context context, View.OnClickListener onClickListener){
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
        text2= (TextView) view.findViewById(R.id.text2_option);
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
    }
    public void setSex(){
        //设置弹出内容为性别(默认为选择更换头像方式)
        text1.setText("男");
        text2.setText("女");
    }
}
