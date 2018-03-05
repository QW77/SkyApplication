package com.sky.shop.ui.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.shop.R;
import com.sky.shop.adaptor.DecoratePagerAdapter;

import butterknife.BindView;

/**
 * 装潢
 * Created by Administrator on 2017/2/30.
 */
public class ShopDecorateFragment extends BaseViewFragment{

    @BindView(R.id.app_shop_list)
    ViewPager viewPager;
    @BindView(R.id.app_category_list)
    TabLayout tabLayout;

    private DecoratePagerAdapter decoratePagerAdapter;

    @Override
    protected void init() {
        decoratePagerAdapter = new DecoratePagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(decoratePagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_shop_decorate_center;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShopIntroducePageFragment shopIntroducePageFragment = (ShopIntroducePageFragment) decoratePagerAdapter.getItem(3);
        shopIntroducePageFragment.onActivityResult(requestCode, resultCode, data);
    }
}