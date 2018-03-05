package com.sky.app.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sky.app.ui.fragment.MyCollectProFragment;
import com.sky.app.ui.fragment.MyCollectPubFragment;
import com.sky.app.ui.fragment.MyCollectShopFragment;

/**
 * 我的收藏tab
 * Created by Administrator on 2015/7/30.
 */
public class MyCollectFragmentAdapter extends FragmentPagerAdapter {

    public static final String APP_ARG_PAGE = "ARG_PAGE";
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"商品", "商家", "发布"};

    public MyCollectFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            return new MyCollectProFragment();
        } else if (1 == position) {
            return new MyCollectShopFragment();
        } else if (2 == position) {
            return new MyCollectPubFragment();
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