package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.UserContract;
import com.sky.shop.presenter.activity.RegisterActivityPresenter;

/**
 * 注册
 * Created by sky on 2017/2/18.
 */

public class RegisterModel extends BaseModel<RegisterActivityPresenter> implements UserContract.IRegisterModel{

    public RegisterModel(Context context, RegisterActivityPresenter registerActivityPresenter){
        super(context, registerActivityPresenter);
    }

    @Override
    public void register(ForgetIn forgetIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                .requestRegister(forgetIn), new HttpUtils.IHttpCallBackListener() {
            @Override
            public void onSuccess(String success) {
                UserBean info = new Gson().fromJson(success, UserBean.class);
                UserBean.getInstance().setUserInfo(info);
                UserBean.getInstance().setUserCache(info);
                getPresenter().showSuccess();
            }

            @Override
            public void onFailure(String error) {
                getPresenter().showError(error);
            }
        }));
    }
}
