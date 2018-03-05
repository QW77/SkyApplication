package com.sky.app.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.alibaba.mobileim.ui.atmessage.mode.FragmentPageItem;
import com.sky.app.R;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.Seller;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.adaptor.holder.RecyclerViewHolder;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.ui.activity.messageandnotice.ContactListFragment;
import com.sky.app.ui.activity.messageandnotice.SessionListFragment;
import com.sky.app.ui.fragment.AllProductPageFragment;
import com.sky.app.ui.fragment.MessageCenterFragment;
import com.sky.app.ui.fragment.ShopHomePageFragment;

import java.util.List;

/**
 * 发布列表适配器
 */
public class MessageOpenAdapter extends FragmentPagerAdapter {

    protected Context mContext;
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"消息", "通知"};
//    private Seller seller;
//    private SearchProductRequest searchProductRequest;

    public MessageOpenAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            SessionListFragment sessionListFragment = new SessionListFragment();
            return sessionListFragment;
        } else {
//            AllProductPageFragment allProductPageFragment = new AllProductPageFragment();
            MessageCenterFragment messageCenterFragment = new MessageCenterFragment();
            return messageCenterFragment;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}