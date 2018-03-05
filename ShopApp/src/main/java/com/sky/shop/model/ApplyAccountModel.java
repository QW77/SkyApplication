package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.ApplyAccountIn;
import com.sky.shop.bean.Empty;
import com.sky.shop.bean.Rate;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.presenter.fragment.ApplyAccountFragmentPresenter;

/**
 * 账户中心
 * Created by sky on 2017/2/18.
 */

public class ApplyAccountModel extends BaseModel<ApplyAccountFragmentPresenter>
        implements AccountContract.IApplyAccountModel{

    public ApplyAccountModel(Context context, ApplyAccountFragmentPresenter applyAccountFragmentPresenter){
        super(context, applyAccountFragmentPresenter);
    }

    @Override
    public void applyAccount(ApplyAccountIn applyAccountIn) {
        applyAccountIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestApplyAccount(UserBean.getInstance().getCacheUid(), applyAccountIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseApplyAccount("提现成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void requestWithDraw() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .getWithdrawRate(UserBean.getInstance().getCacheUid(), new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Rate rate = new Gson().fromJson(success, Rate.class);
                        getPresenter().responseWithDraw(rate.getValue());
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
