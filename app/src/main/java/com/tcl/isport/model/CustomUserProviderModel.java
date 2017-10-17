package com.tcl.isport.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.tcl.isport.presenter.FriendFragmentPresenter;

import java.util.List;

/**
 * Created by lishui.lin on 17-10-16
 * 获取用户数据
 */
public class CustomUserProviderModel {

//  private static CustomUserProviderModel customUserProvider;
//
//  public synchronized static CustomUserProviderModel getInstance() {
//    if (null == customUserProvider) {
//      customUserProvider = new CustomUserProviderModel();
//    }
//    return customUserProvider;
//  }

  private FriendFragmentPresenter friendFragmentPresenter;

  public CustomUserProviderModel(FriendFragmentPresenter friendFragmentPresenter) {
    this.friendFragmentPresenter = friendFragmentPresenter;
  }

  public CustomUserProviderModel() {
  }

  public void getUserDataProvider() {
    AVQuery<AVUser> followerQuery = AVUser.followerQuery(AVUser.getCurrentUser().getObjectId(),
            AVUser.class);
    followerQuery.include("follower");
    followerQuery.findInBackground(new FindCallback<AVUser>() {
      @Override
      public void done(List<AVUser> avObjects, AVException avException) {
        if (avException == null) {
           friendFragmentPresenter.doInCustomUserProvider(avObjects);
        } else {
          friendFragmentPresenter.doInCustomUserProvider(null);
        }
      }
    });
  }

  public interface ICustomUserData {
      void doInCustomUserProvider(List<AVUser> avObjects);
  }
}
