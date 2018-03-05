package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.BaseUser;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.UserContract;
import com.sky.shop.presenter.activity.LoginActivityPresenter;
import com.sky.shop.ui.activity.OpenIMShop.OpenIMUserBean;

/**
 * Created by sky on 2017/2/18.
 * 登录
 */

public class LoginModel extends BaseModel<LoginActivityPresenter> implements UserContract.ILoginModel{

    public LoginModel(Context context, LoginActivityPresenter loginActivityPresenter){
        super(context, loginActivityPresenter);
    }

    @Override
    public void login(ForgetIn forgetIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestLogin(forgetIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().refreshData(new Gson().fromJson(success, UserBean.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
    /**
     * openIM注册
     */
    @Override
    public void registerOpenIM(final OpenIMUserBean openIMUserBean) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getUser_id());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .openIMRegister(UserBean.getInstance().getUser_id(),openIMUserBean),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().loginOpenIM(openIMUserBean);
                    }

                    @Override
                    public void onFailure(String error) {
//                        getPresenter().showError(error);
                        getPresenter().loginOpenIM(openIMUserBean);
                    }
                }));
    }

    /**
     * openIM登录
     */
    @Override
    public void loginOpenIM(final OpenIMUserBean openIMUserBean) {
//        BaseUser baseUser = new BaseUser();
//        baseUser.setUser_id(UserBean.getInstance().getUser_id());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .openIMLogin(UserBean.getInstance().getUser_id(),openIMUserBean),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
//                        getPresenter().showOpenSuccess(new Gson().fromJson(success, OpenIMUserBean.class));
                        getPresenter().showOpenSuccess(openIMUserBean);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showOpenSuccess(openIMUserBean);
//                        getPresenter().showOpenSuccess(error);

                    }
                }));
    }

    /**
     * openIM账号信息修改
     */
    public void upDateOpenIM(final OpenIMUserBean openIMUserBean) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getUser_id());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .openIMUpDate(UserBean.getInstance().getUser_id(),openIMUserBean),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().loginOpenIM(openIMUserBean);
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().loginOpenIM(openIMUserBean);
                    }
                }));
    }
//    @Override
//    public void queryUserIsBindMobile(ThidBindIn thidBindIn) {
//        SimpleHttpUtils.getInstance((Activity) context).post(Constants.Url.BASE_URL +
//                        "user/un/get_user_info_by_openid", new Gson().toJson(thidBindIn),
//                new HttpUtils.IHttpCallBackListener() {
//                    @Override
//                    public void onSuccess(String success) {
//                        getPresenter().responseUserIsBindMobile(new Gson().fromJson(success, BindBean.class));
//                    }
//
//                    @Override
//                    public void onFailure(String error) {
//                        getPresenter().showError(error);
//                    }
//                });
//    }
}
