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
import com.sky.app.ui.adapter.PublishDetailHeadlinearAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishDetailHeadlinearActivity extends BaseViewActivity<PublishContract.IPublishDetailPresenter>
        implements PublishContract.IPublishDetailView {

    SupplyDetail supplyDetail;

    @BindView(R.id.publish_title_tou)
    TextView publishTitleHead;
    @BindView(R.id.publish_normal_toolbar)
    Toolbar publishNormalToolbar;
    @BindView(R.id.publish_mLoopViewPagerLayout1)
    LoopViewPagerLayout publishMLoopViewPagerLayout1;
    //    @BindView(R.id.publish_swip)
//    SwipeRefreshLayout publishSwip;
    @BindView(R.id.publish_recycleview_list)
    ListView publishRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_detail_headlinear);
        ButterKnife.bind(this);
    }

    @Override
    protected PublishContract.IPublishDetailPresenter presenter() {
        return new PublishDetailPresenter(this, this);
    }

    @Override
    protected void init() {
        publishTitleHead.setText(R.string.app_publishgongjiang_detail_string);
        publishNormalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

          /*第一个banner*/
        publishMLoopViewPagerLayout1.setLoop_ms(3000);//轮播的速度(毫秒)
        publishMLoopViewPagerLayout1.setLoop_duration(800);//滑动的速率(毫秒)
        publishMLoopViewPagerLayout1.setLoop_style(LoopStyle.Empty);//轮播的样式-默认empty
        publishMLoopViewPagerLayout1.setIndicatorLocation(IndicatorLocation.Right);
        publishMLoopViewPagerLayout1.initializeData(context);//初始化数据
        publishMLoopViewPagerLayout1.setOnLoadImageViewListener(new OnLoadImageViewListener() {
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
        publishMLoopViewPagerLayout1.setOnBannerItemClickListener(new OnBannerItemClickListener() {
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
        publishNormalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        String product_id = getIntent().getStringExtra("product_id");
//        if (TextUtils.isEmpty(product_id)) {
//            T.showShort(context, "数据异常");
//            return;
//        }
        ProductIn productIn = new ProductIn();
//        productIn.setProduct_id(product_id);
//        //网络请求消息
//        getPresenter().requestDetail(productIn);
        //请求工匠头条的轮播图
        getPresenter().requestPublishBanner();
        //请求工匠头条的信息
        getPresenter().requestPublishHeadlines();

    }

    @Override
    public void showPublishCityBannerSuccess(List<BannerInfo> list) {

    }

    @Override
    public void showPublishCityHeadlinearsSuccess(DecorationCityHeadlinearDetai[] headlinears) {

    }


    //展示工匠头条的轮播图
    @Override
    public void showPublishsuccess(List<BannerInfo> list) {
//        appswipe.setRefreshing(false);
        publishMLoopViewPagerLayout1.setLoopData((ArrayList<BannerInfo>) list);
    }

    //展示工匠头条的信息
    @Override
    public void showPublishHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail) {
        final List<HeadlinearsDetail> list = new ArrayList<>();
        list.addAll(Arrays.asList(headlinearsDetail));
        PublishDetailHeadlinearAdapter pdh = new PublishDetailHeadlinearAdapter(list, context);
        publishRecyclerView.setAdapter(pdh);
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

//    @OnClick(R.id.collect)
//    void clickCollect() {
//        if (null == supplyDetail) {
//            T.showShort(context, "收藏数据错误");
//            return;
//        }
//        CollectPubIn collectPubIn = new CollectPubIn();
//        collectPubIn.setType("3");
//        collectPubIn.setCollect_value(supplyDetail.getProduct_id());
//        getPresenter().requestCollect(collectPubIn);
//    }

//    @OnClick(R.id.call_phone)
//    void callPhone() {
//        if (TextUtils.isEmpty(supplyDetail.getUser_phone())) {
//            T.showShort(context, "没有手机号码");
//            return;
//        }
//        AppUtils.callPhone(context, supplyDetail.getUser_phone());
//    }

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
        publishMLoopViewPagerLayout1.stopLoop();

    }
}
