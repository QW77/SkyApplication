package com.sky.app.ui.activity.user;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.T;
import com.sky.app.ui.adapter.MyCollectFragmentAdapter;

import butterknife.BindView;

/**
 * 我的收藏
 */
public class MyCollectActivity extends BaseViewActivity{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    private MyCollectFragmentAdapter pagerAdapter;

    @BindView(R.id.app_collect_vp)
    ViewPager viewPager;
    @BindView(R.id.app_collect_tab)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_my_collect);
    }

    @Override
    protected IBasePresenter presenter() {
        return null;
    }

    @Override
    protected void init() {
        title.setText(R.string.app_my_collect_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        pagerAdapter = new MyCollectFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    @Override
    protected void onDestoryActivity() {

    }
}
