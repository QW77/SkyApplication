package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.contract.UserContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MineModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 个人中心
 */

public class MineActivityPresenter extends BasePresenter<UserContract.IMineView> implements UserContract.IMinePresenter {

    private UserContract.IMineModel iMineModel;

    /**
     * 构造
     */
    public MineActivityPresenter(Context context, UserContract.IMineView mIMineView) {
        super(context, mIMineView);
        this.iMineModel = new MineModel(context, this);
    }

    @Override
    public void refreshUserInfo() {
        getView().showProgress();
        iMineModel.getUserData();
    }

    @Override
    public void updateInfo() {
        getView().showUserInfo();
        getView().hideProgress();
    }

    @Override
    public void showError(String msg) {
        getView().showError(msg);
        getView().hideProgress();
    }

    @Override
    public void requestMobile() {
        iMineModel.requestMobile();
    }

    @Override
    public void responseMobile(String msg) {
        getView().showMobile(msg);
    }

    @Override
    public void destroy() {
        if (null != iMineModel){
            iMineModel.destroy();
        }
        super.destroy();
    }
}