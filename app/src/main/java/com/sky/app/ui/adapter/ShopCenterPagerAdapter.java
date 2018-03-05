package com.sky.app.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.Seller;
import com.sky.app.ui.fragment.AllProductPageFragment;
import com.sky.app.ui.fragment.ShopHomePageFragment;

/**
 * 商铺中心tab
 * Created by Administrator on 2015/7/30.
 */
public class ShopCenterPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"店铺首页", "全部商品", "新品上架"};
    private Seller seller;
    private SearchProductRequest searchProductRequest;

    public ShopCenterPagerAdapter(FragmentManager fm, Seller seller, SearchProductRequest searchProductRequest) {
        super(fm);
        this.seller = seller;
        this.searchProductRequest = searchProductRequest;
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
            bundle.putSerializable("search", searchProductRequest);
            allProductPageFragment.setArguments(bundle);
            return allProductPageFragment;
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