package com.sky.shop.presenter.activity;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.BindIn;
import com.sky.shop.bean.BindOut;
import com.sky.shop.bean.UpdatePwdIn;
import com.sky.shop.contract.UserContract;
import com.sky.shop.model.SafeCenterModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 安全中心
 */

public class SafeCenterPresenter extends BasePresenter<UserContract.ISafeCenterView>
        implements UserContract.ISafeCenterPresenter {

    private UserContract.ISafeCenterModel iSafeCenterModel;

    /**
     * 构造
     */
    public SafeCenterPresenter(Context context,
                               UserContract.ISafeCenterView iSafeCenterView) {
        super(context, iSafeCenterView);
        this.iSafeCenterModel = new SafeCenterModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iSafeCenterModel){
            iSafeCenterModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void queryUserDetail() {
        iSafeCenterModel.queryUserDetail();
    }

    @Override
    public void responseUserInfo(BindOut bindOut) {
        getView().refreshView(bindOut);
    }

    @Override
    public void updatePwd(UpdatePwdIn updatePwdIn) {
        iSafeCenterModel.updatePwd(updatePwdIn);
    }

    @Override
    public void responseUpdatePwd(String pwd) {
        getView().responseUpdatePwd(pwd);
    }

    @Override
    public void bindData(BindIn bindIn) {
        iSafeCenterModel.bindData(bindIn);
    }

    @Override
    public void responseBindData(String msg) {
        getView().responseBindData(msg);
    }
}