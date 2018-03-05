package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.CenterActivityPresenter;

/**
 * 个人中心
 * Created by sky on 2017/2/18.
 */

public class CenterModel extends BaseModel<CenterActivityPresenter> implements UserContract.ICenterModel{

    public CenterModel(Context context, CenterActivityPresenter centerActivityPresenter){
        super(context, centerActivityPresenter);
    }

    @Override
    public void getUserInfo() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCenter(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        UserBean userBean = new Gson().fromJson(success, UserBean.class);
                        UserBean.getInstance().setUserInfo(userBean);
                        getPresenter().showSuccess();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void setUserInfo() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestSettingInfo(UserBean.getInstance().getCacheUid(), UserBean.getInstance()),
                new HttpUtils.IHttpCallBackListener() {

                    @Override
                    public void onSuccess(String success) {
                        UserBean userBean = new Gson().fromJson(success, UserBean.class);
                        UserBean.getInstance().setUserInfo(userBean);
                        getPresenter().showOnSuccess("操作成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
