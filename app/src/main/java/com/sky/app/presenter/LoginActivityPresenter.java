package com.sky.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.utility.IMNotificationUtils;
import com.sky.app.bean.BindBean;
import com.sky.app.bean.ForgetIn;
import com.sky.app.bean.ThidBindIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.LoginModel;
import com.sky.app.ui.activity.openIM.OpenIMUserBean;
import com.sky.app.utils.LogUtil;

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
        if (null != iLoginModel) {
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
        YWLogin(userBean.getUser_id(), userBean.getPwd(), "24629506");
        getView().showUserInfo();
    }

    /**
     * 登陆云旺IM
     *
     * @param user_id  用户id
     * @param password 登陆密码
     * @param APP_KEY  云旺key
     */
    private void YWLogin(String user_id, String password, String APP_KEY) {
        if (TextUtils.isEmpty(user_id) || TextUtils.isEmpty(password) || TextUtils.isEmpty(APP_KEY)) {
            LogUtil.e(getClass().getName(), "云旺登陆参数为空");
            return;
        }
        YWIMKit mIMKit = YWAPI.getIMKitInstance(user_id, APP_KEY);
        //开始登录
        IYWLoginService loginService = mIMKit.getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(user_id, password);
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                LogUtil.e(getClass().getName(), "登陆成功回调");
            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(int errCode, String description) {
                LogUtil.e(getClass().getName(), "登陆失败回调=errCode=" + errCode + "=description=" + description);

                //用户不存在
                if (errCode == 1) {
                    return;
                }
                //密码错误
                if (errCode == 2) {
                    return;
                }
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
            }
        });
    }

    @Override
    public void queryUserIsBindMobile(ThidBindIn thidBindIn) {
        iLoginModel.queryUserIsBindMobile(thidBindIn);
    }

    @Override
    public void responseUserIsBindMobile(BindBean bindBean) {

        if (bindBean.getCode() == Constants.HttpStatus.HTTP_OK_STATUS) {//有数据
            if (TextUtils.isEmpty(bindBean.getRetData().getMobile())) {
                getView().responseIsBindMobile(false, "");
            } else {
                getView().responseIsBindMobile(true, bindBean.getRetData().getMobile());
            }
        } else if (bindBean.getCode() == 200013) {//没有用户
            getView().responseIsBindMobile(false, "");
        } else {
            getView().showError("数据错误");
        }
    }
}