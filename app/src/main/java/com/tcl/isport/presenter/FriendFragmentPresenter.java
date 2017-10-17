package com.tcl.isport.presenter;

import android.util.Log;

import com.avos.avoscloud.AVUser;
import com.tcl.isport.model.CustomUserProviderModel;
import com.tcl.isport.iview.IFriendFragment;
import com.tcl.isport.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by lishui.lin on 10/16/2017.
 */

public class FriendFragmentPresenter implements CustomUserProviderModel.ICustomUserData, LCChatProfileProvider {

    private IFriendFragment iFriendFragment;
    private CustomUserProviderModel customUserProviderModel;
    private List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    private static FriendFragmentPresenter friendFragmentPresenter;

    public synchronized static FriendFragmentPresenter getInstance() {
        if (null == friendFragmentPresenter) {
            friendFragmentPresenter = new FriendFragmentPresenter();
        }
        return friendFragmentPresenter;
    }

    private FriendFragmentPresenter() {
        customUserProviderModel = new CustomUserProviderModel(this);
    }

    public FriendFragmentPresenter(IFriendFragment iFriendFragment) {
        this.iFriendFragment = iFriendFragment;
        customUserProviderModel = new CustomUserProviderModel(this);
    }

    @Override
    public void doInCustomUserProvider(List<AVUser> avObjects) {
        partUsers.clear();
        if (avObjects != null && avObjects.size() > 0) {
            for (int i = 0; i < avObjects.size(); i++) {
                if (avObjects.get(i).getAVFile("photo") != null) {
                    partUsers.add(new LCChatKitUser(avObjects.get(i).getObjectId(),
                            avObjects.get(i).getUsername(), avObjects.get(i).getAVFile("photo").getUrl()));
                } else {
                    partUsers.add(new LCChatKitUser(avObjects.get(i).getObjectId(),
                            avObjects.get(i).getUsername(), null));
                }

            }

        }
        iFriendFragment.setFriendData(partUsers);
    }

    @Override
    public void fetchProfiles(List<String> list, LCChatProfilesCallBack lcChatProfilesCallBack) {
        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
        for (String userId : list) {
            for (LCChatKitUser user : partUsers) {
                if (user.getUserId().equals(userId)) {
                    userList.add(user);
                    break;
                }
            }
        }
        lcChatProfilesCallBack.done(userList, null);
    }

    public void getUserData() {
        customUserProviderModel.getUserDataProvider();
    }

    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }
}
