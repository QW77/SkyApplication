package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.ProductIntroduceIn;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.ProductIntroducePresenter;

/**
 * 店铺简介
 * Created by sky on 2017/2/18.
 */

public class ProductIntroduceModel extends BaseModel<ProductIntroducePresenter>
        implements ShopContract.IProductIntroduceModel{

    public ProductIntroduceModel(Context context, ProductIntroducePresenter productIntroducePresenter){
        super(context, productIntroducePresenter);
    }

    @Override
    public void requestDescData() {
        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setUser_id(UserBean.getInstance().getCacheUid());
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

    @Override
    public void requestShopIntroduce(ProductIntroduceOut productIntroduceOut) {
        productIntroduceOut.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .updateShopIntroduce(UserBean.getInstance().getCacheUid(), productIntroduceOut),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseShopInfo("修改成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void postlonlat(double longitude, double latitude) {

        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .postlonlat(UserBean.getInstance().getCacheUid(),Double.toString(longitude),Double.toString(latitude)),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {

                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

}
