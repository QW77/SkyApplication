package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.CommentList;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.ProductDeatilResponse;
import com.sky.app.bean.ProductDetailRequest;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.ProductDetailPresent;

/**
 * Created by hongbang on 2017/5/8.
 */

public class ProductDetailModel extends BaseModel<ProductDetailPresent> implements ShopContract.IProductDetailModel {


    public ProductDetailModel(Context context, ProductDetailPresent presenter) {
        super(context, presenter);
    }

    @Override
    public void getProductDetail(ProductDetailRequest productDetailRequest) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).productDetail(productDetailRequest),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Log.e("7777777777",success);
                        ProductDeatilResponse productDeatilResponse = new Gson().fromJson(success, ProductDeatilResponse.class);
                        getPresenter().productDetailSuccess(productDeatilResponse);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void getProductComment(CommentRequest commentRequest) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).productMessage(commentRequest),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {

                        CommentList commentResponse = new Gson().fromJson(success, CommentList.class);
                        getPresenter().ProductCommentSuccess(commentResponse);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().getView().showError(error);
                    }
                }));
    }

    @Override
    public void addShoppingCar(SingleOrderIn singleOrderIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAddShoppingCar(UserBean.getInstance().getCacheUid(), singleOrderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showAddResult("成功添加到购物车");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }


}
