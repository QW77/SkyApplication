package com.sky.shop.adaptor;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hongbang on 2017/5/11.
 */

public class SeconCategoryViewPagerAdapter extends PagerAdapter {


    Context  context;

    ArrayList<View> mViewList = null;

    public SeconCategoryViewPagerAdapter(Context context, ArrayList<View> mViewList) {
        this.context = context;
        this.mViewList = mViewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }


}
