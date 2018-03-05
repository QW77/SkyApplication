package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.SellMessageComplete;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.UploadActivityPresenter;

/**
 * Created by hongbang on 2017/5/12.
 */

public class SellMessageCompleteModel extends BaseModel<UploadActivityPresenter> implements ShopContract.IUploadModel {

    public SellMessageCompleteModel(Context context, UploadActivityPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void sellRegister(SellMessageComplete sellMessageComplete) {
        sellMessageComplete.setCity_id("025");
        sellMessageComplete.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .sellRegister(UserBean.getInstance().getCacheUid(), sellMessageComplete),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBean userBean = new Gson().fromJson(success, UserBean.class);
                        UserBean.getInstance().setUserInfo(userBean);
                        UserBean.getInstance().setUserCache(userBean);
                        getPresenter().registerSuccess(userBean);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
