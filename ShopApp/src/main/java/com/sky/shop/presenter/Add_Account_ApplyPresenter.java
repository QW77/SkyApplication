package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.Add_Account_ApplyBean;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.model.Add_Account_ApplyModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 添加提现账户
 */

public class Add_Account_ApplyPresenter extends BasePresenter<AccountContract.Add_Account_ApplyView>
        implements AccountContract.Add_Account_ApplyPresenter {

    private Add_Account_ApplyModel model;

    /**
     * 构造
     */
    public Add_Account_ApplyPresenter(Context context,
                                      AccountContract.Add_Account_ApplyView iApplyAccountView) {
        super(context, iApplyAccountView);
        this.model = new Add_Account_ApplyModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != model){
            model.destroy();
        }
        super.destroy();
    }

    @Override
    public void forget(String userid,Add_Account_ApplyBean info) {
        model.getAdd_Account_ApplyModel(userid,info);
    }


    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void showSuccess() {
        getView().onSuccess();
    }
}