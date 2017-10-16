package com.tcl.isport.presenter;

import android.graphics.Bitmap;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.tcl.isport.activity.ChangeNameActivity;
import com.tcl.isport.fragment.MineFragment;
import com.tcl.isport.imodel.IUserModel;
import com.tcl.isport.iview.IInformationActivity;
import com.tcl.isport.model.UserModel;

/**
 * Created by haoyi.pan on 17-10-16.
 */
public class InformationActivityPresenter {
    private IInformationActivity iInformationActivity;
    private ChangeNameActivity changeNameActivity;
    private MineFragment mineFragment;
    private IUserModel iUserModel;
    public InformationActivityPresenter(IInformationActivity view){
        iInformationActivity=view;
        iUserModel=new UserModel(this);
        try {
            iUserModel.getPhoto();
        }catch (Exception e){

        }
    }
    public InformationActivityPresenter(ChangeNameActivity view){
        changeNameActivity=view;
        iUserModel=new UserModel(this);
    }

    public InformationActivityPresenter(MineFragment view) {
        mineFragment=view;
        iUserModel=new UserModel(this);
        try {
            iUserModel.getPhoto();
        }catch (Exception e){

        }
    }

    public void getPhoto() {
        iUserModel.getPhoto();
    }

    public void setPhoto(Bitmap bitmap) {
        if (iInformationActivity!=null) {
            iInformationActivity.setPhoto(bitmap);
        }else if(mineFragment!=null){
            mineFragment.setPhoto(bitmap);
        }
    }

    public void refreshView() {
        iInformationActivity.refresh();
    }

    public void changePhoto(byte[] bytes){
        AVFile avFile=new AVFile("photo",bytes);
        AVUser user=AVUser.getCurrentUser();
        user.put("photo",avFile);
        iUserModel.updateUser(user);
    }

    public void changeSex(String sex) {
        AVUser user=AVUser.getCurrentUser();
        user.put("sex",sex);
        iUserModel.updateUser(user);
    }

    public void changeBirth(String birth) {
        AVUser user=AVUser.getCurrentUser();
        user.put("birth",birth);
        iUserModel.updateUser(user);
    }

    public void changeName(String name) {
        AVUser user=AVUser.getCurrentUser();
        user.setUsername(name);
        iUserModel.updateUser(user);
    }

    public void changeCity(String city) {
        AVUser user=AVUser.getCurrentUser();
        user.put("city",city);
        iUserModel.updateUser(user);
    }

    public void changeSignature(String signature) {
        AVUser user=AVUser.getCurrentUser();
        user.put("signature",signature);
        iUserModel.updateUser(user);
    }
}
