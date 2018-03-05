package com.sky.app.ui.activity.publish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.DecorationCityHeadlinearDetai;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.banner.LoopViewPagerLayout;
import com.sky.app.library.component.banner.listener.OnBannerItemClickListener;
import com.sky.app.library.component.banner.listener.OnLoadImageViewListener;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.library.component.banner.modle.IndicatorLocation;
import com.sky.app.library.component.banner.modle.LoopStyle;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.ImageHelper;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.PublishDetailPresenter;
import com.sky.app.ui.adapter.PublishDetailCityHeadlinearAdapter;
import com.sky.app.ui.adapter.PublishDetailHeadlinearAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishDecorationCityHeadlinearActivity extends BaseViewActivity<PublishContract.IPublishDetailPresenter>
        implements PublishContract.IPublishDetailView {

    SupplyDetail supplyDetail;

    @BindView(R.id.publish_title_city_tou)
    TextView publishTitletou;
    @BindView(R.id.publish_toolbar)
    Toolbar publishbar;
    @BindView(R.id.city_mLoopViewPagerLayout1)
    LoopViewPagerLayout cityLoopViewPagerLayout1;
    @BindView(R.id.city_recycleview_list)
    ListView publishlistview;
    private PublishDetailCityHeadlinearAdapter publishDetailCityHeadlinearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_decoration_city_headlinear);
        ButterKnife.bind(this);
    }

    @Override
    protected PublishContract.IPublishDetailPresenter presenter() {
        return new PublishDetailPresenter(this, this);
    }

    @Override
    protected void init() {
        publishTitletou.setText("商家头条");
        publishbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        String decorateCity = getIntent().getStringExtra("three_dir_id");
        //请求商家头条的轮播图
        getPresenter().requestPublishCityBanner(decorateCity);
        //请求商家头条的信息
        getPresenter().requestPublishCityHeadlines(decorateCity);

          /*第一个banner*/
        cityLoopViewPagerLayout1.setLoop_ms(3000);//轮播的速度(毫秒)
        cityLoopViewPagerLayout1.setLoop_duration(800);//滑动的速率(毫秒)
        cityLoopViewPagerLayout1.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        cityLoopViewPagerLayout1.setIndicatorLocation(IndicatorLocation.Right);
        cityLoopViewPagerLayout1.initializeData(context);//初始化数据
        cityLoopViewPagerLayout1.setOnLoadImageViewListener(new OnLoadImageViewListener() {
            @Override
            public void onLoadImageView(ImageView imageView, Object parameter) {
                ImageHelper.getInstance().displayDefinedImage(String.valueOf(parameter), imageView,
                        R.mipmap.app_default_icon_1, R.mipmap.app_default_icon_1);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        cityLoopViewPagerLayout1.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
                if (!TextUtils.isEmpty(banner.get(index).skipUrl)) {
                    AppUtils.skipBrowser(context, banner.get(index).skipUrl);
                }
            }
        });
    }

    @Override
    public void initViewsAndEvents() {
        publishbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        String decorativeId = getIntent().getStringExtra("product_id");
//        if (TextUtils.isEmpty(product_id)) {
//            T.showShort(context, "数据异常");
//            return;
//        }
//        ProductIn productIn = new ProductIn();
//        productIn.setProduct_id(decorativeId);
//        //网络请求消息
//        getPresenter().requestDetail(productIn);


    }

    //展示装饰城中的商家头条的轮播图
    @Override
    public void showPublishCityBannerSuccess(List<BannerInfo> list) {
        cityLoopViewPagerLayout1.setLoopData((ArrayList<BannerInfo>) list);
    }

    @Override
    public void showPublishCityHeadlinearsSuccess(DecorationCityHeadlinearDetai[] headlinears) {
        final List<DecorationCityHeadlinearDetai> list = new ArrayList<>();
        list.addAll(Arrays.asList(headlinears));
        publishDetailCityHeadlinearAdapter = new PublishDetailCityHeadlinearAdapter(list, context);
        publishlistview.setAdapter(publishDetailCityHeadlinearAdapter);

    }


    //展示商家头条的轮播图
    @Override
    public void showPublishsuccess(List<BannerInfo> list) {
    }

    @Override
    public void showPublishHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail) {
//        final List<HeadlinearsDetail> list = new ArrayList<>();
//        list.addAll(Arrays.asList(headlinearsDetail));
//        PublishDetailHeadlinearAdapter pdh = new PublishDetailHeadlinearAdapter(list, context);
//        publishlistview.setAdapter(pdh);
    }

    //展示请求回来的工匠信息
    @Override
    public void responseDetail(SupplyDetail supplyDetail) {
        this.supplyDetail = supplyDetail;
//        publishTitle.setText(supplyDetail.getProduct_name());
//        publishContent.setText(supplyDetail.getProduct_desc());
    }

    @Override
    public void responseCollect(String msg) {
        T.showShort(context, msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        showShare();
//        ShareUtils.onActivityResult(PublishDetailActivity.this, requestCode, resultCode, data);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
//        appswipe.setRefreshing(false);
    }

    @Override
    protected void onDestoryActivity() {
        cityLoopViewPagerLayout1.stopLoop();

    }
}

