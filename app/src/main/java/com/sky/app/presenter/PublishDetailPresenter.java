package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.BannerDetail;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.DecorationCityHeadlinearDetai;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.library.component.banner.modle.BannerInfo;
import com.sky.app.model.PublishDetailModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 发布详情处理业务逻辑
 */
public class PublishDetailPresenter extends BasePresenter<PublishContract.IPublishDetailView>
        implements PublishContract.IPublishDetailPresenter {

    private PublishContract.IPublishDetailModel iPublishDetailModel;

    /**
     * 构造
     */
    public PublishDetailPresenter(Context context, PublishContract.IPublishDetailView iPublishDetailView) {
        super(context, iPublishDetailView);
        iPublishDetailModel = new PublishDetailModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iPublishDetailModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void requestPublishBanner() {
        BannerIn bannerIn = new BannerIn();
        bannerIn.setStart_type("11");
        bannerIn.setEnd_type("14");
        iPublishDetailModel.requestPublishBanner(bannerIn);
    }

    //请求装饰城中的商家头条轮播图
    @Override
    public void requestPublishCityBanner(String id) {
        iPublishDetailModel.requestPublishCityBanner(id);

    }
    //请求装饰城中的商家头条信息
    @Override
    public void requestPublishCityHeadlines(String id) {
        iPublishDetailModel.requestPublishCityHeadlines(id);

    }

    @Override
    public void showPublishCityHeadlinearsSuccess( DecorationCityHeadlinearDetai[] headlinears) {
        getView().showPublishCityHeadlinearsSuccess(headlinears);
    }

    //请求商家头条轮播图
    @Override
    public void showPublishCityBannerSuccess(BannerOut bannerOut) {
        getView().showPublishCityBannerSuccess(renderBanner(bannerOut));
    }
    //请求工匠头条轮播图
    @Override
    public void showPublishsuccess(BannerOut bannerOut) {
        getView().showPublishsuccess(renderBanner(bannerOut));

    }

    //请求工匠头条信息
    @Override
    public void requestPublishHeadlines() {
        iPublishDetailModel.requestPublishHeadlines();

    }

    //展示工匠头条信息
    @Override
    public void showPublishHeadlinearsSuccess(HeadlinearsDetail[] headlinearsDetail) {
        getView().showPublishHeadlinearsSuccess(headlinearsDetail);
    }

    /**
     * 渲染广告
     *
     * @return
     */
    private List<BannerInfo> renderBanner(BannerOut bannerOut) {
        List<BannerDetail> detail = bannerOut.getList();
        List<BannerInfo> bannerInfo = new ArrayList<>();
        for (BannerDetail b : detail) {
            bannerInfo.add(new BannerInfo(b.getNews_title_image_url(), b.getNews_title(), b.getNews_title_image_jump_url()));
        }
        return bannerInfo;
    }


    @Override
    public void requestDetail(ProductIn productIn) {
        iPublishDetailModel.requestDetail(productIn);
    }

    @Override
    public void responseDetail(SupplyDetail supplyDetail) {
        getView().responseDetail(supplyDetail);
    }

    @Override
    public void requestCollect(CollectPubIn collectPubIn) {
        collectPubIn.setUser_id(UserBean.getInstance().getCacheUid());
        iPublishDetailModel.requestCollect(collectPubIn);
    }

    @Override
    public void responseCollect(String msg) {
        getView().responseCollect(msg);
    }
}