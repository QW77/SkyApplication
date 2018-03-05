package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.AreaList;
import com.sky.shop.bean.BaseUser;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.DecorationCityList;
import com.sky.shop.bean.Empty;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.SearchDecorationCity;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.ShopDataPresenter;


/**
 * 商铺资料
 * Created by sky on 2017/2/18.
 */

public class ShopDataModel extends BaseModel<ShopDataPresenter> implements ShopContract.IShopDataModel{

    public ShopDataModel(Context context, ShopDataPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void queryUserInfo() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .queryUserInfo(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseUserInfo(new Gson().fromJson(success, UserBean.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void updateShopInfo(ProductIntroduceOut productIntroduceOut) {
        productIntroduceOut.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .updateShopIntroduce(UserBean.getInstance().getCacheUid(), productIntroduceOut),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseUpdateShopInfo("操作成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestAreaList() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchPlace(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseAreaList(new Gson().fromJson(success, AreaList.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void requestDecorateCity(SearchDecorationCity searchDecorationCity) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchDecorationCity(searchDecorationCity),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDecorateCity(new Gson().fromJson(success, DecorationCityList.class));
                    }
                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void requestCompanyType() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchFactory(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseCompanyType(new Gson().fromJson(success, CategoryList.class));
                    }
                    @Override
                    public void onFailure(String error) {
                        getPresenter().getView().showError(error);
                    }
                }));
    }
}
