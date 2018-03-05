package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.Empty;
import com.sky.shop.bean.FirstCategoryOut;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.SellerFristCategoryPresenter;

/**
 * Created by hongbang on 2017/5/11.
 */

public class SellerFristCategoryModel extends BaseModel<SellerFristCategoryPresenter>
        implements ShopContract.ISellerFirstCategoryModel {

    public SellerFristCategoryModel(Context context, SellerFristCategoryPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void getFirstCatogoryData() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchFirstCatogory(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().firstCatogoryDataSuccess(new Gson().fromJson(success, FirstCategoryOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);

                    }
                })
        );

    }
}
