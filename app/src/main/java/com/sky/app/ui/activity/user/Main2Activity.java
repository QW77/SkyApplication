package com.sky.app.ui.activity.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.sky.app.R;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.ui.activity.ask.adapter.MainViewPgerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.list;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        List<ImageView> mViewList = new ArrayList<>();
        ArrayList<String> strings = getIntent().getStringArrayListExtra("strings");
        ImageView mImageView = null;
        //给控件设置图片
        for (int i = 0; i < strings.size(); i++) {
            mImageView = (ImageView) getLayoutInflater().inflate(R.layout.lead_activity_item, null);
            ImageHelper.getInstance().displayDefinedImage(strings.get(i), mImageView,
                    R.mipmap.app_default_icon, R.mipmap.app_default_icon);
            mViewList.add(mImageView);
            //给viewpager设置adapgter
            mainViewpager.setAdapter(new MainViewPgerAdapter(mViewList));
        }

    }


}
