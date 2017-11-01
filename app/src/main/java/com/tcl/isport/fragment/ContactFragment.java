package com.tcl.isport.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.tcl.isport.R;
import com.tcl.isport.adapter.MembersAdapter;
import com.tcl.isport.bean.MemberLetterEvent;
import com.tcl.isport.iview.IFriendFragment;
import com.tcl.isport.presenter.FriendFragmentPresenter;

import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.cache.LCIMProfileCache;
import cn.leancloud.chatkit.view.LCIMDividerItemDecoration;
import de.greenrobot.event.EventBus;

/**
 * Created by lishui.lin on 17-10-16
 */
public class ContactFragment extends Fragment implements IFriendFragment {

  protected SwipeRefreshLayout refreshLayout;
  protected RecyclerView recyclerView;

  private MembersAdapter itemAdapter;
  private FriendFragmentPresenter friendFragmentPresenter;
  private boolean isGetFirstUserData = true;
  LCChatKitUser currentUser;
  LinearLayoutManager layoutManager;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.contact_fragment, container, false);

    refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.contact_fragment_srl_list);
    recyclerView = (RecyclerView) view.findViewById(R.id.contact_fragment_rv_list);

    layoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(new LCIMDividerItemDecoration(getActivity()));
    itemAdapter = new MembersAdapter();
    recyclerView.setAdapter(itemAdapter);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        refreshMembers();
      }
    });
    friendFragmentPresenter = new FriendFragmentPresenter(this);

    if (isGetFirstUserData) {
      initChat(friendFragmentPresenter);
      isGetFirstUserData = false;
    }
    EventBus.getDefault().register(this);
    return view;
  }

  @Override
  public void onDestroyView() {
    EventBus.getDefault().unregister(this);
    super.onDestroyView();
  }

  @Override
  public void onResume() {
    super.onResume();
    initMe();
    refreshMembers();
  }

  public void refreshMembers() {
    friendFragmentPresenter.getUserData();
  }

  /**
   * 处理 LetterView 发送过来的 MemberLetterEvent
   * 会通过 MembersAdapter 获取应该要跳转到的位置，然后跳转
   */
  public void onEvent(MemberLetterEvent event) {
    Character targetChar = Character.toLowerCase(event.letter);
    if (itemAdapter.getIndexMap().containsKey(targetChar)) {
      int index = itemAdapter.getIndexMap().get(targetChar);
      if (index > 0 && index < itemAdapter.getItemCount()) {
        layoutManager.scrollToPositionWithOffset(index, 0);
      }
    }
  }

  private void initChat(FriendFragmentPresenter friendFragmentPresenter) {
    LCChatKit.getInstance().setProfileProvider(friendFragmentPresenter);
  }

  private void initMe(){
    if (AVUser.getCurrentUser().getAVFile("photo") != null &&
            !AVUser.getCurrentUser().getAVFile("photo").equals("")) {
      currentUser = new LCChatKitUser(AVUser.getCurrentUser().getObjectId(),
              AVUser.getCurrentUser().getUsername(), AVUser.getCurrentUser().getAVFile("photo").getUrl());
    } else {
      currentUser = new LCChatKitUser(AVUser.getCurrentUser().getObjectId(),
              AVUser.getCurrentUser().getUsername(), null);
    }

    LCChatKit.getInstance().open(currentUser.getUserId(), new AVIMClientCallback() {
      @Override
      public void done(AVIMClient avimClient, AVIMException e) {
        if (null == e) {
        } else {
        }
      }
    });
  }

  private void refreshMe() {
    if (AVUser.getCurrentUser().getAVFile("photo") != null &&
            !AVUser.getCurrentUser().getAVFile("photo").equals("")) {
      currentUser = new LCChatKitUser(AVUser.getCurrentUser().getObjectId(),
              AVUser.getCurrentUser().getUsername(), AVUser.getCurrentUser().getAVFile("photo").getUrl());
    } else {
      currentUser = new LCChatKitUser(AVUser.getCurrentUser().getObjectId(),
              AVUser.getCurrentUser().getUsername(), null);
    }

    LCIMProfileCache.getInstance().cacheUser(currentUser);
  }

  @Override
  public void setFriendData(List<LCChatKitUser> partUsers) {

    if (partUsers != null && partUsers.size() > 0) {
      itemAdapter.setMemberList(partUsers);
      itemAdapter.notifyDataSetChanged();
      refreshLayout.setRefreshing(false);
      refreshMe();
    } else {
      refreshLayout.setRefreshing(false);
      refreshMe();
    }

  }
}
