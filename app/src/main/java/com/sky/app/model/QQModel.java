package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.QQWeixinIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.QQContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.QQActivityPresenter;

/**
 * Created by sky on 2017/2/18.
 * 自动登录
 */

public class QQModel extends BaseModel<QQActivityPresenter> implements QQContract.IQQModel{

    public QQModel(Context context, QQActivityPresenter qqActivityPresenter){
        super(context, qqActivityPresenter);
    }

    @Override
    public void qqLogin(QQWeixinIn qqWeixinIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestQQLogin(qqWeixinIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBean info = new Gson().fromJson(success, UserBean.class);
                        getPresenter().refreshData(info);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void weixinLogin(QQWeixinIn qqWeixinIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestWeixinLogin(qqWeixinIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBean info = new Gson().fromJson(success, UserBean.class);
                        getPresenter().refreshData(info);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
