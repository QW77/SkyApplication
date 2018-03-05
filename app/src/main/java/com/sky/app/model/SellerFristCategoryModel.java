package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.Empty;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.contract.SellerContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.SellerFristCategoryPresenter;

/**
 * Created by hongbang on 2017/5/11.
 */

public class SellerFristCategoryModel extends BaseModel<SellerFristCategoryPresenter>  implements SellerContract.ISellerFirstCategoryModel {

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
                        Log.i("88888",success.toString());
                        FirstCategoryOut categoryList = new Gson().fromJson(success, FirstCategoryOut.class);
                        getPresenter().firstCatogoryDataSuccess(categoryList);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);

                    }
                }));

    }
}
