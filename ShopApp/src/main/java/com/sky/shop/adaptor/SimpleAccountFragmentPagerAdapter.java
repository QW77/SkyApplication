package com.sky.shop.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sky.shop.ui.fragment.AccountPageFragment;
import com.sky.shop.ui.fragment.ApplyAccountPageFragment;
import com.sky.shop.ui.fragment.QueryAccountPageFragment;

/**
 * 提现账户tab
 * Created by Administrator on 2015/7/30.
 */
public class SimpleAccountFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"申请提现", "提现账户", "提现查询"};

    public SimpleAccountFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int position) {
        if (0 == position){
            return new ApplyAccountPageFragment();
        }else if (1 == position){
            return new AccountPageFragment();
        }else if (2 == position){
            return new QueryAccountPageFragment();
        }
        return null;
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