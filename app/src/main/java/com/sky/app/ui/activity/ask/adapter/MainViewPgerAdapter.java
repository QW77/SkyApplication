package com.sky.app.ui.activity.ask.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sky.app.R;
import com.sky.app.library.utils.ImageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class MainViewPgerAdapter extends PagerAdapter {

    private List<ImageView> list = new ArrayList<>();
    private ImageView imageview;

    public MainViewPgerAdapter(List<ImageView> list) {
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
        imageview = list.get(position);
        container.addView(imageview);
        return imageview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageview);
    }
}
