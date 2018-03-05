package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.BannerIn;
import com.sky.app.bean.BannerOut;
import com.sky.app.bean.Empty;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.bean.GoodShop;
import com.sky.app.bean.Goods;
import com.sky.app.bean.HeadLinearsIn;
import com.sky.app.bean.Headlinears;
import com.sky.app.bean.HeadlinearsDetail;
import com.sky.app.bean.SupplyIn;
import com.sky.app.bean.SupplyOut;
import com.sky.app.contract.HomeContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.HomeActivityPresenter;

/**
 * 首页请求
 * Created by sky on 2017/2/18.
 */

public class HomeModel extends BaseModel<HomeActivityPresenter> implements HomeContract.IHomeModel {

    public HomeModel(Context context, HomeActivityPresenter homeActivityPresenter) {
        super(context, homeActivityPresenter);
    }

    @Override
    public void requestBanner(BannerIn bannerIn, final int flag) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestBanner(bannerIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag) {
                            case 1:
                                BannerOut bannerOut = new Gson().fromJson(success, BannerOut.class);
                                getPresenter().showBannerSuccess(bannerOut);
                                break;
                            case 2:
                                getPresenter().showBanner2Success(new Gson().fromJson(success, BannerOut.class));
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void requestFirstCategory() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestFirstCategory(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        FirstCategoryOut firstCategoryOut = new Gson().fromJson(success, FirstCategoryOut.class);
                        getPresenter().showFirstCategorySuccess(firstCategoryOut);

                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }


    @Override
    public void requestHeadlines() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestHeadlines(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        HeadlinearsDetail[] headlinearsDetail = new Gson().fromJson(success, HeadlinearsDetail[].class);
                        getPresenter().showHeadlinearsSuccess(headlinearsDetail);
                    }

                    @Override
                    public void onFailure(String error) {
                        // TODO: 2017/12/23 0023  
//                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestSupply(SupplyIn supplyIn, final int flag) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestSupply(supplyIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag) {
                            case 1:
                                Class<SupplyOut> classOfT = SupplyOut.class;
                                SupplyOut supplyOut = new Gson().fromJson(success, SupplyOut.class);
                                getPresenter().showSupplySuccess(supplyOut);
                                break;
                            case 2:
                                SupplyOut supplyOut1 = new Gson().fromJson(success, SupplyOut.class);
                                getPresenter().showBuySuccess(supplyOut1);
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        // TODO: 2017/12/23 0023
//                        getPresenter().showError(error);
                    }
                }
        ));
    }

    //首页的品质好店
    @Override
    public void requestGoodShop(int i) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestGoodShop("1"),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        GoodShop goodShop = new Gson().fromJson(success, GoodShop.class);
                        getPresenter().showGoodShop(goodShop);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    //获取精品好货
    @Override
    public void requestGoods(int i) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestGoods("1"),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showGoodsSuccess(new Gson().fromJson(success, Goods.class));
                    }
                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

}
