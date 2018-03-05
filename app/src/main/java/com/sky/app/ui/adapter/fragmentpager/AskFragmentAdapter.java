package com.sky.app.ui.adapter.fragmentpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.sky.app.bean.MessageAskTable;
import com.sky.app.ui.fragment.AskListFragment;

import java.util.List;

/**
 * Created by xmy on 2018/1/2.
 */

public class AskFragmentAdapter extends FragmentPagerAdapter {
    private List<MessageAskTable.ListBean> mTypeList;
    private List<AskListFragment> mViewList;


    public AskFragmentAdapter(FragmentManager fm, List<MessageAskTable.ListBean> mTypeList, List<AskListFragment> mViewList) {
        super(fm);
        this.mViewList = mViewList;
        this.mTypeList = mTypeList;
    }

    public void setTypeList(List<MessageAskTable.ListBean> typeList) {
        mTypeList = typeList;

    }

    public void setFragmentList(List<AskListFragment> ViewList) {
        mViewList = ViewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();//页卡数
    }

    @Override
    public Fragment getItem(int position) {
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {}

    @Override
    public CharSequence getPageTitle(int position) {
        return mTypeList.get(position).getTwo_dir_name();//页卡标题
    }

}

