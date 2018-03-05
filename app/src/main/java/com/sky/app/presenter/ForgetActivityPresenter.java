package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.ForgetIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.ForgetModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 忘记密码
 */

public class ForgetActivityPresenter extends BasePresenter<UserContract.IForgetView>
        implements UserContract.IForgetPresenter {

    private UserContract.IForgetModel iForgetModel;

    /**
     * 构造
     */
    public ForgetActivityPresenter(Context context, UserContract.IForgetView mIForgetView) {
        super(context, mIForgetView);
        this.iForgetModel = new ForgetModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iForgetModel){
            iForgetModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void forget(ForgetIn forgetIn) {
        iForgetModel.forget(forgetIn);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void showSuccess() {
        //清理缓存
        UserBean.getInstance().clearCode();
        getView().showSuccess();
    }
}