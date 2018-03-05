package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.QQWeixinIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.QQContract;
import com.sky.shop.model.QQModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * qq weixin自动登录
 */

public class QQActivityPresenter extends BasePresenter<QQContract.IQQView>
        implements QQContract.IQQPresenter {
    private QQContract.IQQModel iqqModel;

    /**
     * 构造
     */
    public QQActivityPresenter(Context context,
                               QQContract.IQQView iqqView) {
        super(context, iqqView);
        this.iqqModel = new QQModel(context, this);
    }

    @Override
    public void qqLogin(QQWeixinIn qqWeixinIn) {
        iqqModel.qqLogin(qqWeixinIn);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void weixinLogin(QQWeixinIn qqWeixinIn) {
        iqqModel.weixinLogin(qqWeixinIn);
    }

    @Override
    public void refreshData(UserBean userBean) {
        UserBean.getInstance().setUserInfo(userBean);
        UserBean.getInstance().setUserCache(userBean);
        getView().showUserInfo();
    }

    @Override
    public void destroy() {
        if (null != iqqModel){
            iqqModel.destroy();
        }
        super.destroy();
    }

}