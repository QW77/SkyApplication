package com.sky.shop.ui.activity.OpenIMShop;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.UserBean;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 个人中心
 */

public class OpenIMLoginActivityPresenter extends BasePresenter<UserOpenIM.IOpenIMView>
        implements UserOpenIM.IOpenIMPresenter {

    private UserOpenIM.IOpenIMModel iOpenIMModel;

    /**
     * 构造
     */
    public OpenIMLoginActivityPresenter(Context context,UserOpenIM.IOpenIMView iOpenIMView) {
        super(context, iOpenIMView);
        this.iOpenIMModel = new OpenIMLoginModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iOpenIMModel) {
            iOpenIMModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void registerOpenIM(OpenIMUserBean openIMUserBean) {
        iOpenIMModel.registerOpenIM(openIMUserBean);
    }

    @Override
    public void loginOpenIM(OpenIMUserBean openIMUserBean) {
        iOpenIMModel.loginOpenIM(openIMUserBean);
    }

    @Override
    public void upDateOpenIM(OpenIMUserBean openIMUserBean) {
        iOpenIMModel.upDateOpenIM(openIMUserBean);
    }

    @Override
    public void showError(String msg) {
        getView().showError(msg);
    }


    @Override
    public void showOpenSuccess(OpenIMUserBean openIMUserBean) {
        getView().showOnSuccess(openIMUserBean);
    }

}