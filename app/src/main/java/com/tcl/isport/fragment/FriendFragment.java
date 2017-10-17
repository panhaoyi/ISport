package com.tcl.isport.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.FollowCallback;
import com.tcl.isport.R;
import com.tcl.isport.application.MyApplication;
import com.tcl.isport.util.UserUtil;

import java.util.Arrays;
import java.util.List;

import cn.leancloud.chatkit.activity.LCIMConversationListFragment;

/**
 * Created by haoyi.pan on 17-10-10.
 */
public class FriendFragment extends Fragment implements OnClickListener {
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView addFriends;
    private EditText user_id;
    private ContactFragment contactFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_friend,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.pager);
        tabLayout = (TabLayout)view.findViewById(R.id.tablayout);
        user_id = (EditText) view.findViewById(R.id.user_id);
        addFriends = (TextView) view.findViewById(R.id.addFriends);
        addFriends.setOnClickListener(this);
        initTabLayout();
        return view;
    }

    private void initTabLayout() {
        String[] tabList = new String[]{"消息箱", "联系人"};
        contactFragment = new ContactFragment();
        final Fragment[] fragmentList = new Fragment[] {new LCIMConversationListFragment(),
                contactFragment};

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabList.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabList[i]));
        }

        TabFragmentAdapter adapter = new TabFragmentAdapter(getActivity().getSupportFragmentManager(),
                Arrays.asList(fragmentList), Arrays.asList(tabList));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (0 == position) {
//          EventBus.getDefault().post(new ConversationFragmentUpdateEvent());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addFriends:
                //隐藏虚拟按键,沉浸式状态栏,设置布局marginTop为状态栏高度
                MyApplication.hide(getActivity(),R.id.activity_main);
                addNewFriend();
                break;
            default:
                break;
        }
    }

    private void addNewFriend() {
        String userId = user_id.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(userId)) {
            showFriendFragmentToast("输入用户id为空值！");
            focusView = user_id;
            cancel = true;
        }else if (!UserUtil.checkValidPhoneNumber(userId)) {
            showFriendFragmentToast("无此用户id！");
            focusView = user_id;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
            userAVQuery.whereEqualTo("mobilePhoneNumber", userId);
            userAVQuery.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if (e == null) {
                        if (list != null && list.size() > 0) {
                            startAdd(list);
                        } else {
                            showFriendFragmentToast("该用户不存在！");
                        }

                    } else {
                        showFriendFragmentToast("添加用户失败！");
                    }
                }
            });

        }
    }

    private void startAdd(List<AVUser> list){

        String ObjectId = list.get(0).getObjectId();
        AVUser.getCurrentUser().followInBackground(ObjectId, new FollowCallback() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    showFriendFragmentToast("该用户已经添加成功！");
                } else if (e.getCode() == AVException.DUPLICATE_VALUE) {
                    showFriendFragmentToast("此用户已经是你的好友了！");
                } else {
                    showFriendFragmentToast("该用户不存在！");
                }
            }

            @Override
            protected void internalDone0(Object o, AVException e) {
                if (e == null) {
                    showFriendFragmentToast("该用户已经添加成功！");
                    contactFragment.refreshMembers();
                    user_id.setText("");
                } else if (e.getCode() == AVException.DUPLICATE_VALUE) {
                    showFriendFragmentToast("此用户已经是你的好友了！");
                } else {
                    showFriendFragmentToast("该用户不存在或是本身！");
                }
            }
        });
    }
    private void showFriendFragmentToast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }
    private class TabFragmentAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;
        private List<String> mTitles;

        public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
