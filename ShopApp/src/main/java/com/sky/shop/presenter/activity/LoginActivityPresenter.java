package com.sky.shop.presenter.activity;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.UserContract;
import com.sky.shop.model.LoginModel;
import com.sky.shop.ui.activity.OpenIMShop.OpenIMUserBean;

/**
 * 登录
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 */

public class LoginActivityPresenter extends BasePresenter<UserContract.ILoginView>
        implements UserContract.ILoginPresenter {

    private UserContract.ILoginModel iLoginModel;

    /**
     * 构造
     */
    public LoginActivityPresenter(Context context, UserContract.ILoginView mILoginView) {
        super(context, mILoginView);
        this.iLoginModel = new LoginModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iLoginModel){
            iLoginModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void login(ForgetIn forgetIn) {
        iLoginModel.login(forgetIn);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void refreshData(UserBean userBean) {
        UserBean.getInstance().setUserInfo(userBean);
        UserBean.getInstance().setUserCache(userBean);
        getView().showUserInfo();
    }
    @Override
    public void registerOpenIM(OpenIMUserBean openIMUserBean) {
        iLoginModel.registerOpenIM(openIMUserBean);
    }

    @Override
    public void loginOpenIM(OpenIMUserBean openIMUserBean) {
        iLoginModel.loginOpenIM(openIMUserBean);
    }

    @Override
    public void upDateOpenIM(OpenIMUserBean openIMUserBean) {
        iLoginModel.upDateOpenIM(openIMUserBean);
    }



    @Override
    public void showOpenSuccess(OpenIMUserBean openIMUserBean) {
        getView().showOnSuccess(openIMUserBean);
    }
//    @Override
//    public void queryUserIsBindMobile(ThidBindIn thidBindIn) {
//        iLoginModel.queryUserIsBindMobile(thidBindIn);
//    }

//    @Override
//    public void responseUserIsBindMobile(BindBean bindBean) {
//        if (bindBean.getCode() == Constants.HttpStatus.HTTP_OK_STATUS){//有数据
//            if (TextUtils.isEmpty(bindBean.getRetData().getMobile())){
//                getView().responseIsBindMobile(false, "");
//            }else{
//                getView().responseIsBindMobile(true, bindBean.getRetData().getMobile());
//            }
//        } else if (bindBean.getCode() == 200013){//没有用户
//            getView().responseIsBindMobile(false, "");
//        } else {
//            getView().showError("数据错误");
//        }
//    }
}