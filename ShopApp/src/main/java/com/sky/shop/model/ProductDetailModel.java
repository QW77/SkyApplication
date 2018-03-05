package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.ProductDetailRequest;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ProductContract;
import com.sky.shop.presenter.ProductDetailPresenter;

/**
 * 商品详情
 * Created by sky on 2017/2/18.
 */

public class ProductDetailModel extends BaseModel<ProductDetailPresenter>
        implements ProductContract.IProDetailModel{

    public ProductDetailModel(Context context, ProductDetailPresenter productDetailPresenter){
        super(context, productDetailPresenter);
    }

    @Override
    public void getProductDetail(ProductDetailRequest productDetailRequest) {
        productDetailRequest.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).productDetail(productDetailRequest),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().productDetailSuccess(new Gson().fromJson(success, ProductDeatilResponse.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
