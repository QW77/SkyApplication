package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.AccountBankList;
import com.sky.shop.bean.BankIn;
import com.sky.shop.bean.BaseUser;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.presenter.fragment.AccountDetailFragmentPresenter;

/**
 * 账户中心
 * Created by sky on 2017/2/18.
 */

public class AccountDetailModel extends BaseModel<AccountDetailFragmentPresenter> implements AccountContract.IAccountDetailModel{

    public AccountDetailModel(Context context, AccountDetailFragmentPresenter accountDetailFragmentPresenter){
        super(context, accountDetailFragmentPresenter);
    }

    @Override
    public void queryMyAccountList() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestMyAccountBank(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().refreshData(new Gson().fromJson(success, AccountBankList.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void del(BankIn bankIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .delRecord(UserBean.getInstance().getCacheUid(), bankIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showDelSuccess("删除成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
