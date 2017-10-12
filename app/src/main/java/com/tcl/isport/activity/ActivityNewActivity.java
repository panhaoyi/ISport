package com.tcl.isport.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.iview.IActivityNewActivity;
import com.tcl.isport.presenter.NewActivityPresenter;
import com.tcl.isport.ui.MyPopupWindow;
import com.tcl.isport.util.ImageUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by haoyi.pan on 17-9-28.
 */
public class ActivityNewActivity extends Activity implements View.OnClickListener,IActivityNewActivity {
    private ImageView back,cover;
    private RelativeLayout editTheme,editIntro,editContent,editNumber,editTime,editLocation,editDeadline,editCover;
    private TextView pub,theme,intro,content,number,time,location,deadline;
    private ProgressBar progressBar;
    private Intent intent;
    private MyPopupWindow myPopupWindow;
    private WindowManager.LayoutParams params;
    private String activityTheme,activityIntro,activityContent;
    private byte[] bytesCover=null;
    private NewActivityPresenter newActivityPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_new);
        MyApplication.hide(this,R.id.layout_activity_new);

        back= (ImageView) findViewById(R.id.back_activity_new);
        back.setOnClickListener(this);
        pub= (TextView) findViewById(R.id.pub_activity_new);
        pub.setOnClickListener(this);
        editTheme= (RelativeLayout) findViewById(R.id.edit_theme);
        editTheme.setOnClickListener(this);
        editIntro= (RelativeLayout) findViewById(R.id.edit_intro);
        editIntro.setOnClickListener(this);
        editContent= (RelativeLayout) findViewById(R.id.edit_content);
        editContent.setOnClickListener(this);
        editNumber= (RelativeLayout) findViewById(R.id.edit_number);
        editNumber.setOnClickListener(this);
        editTime= (RelativeLayout) findViewById(R.id.edit_time);
        editTime.setOnClickListener(this);
        editLocation= (RelativeLayout) findViewById(R.id.edit_location);
        editLocation.setOnClickListener(this);
        editDeadline= (RelativeLayout) findViewById(R.id.edit_deadline);
        editDeadline.setOnClickListener(this);
        editCover= (RelativeLayout) findViewById(R.id.edit_cover);
        editCover.setOnClickListener(this);
        theme= (TextView) findViewById(R.id.theme_activity_new);
        intro= (TextView) findViewById(R.id.intro_activity_new);
        content= (TextView) findViewById(R.id.content_activity_new);
        number= (TextView) findViewById(R.id.number_activity_new);
        time= (TextView) findViewById(R.id.time_activity_new);
        location= (TextView) findViewById(R.id.location_activity_new);
        deadline= (TextView) findViewById(R.id.deadline_activity_new);
        cover= (ImageView) findViewById(R.id.cover_activity_new);
        progressBar= (ProgressBar) findViewById(R.id.progress_activity_new);
        newActivityPresenter=new NewActivityPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_activity_new:
                //返回
                finish();
                break;
            case R.id.pub_activity_new:
                if("".equals(theme.getText())){
                    Toast.makeText(this,"请编辑活动主题!",Toast.LENGTH_SHORT).show();
                }else if("".equals(intro.getText())){
                    Toast.makeText(this,"请编辑活动简介!",Toast.LENGTH_SHORT).show();
                }else if("".equals(content.getText())){
                    Toast.makeText(this,"请编辑行程详情!",Toast.LENGTH_SHORT).show();
                }else if("".equals(number.getText())){
                    Toast.makeText(this,"请编辑人数规模!",Toast.LENGTH_SHORT).show();
                }else if("".equals(time.getText())){
                    Toast.makeText(this,"请编辑活动时间!",Toast.LENGTH_SHORT).show();
                }else if("".equals(location.getText())){
                    Toast.makeText(this,"请编辑活动地点!",Toast.LENGTH_SHORT).show();
                }else if("".equals(deadline.getText())){
                    Toast.makeText(this,"请编辑截止时间!",Toast.LENGTH_SHORT).show();
                }else if(bytesCover==null){
                    Toast.makeText(this,"请添加封面图片!",Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    newActivityPresenter.pubActivity(this);
                }
                break;
            case R.id.edit_theme:
                //活动主题
                intent=new Intent(this,ActivityThemeActivity.class);
                intent.putExtra("type","活动主题");
                startActivityForResult(intent,1);
                break;
            case R.id.edit_intro:
                //活动简介
                intent=new Intent(this,ActivityIntroActivity.class);
                startActivityForResult(intent,2);
                break;
            case R.id.edit_content:
                //行程详情
                intent=new Intent(this,ActivityContentActivity.class);
                startActivityForResult(intent,3);
                break;
            case R.id.edit_number:
                //人数规模
                myPopupWindow=new MyPopupWindow(this,this);
                myPopupWindow.setOptionNumber();
                //设置弹窗
                setMyPopWindow();
                break;
            case R.id.edit_time:
                //活动时间
                intent=new Intent(this,ActivityTimeActivity.class);
                intent.putExtra("type","活动时间");
                startActivityForResult(intent,5);
                break;
            case R.id.edit_location:
                //活动地点
                intent=new Intent(this,ActivityThemeActivity.class);
                intent.putExtra("type","活动地点");
                startActivityForResult(intent,6);
                break;
            case R.id.edit_deadline:
                //截止时间
                intent=new Intent(this,ActivityTimeActivity.class);
                intent.putExtra("type","截止时间");
                startActivityForResult(intent,7);
                break;
            case R.id.edit_cover:
                //封面图片
                myPopupWindow=new MyPopupWindow(this,this);
                myPopupWindow.setOptionPhoto();
                //设置弹窗
                setMyPopWindow();
                break;
            case R.id.text1_option:
                //点击底部弹窗选项1
                if(myPopupWindow.getOption().equals("number")){
                    number.setText(myPopupWindow.getText1());
                    myPopupWindow.dismiss();
                }else if (myPopupWindow.getOption().equals("photo")){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, 11);
                    myPopupWindow.dismiss();
                }
                break;
            case R.id.text2_option:
                //点击底部弹窗选项2
                if(myPopupWindow.getOption().equals("number")){
                    number.setText(myPopupWindow.getText2());
                    myPopupWindow.dismiss();
                }else if (myPopupWindow.getOption().equals("photo")){

                    myPopupWindow.dismiss();
                }
                break;
            case R.id.text3_option:
                //点击底部弹窗选项3
                if(myPopupWindow.getOption().equals("number")){
                    number.setText(myPopupWindow.getText3());
                    myPopupWindow.dismiss();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==1){
            //ActivityThemeActivity中填写并确认了主题
            theme.setText("已填写");
            activityTheme=data.getStringExtra("theme");
        }else if (resultCode==2){
            //填写并确认了简介
            intro.setText("已填写");
            activityIntro=data.getStringExtra("intro");
        }else if (resultCode==3){
            //填写并确认了详情
            content.setText("已填写");
            activityContent=data.getStringExtra("content");
        }else if (resultCode==5){
            time.setText(data.getStringExtra("time"));
        }else if (resultCode==6){
            location.setText(data.getStringExtra("location"));
        }else if (resultCode==7){
            deadline.setText(data.getStringExtra("deadline"));
        }
        if (requestCode==11&&resultCode==RESULT_OK){
            try {
                cover.setImageBitmap(ImageUtil.getBitmapFormUri(this,data.getData()));
                Bitmap bm=ImageUtil.getBitmapFormUri(this,data.getData());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                bytesCover=baos.toByteArray();

//                cover.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData()));
//                bytesCover=getBytes(getContentResolver().openInputStream(data.getData()));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private void setMyPopWindow(){
        //设置从底部弹出
        myPopupWindow.showAtLocation(findViewById(R.id.layout_activity_new), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
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
    public String getActivityTheme() {
        return activityTheme;
    }

    @Override
    public String getIntro() {
        return activityIntro;
    }

    @Override
    public String getContent() {
        return activityContent;
    }

    @Override
    public String getNumber() {
        return number.getText().toString();
    }

    @Override
    public String getTime() {
        return time.getText().toString();
    }

    @Override
    public String getLocation() {
        return location.getText().toString();
    }

    @Override
    public String getDeadline() {
        return deadline.getText().toString();
    }

    @Override
    public byte[] getCover() {
        return bytesCover;
    }
}
