package com.sky.shop.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sky.shop.ui.fragment.AllProductPageFragment;
import com.sky.shop.ui.fragment.ShopCategoryPageFragment;
import com.sky.shop.ui.fragment.ShopHomePageFragment;
import com.sky.shop.ui.fragment.ShopIntroducePageFragment;

/**
 * 装潢适配器
 * Created by Administrator on 2015/7/30.
 */
public class DecoratePagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[]{"首页", "目录", "商品", "简介"};

    public DecoratePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            return new ShopHomePageFragment();
        } else if (1 == position) {
            return new ShopCategoryPageFragment();
        } else if (2 == position) {
            return new AllProductPageFragment();
        } else if (3 == position) {
            return new ShopIntroducePageFragment();
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