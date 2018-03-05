package com.sky.app.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sky.app.bean.Seller;
import com.sky.app.ui.fragment.AllProductPageFragment;
import com.sky.app.ui.fragment.ShopHomePageFragment;

/**
 * 商铺中心tab
 * Created by Administrator on 2015/7/30.
 */
public class ShopDecoratePagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"店铺首页", "全部商品", "新品上架"};
    private Seller seller;

    public ShopDecoratePagerAdapter(FragmentManager fm, Seller seller) {
        super(fm);
        this.seller = seller;
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position){
            ShopHomePageFragment shopHomePageFragment = new ShopHomePageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("seller_id", seller.getUser_id());
            shopHomePageFragment.setArguments(bundle);
            return shopHomePageFragment;
        }else {
            AllProductPageFragment allProductPageFragment = new AllProductPageFragment();
            Bundle bundle = new Bundle();
            bundle.putString("seller_id", seller.getUser_id());
            allProductPageFragment.setArguments(bundle);
            return allProductPageFragment;
        }

//        }else if (2 == position){
//            NewProductPageFragment newProductPageFragment = new NewProductPageFragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("seller_id", seller.getUser_id());
//            newProductPageFragment.setArguments(bundle);
//            return newProductPageFragment;
//        }
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