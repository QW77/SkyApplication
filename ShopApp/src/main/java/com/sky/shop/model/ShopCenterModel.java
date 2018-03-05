package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductCategoryOut;
import com.sky.shop.bean.ProductIn;
import com.sky.shop.bean.ProductList;
import com.sky.shop.bean.ProductUpAndDownIn;
import com.sky.shop.bean.SearchProductRequest;
import com.sky.shop.bean.ShopProductIn;
import com.sky.shop.bean.ShopProductOut;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.ShopCenterPresenter;

/**
 * 商铺个人中心
 * Created by sky on 2017/2/18.
 */

public class ShopCenterModel extends BaseModel<ShopCenterPresenter> implements ShopContract.IShopCenterModel{

    public ShopCenterModel(Context context, ShopCenterPresenter shopCenterPresenter){
        super(context, shopCenterPresenter);
    }

    @Override
    public void requestHomeData(ShopProductIn shopProductIn) {
        shopProductIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestShopHomeData(shopProductIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseHomeData(new Gson().fromJson(success, ShopProductOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestAllProduct(SearchProductRequest searchProductRequest, final int flag) {
        searchProductRequest.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).searchProduct(searchProductRequest),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag){
                            case Constants.ListStatus.LOADMORE:
                                getPresenter().loadMoreData(new Gson().fromJson(success, ProductList.class));
                                break;
                            case Constants.ListStatus.REFRESH:
                                getPresenter().refreshData(new Gson().fromJson(success, ProductList.class));
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void delProduct(ProductIn productIn) {
        productIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .delProduct(UserBean.getInstance().getCacheUid(), productIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDelProduct("删除商品成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void upAndDownProduct(ProductUpAndDownIn productUpAndDownIn) {
        productUpAndDownIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .upAndDownProduct(UserBean.getInstance().getCacheUid(), productUpAndDownIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseUpAndDownProduct("操作商品成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestProductCategory(ProductCategoryIn productCategoryIn) {
        productCategoryIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestProductCategory(productCategoryIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseProductCategory(new Gson().fromJson(success, ProductCategoryOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
