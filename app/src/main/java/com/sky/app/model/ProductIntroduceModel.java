package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.ProductIntroducePresenter;

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
    public void requestDescData(ProductIntroduceIn productIntroduceIn) {
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
}
