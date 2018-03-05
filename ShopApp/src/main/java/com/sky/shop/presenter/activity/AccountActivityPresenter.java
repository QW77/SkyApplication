package com.sky.shop.presenter.activity;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.AccountMoney;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.model.AccountModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 账户中心
 */

public class AccountActivityPresenter extends BasePresenter<AccountContract.IAccountView>
        implements AccountContract.IAccountPresenter {

    private AccountContract.IAccountModel iAccountModel;

    /**
     * 构造
     */
    public AccountActivityPresenter(Context context,
                                    AccountContract.IAccountView iAccountView) {
        super(context, iAccountView);
        this.iAccountModel = new AccountModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iAccountModel){
            iAccountModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void requestAccountMoney() {
        iAccountModel.requestAccountMoney();
    }

    @Override
    public void responseAccountMoney(AccountMoney accountMoney) {
        getView().responseAccountMoney(accountMoney);
    }
}