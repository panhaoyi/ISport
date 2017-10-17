package com.tcl.isport.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.IInformationActivity;
import com.tcl.isport.presenter.InformationActivityPresenter;
import com.tcl.isport.ui.MyPopupWindow;
import com.tcl.isport.util.ImageUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by haoyi.pan on 17-9-26.
 */
public class InformationActivity extends Activity implements OnClickListener,IInformationActivity {
    //主界面-我-资料编辑
    private ImageView back,photo;
    private RelativeLayout changePhoto,changeName,changeSex,changeBirth,changeCity,changeSignature;
    private TextView name,sex,birth,city,signature;
    private MyPopupWindow myPopupWindow;
    private Intent intent;
    private WindowManager.LayoutParams params;
    private InformationActivityPresenter informationActivityPresenter;
    private AVUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_information);
        //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
        MyApplication.hide(this,R.id.layout_information);
        user=AVUser.getCurrentUser();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }

    private void init() {
        back= (ImageView) findViewById(R.id.back_information);
        back.setOnClickListener(this);
        changePhoto= (RelativeLayout) findViewById(R.id.changePhoto_information);
        changePhoto.setOnClickListener(this);
        changeName= (RelativeLayout) findViewById(R.id.changeName_information);
        changeName.setOnClickListener(this);
        changeSex= (RelativeLayout) findViewById(R.id.changeSex_information);
        changeSex.setOnClickListener(this);
        changeBirth= (RelativeLayout) findViewById(R.id.changeBirth_information);
        changeBirth.setOnClickListener(this);
        changeCity= (RelativeLayout) findViewById(R.id.changeCity_information);
        changeCity.setOnClickListener(this);
        changeSignature= (RelativeLayout) findViewById(R.id.changeSignature_information);
        changeSignature.setOnClickListener(this);
        photo= (ImageView) findViewById(R.id.photo_information);

        name= (TextView) findViewById(R.id.name_information);
        name.setText(user.getUsername());
        sex= (TextView) findViewById(R.id.sex_information);
        try{
            sex.setText(user.get("sex").toString());
        }catch (Exception e){
            sex.setText("");
        }
        birth= (TextView) findViewById(R.id.birth_information);
        try{
            birth.setText(user.get("birth").toString());
        }catch (Exception e){
            birth.setText("");
        }
        city= (TextView) findViewById(R.id.city_information);
        try{
            city.setText(user.get("city").toString());
        }catch (Exception e){
            city.setText("");
        }
        signature= (TextView) findViewById(R.id.signature_information);
        try{
            signature.setText(user.get("signature").toString());
        }catch (Exception e){
            signature.setText("爱生活,爱运动!");
        }
        informationActivityPresenter=new InformationActivityPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_information:
                //返回
                finish();
                break;
            case R.id.changePhoto_information:
                //更换头像
                myPopupWindow=new MyPopupWindow(this,this);
                myPopupWindow.setOptionPhoto();
                //设置弹窗
                setMyPopupWindow();
                break;
            case R.id.changeName_information:
                //更改昵称
                intent=new Intent(this,ChangeNameActivity.class);
                intent.putExtra("type","更换昵称");
                startActivity(intent);
                break;
            case R.id.changeSex_information:
                //更改性别
                myPopupWindow=new MyPopupWindow(this,this);
                myPopupWindow.setOptionSex();
                //设置弹窗
                setMyPopupWindow();
                break;
            case R.id.changeBirth_information:
                //修改出生日期
//                intent=new Intent(this,ActivityTimeActivity.class);
//                startActivity(intent);
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birth.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                        informationActivityPresenter.changeBirth(year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
            case R.id.changeCity_information:
                //更改所在城市
                intent=new Intent(this,ChangeNameActivity.class);
                intent.putExtra("type","所在城市");
                startActivity(intent);
                break;
            case R.id.changeSignature_information:
                //个性签名
                intent=new Intent(this,ChangeNameActivity.class);
                intent.putExtra("type","个性签名");
                startActivity(intent);
                break;
            case R.id.text1_option:
                if(myPopupWindow.getOption().equals("sex")){
                    //如当前是选择性别男
                    sex.setText(myPopupWindow.getText1());
                    informationActivityPresenter.changeSex("男");
                    myPopupWindow.dismiss();
                }else if(myPopupWindow.getOption().equals("photo")){
                    //如当前是选择相册照片上传头像
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, 22);
                    myPopupWindow.dismiss();
                }
                break;
            case R.id.text2_option:
                if(myPopupWindow.getOption().equals("sex")){
                    //如当前是选择性别女
                    sex.setText(myPopupWindow.getText2());
                    informationActivityPresenter.changeSex("女");
                    myPopupWindow.dismiss();
                }else if(myPopupWindow.getOption().equals("photo")){
                    //如当前是选择相机拍摄上传头像

                    myPopupWindow.dismiss();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==22&&resultCode==RESULT_OK){
            try {
                photo.setImageBitmap(ImageUtil.getBitmapFormUri(this,data.getData()));
                Bitmap bm=ImageUtil.getBitmapFormUri(this,data.getData());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytesPhoto=baos.toByteArray();
                informationActivityPresenter.changePhoto(bytesPhoto);

//                cover.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
//                bytesCover=getBytes(getContentResolver().openInputStream(data.getData()));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setMyPopupWindow(){
        //设置从底部弹出
        myPopupWindow.showAtLocation(findViewById(R.id.layout_information), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        params=getWindow().getAttributes();
        //设置弹出时背景透明度0.7
        params.alpha=0.7f;
        getWindow().setAttributes(params);
        myPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //监听弹窗关闭时背景透明度恢复
            @Override
            public void onDismiss() {
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }

    @Override
    public void setPhoto(Bitmap bitmap) {
        photo.setImageBitmap(bitmap);
    }

    @Override
    public void setName(String name) {
        this.name.setText(name);
    }

    @Override
    public void setSex(String sex) {
        this.sex.setText(sex);
    }

    @Override
    public void setBirth(String birth) {
        this.birth.setText(birth);
    }

    @Override
    public void setCity(String city) {
        this.city.setText(city);
    }

    @Override
    public void setSignature(String signature) {
        this.signature.setText(signature);
    }

    @Override
    public void refresh() {

    }

}
