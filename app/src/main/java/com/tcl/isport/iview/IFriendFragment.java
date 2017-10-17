package com.tcl.isport.iview;

import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;

/**
 * Created by Water on 10/16/2017.
 */

public interface IFriendFragment {
    void setFriendData(List<LCChatKitUser> partUsers);
}
