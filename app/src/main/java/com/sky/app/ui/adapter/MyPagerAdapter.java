package com.sky.app.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class MyPagerAdapter extends PagerAdapter {

    List<ImageView> list;

    public MyPagerAdapter(List<ImageView> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

      @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView mImageView = list.get(position);
        container.addView(mImageView);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView mImageView = list.get(position);
        container.removeView(mImageView);

    }

}
