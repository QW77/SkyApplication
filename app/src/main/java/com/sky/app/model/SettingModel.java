package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.BindIn;
import com.sky.app.bean.BindOut;
import com.sky.app.bean.UpdatePwdIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.SettingsActivityPresenter;

/**
 * 设置
 * Created by sky on 2017/2/18.
 */

public class SettingModel extends BaseModel<SettingsActivityPresenter> implements UserContract.ISettingModel{

    public SettingModel(Context context, SettingsActivityPresenter settingActivityPresenter){
        super(context, settingActivityPresenter);
    }

    @Override
    public void queryUserDetail() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .queryBindInfo(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseUserInfo(new Gson().fromJson(success, BindOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void updatePwd(UpdatePwdIn updatePwdIn) {
        updatePwdIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .updateUserPwd(UserBean.getInstance().getCacheUid(), updatePwdIn),
                new HttpUtils.IHttpCallBackListener() {

                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseUpdatePwd("成功修改密码");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void bindData(BindIn bindIn) {
        bindIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .updateUserPwd(UserBean.getInstance().getCacheUid(), bindIn),
                new HttpUtils.IHttpCallBackListener() {

                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseBindData("绑定成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
