package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.ProductCategoryIn;
import com.sky.app.bean.ProductCategoryOut;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.ProductList;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.ShopProductIn;
import com.sky.app.bean.ShopProductOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.ShopCenterPresenter;
import com.sky.app.ui.activity.seller.UserGPS;

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
        searchProductRequest.setState(1);
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
    public void requestProductCategory(final ProductCategoryIn productCategoryIn) {
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

    @Override
    public void requestDescData(ProductIntroduceIn productIntroduceIn) {
        productIntroduceIn.setLogin_user_id(UserBean.getInstance().getCacheUid());
        productIntroduceIn.setLogin_user_name(UserBean.getInstance().getUserCache().getNick_name());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestDescData(productIntroduceIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDescData(new Gson().fromJson(success, ProductIntroduceOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

//    @Override
//    public void getLonglat(String id) {
//        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
//                HttpUtils.getInstance(context).createApi(ApiService.class).getUserGps(id),
//                new HttpUtils.IHttpCallBackListener() {
//                    @Override
//                    public void onSuccess(String success) {
//                        UserGPS userGPS = new Gson().fromJson(success, UserGPS.class);
//                        getPresenter().setLonglat(userGPS);
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//                        getPresenter().showError(error);
//                    }
//                }
//        ));
//    }
}
