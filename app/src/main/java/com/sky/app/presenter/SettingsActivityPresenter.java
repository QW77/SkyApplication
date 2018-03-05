package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.BindIn;
import com.sky.app.bean.BindOut;
import com.sky.app.bean.UpdatePwdIn;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.SettingModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 设置
 */

public class SettingsActivityPresenter extends BasePresenter<UserContract.ISettingView>
        implements UserContract.ISettingPresenter {

    private UserContract.ISettingModel iSettingModel;

    /**
     * 构造
     */
    public SettingsActivityPresenter(Context context,
                                     UserContract.ISettingView mISettingView) {
        super(context, mISettingView);
        this.iSettingModel = new SettingModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iSettingModel){
            iSettingModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void queryUserDetail() {
        iSettingModel.queryUserDetail();
    }

    @Override
    public void responseUserInfo(BindOut bindOut) {
        getView().refreshView(bindOut);
    }

    @Override
    public void updatePwd(UpdatePwdIn updatePwdIn) {
        iSettingModel.updatePwd(updatePwdIn);
    }

    @Override
    public void responseUpdatePwd(String pwd) {
        getView().responseUpdatePwd(pwd);
    }

    @Override
    public void bindData(BindIn bindIn) {
        iSettingModel.bindData(bindIn);
    }

    @Override
    public void responseBindData(String msg) {
        getView().responseBindData(msg);
    }
}