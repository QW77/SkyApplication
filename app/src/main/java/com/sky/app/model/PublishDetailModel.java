package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.DecorationCityHeadlinearDetai;
import com.sky.app.bean.DecorationHeadlinearDetai;
import com.sky.app.bean.Empty;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.PublishDetailPresenter;

/**
 * Created by sky on 2017/2/18.
 * 发布详情
 */

public class PublishDetailModel extends BaseModel<PublishDetailPresenter>
        implements PublishContract.IPublishDetailModel {

    public PublishDetailModel(Context context, PublishDetailPresenter publishDetailPresenter) {
        super(context, publishDetailPresenter);
    }

    //请求商家头条中的banner
    @Override
    public void requestPublishCityBanner(String id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestZSCBanner(id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        BannerOut bannerOut = new Gson().fromJson(success, BannerOut.class);
                        getPresenter().showPublishCityBannerSuccess(bannerOut);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));


    }

    //请求工匠头条中的banner
    @Override
    public void requestPublishBanner(BannerIn bannerIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestBanner(bannerIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        BannerOut bannerOut = new Gson().fromJson(success, BannerOut.class);
                        getPresenter().showPublishsuccess(bannerOut);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));


    }

    //请求点击装饰城中商家头条中的数据
    @Override
    public void requestPublishCityHeadlines(String id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestDecorationHeadlines(id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        DecorationCityHeadlinearDetai[] headlinears = new Gson().fromJson(success, DecorationCityHeadlinearDetai[].class);
                        getPresenter().showPublishCityHeadlinearsSuccess(headlinears);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));

    }

    //请求工匠头条的数据
    @Override
    public void requestPublishHeadlines() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestHeadlines(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        HeadlinearsDetail[] headlinearsDetail = new Gson().fromJson(success, HeadlinearsDetail[].class);
                        getPresenter().showPublishHeadlinearsSuccess(headlinearsDetail);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestDetail(ProductIn productIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestSupplyDetail(productIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDetail(new Gson().fromJson(success, SupplyDetail.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestCollect(CollectPubIn collectPubIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCollectPublish(UserBean.getInstance().getCacheUid(), collectPubIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseCollect("收藏成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
