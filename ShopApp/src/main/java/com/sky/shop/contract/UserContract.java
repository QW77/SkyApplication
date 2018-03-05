package com.sky.shop.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.shop.bean.BindIn;
import com.sky.shop.bean.BindOut;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.Message;
import com.sky.shop.bean.MessageIn;
import com.sky.shop.bean.MessageList;
import com.sky.shop.bean.UpdatePwdIn;
import com.sky.shop.bean.UserBean;
import com.sky.shop.ui.activity.OpenIMShop.OpenIMUserBean;

import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 主页契约类
 */

public class UserContract {

    /**
     * 主页更新UI方法
     */
    public interface ILoginView extends IBaseView {
        /**
         * 展示用户
         */
        void showUserInfo();
        void showOnSuccess(OpenIMUserBean openIMUserBean);
//        void responseIsBindMobile(boolean isBind, String mobile);
    }

    /**
     * UI方法
     */
    public interface IRegisterView extends IBaseView {
        void showResult();
    }

    /**
     * UI方法
     */
    public interface IForgetView extends IBaseView {
        void showSuccess();
    }

    /**
     * 主页处理业务逻辑
     */
    public interface ILoginPresenter extends IBasePresenter {
        /**
         * 登录
         */
        void login(ForgetIn forgetIn);
        void registerOpenIM(OpenIMUserBean openIMUserBean);
        void loginOpenIM(OpenIMUserBean openIMUserBean);
        void upDateOpenIM(OpenIMUserBean openIMUserBean);
        void showOpenSuccess(OpenIMUserBean OpenIMUserBean);
        /**
         * 刷新用户界面
         * @param userBean
         */
        void refreshData(UserBean userBean);

//        void queryUserIsBindMobile(ThidBindIn thidBindIn);
//        void responseUserIsBindMobile(BindBean bindBean);
    }

    /**
     * 注册处理业务逻辑
     */
    public interface IRegisterPresenter extends IBasePresenter {
        /**
         * 注册
         */
        void register(ForgetIn forgetIn);

        void showSuccess();
    }

    /**
     * 忘记密码处理业务逻辑
     */
    public interface IForgetPresenter extends IBasePresenter {
        void forget(ForgetIn forgetIn);
        void showSuccess();
    }

    /**
     * 验证码处理业务逻辑
     */
    public interface ICodePresenter extends IBasePresenter {
        /**
         * 发送验证码
         */
        void sendCode(String mobile);
    }

    /**
     * 网络请求
     */
    public interface ILoginModel extends IBaseModel{
        /**
         * 登录
         */
        void login(ForgetIn forgetIn);

        /**
         * 展示用户信息
         */
        void registerOpenIM(OpenIMUserBean openIMUserBean);

        /**
         * 设置用户信息
         */
        void loginOpenIM(OpenIMUserBean openIMUserBean);

        /**
         * 修改用户信息
         * @param openIMUserBean
         */
        void upDateOpenIM(OpenIMUserBean openIMUserBean);
//        void queryUserIsBindMobile(ThidBindIn thidBindIn);
    }

    /**
     * 网络请求
     */
    public interface IRegisterModel extends IBaseModel{
        /**
         * 注册
         */
        void register(ForgetIn forgetIn);
    }

    /**
     * 忘记密码
     */
    public interface IForgetModel extends IBaseModel{
        void forget(ForgetIn forgetIn);
    }

    /**
     * 验证码
     */
    public interface ICodeModel extends IBaseModel{
        /**
         * 发送验证码
         */
        void sendCode(String mobile);
    }

    /**
     * UI方法
     */
    public interface IMessageView extends IBaseView {
        void getRefreshData(List<Message> list);
        void getLoadMoreData(List<Message> list);
        void deleteSuccess(String msg);
    }
    /**
     * 搜索地址业务逻辑
     */
    public interface IMessagePresenter extends IBasePresenter {
        void deleteMessage(Message message);
        void deleteSuccess(String msg);
        void loadMore();
        void loadData();
        boolean hasMore();
        void refreshData(MessageList messageList);
        void loadMoreData(MessageList messageList);
    }
    /**
     * 搜索地址
     */
    public interface IMessageModel extends IBaseModel{
        void deleteMessage(Message message);
        void getMessage(MessageIn messageIn, int flag);
    }

    /**
     * 安全中心UI方法
     */
    public interface ISafeCenterView extends IBaseView {
        void refreshView(BindOut bindOut);
        void responseUpdatePwd(String pwd);
        void responseBindData(String msg);
    }

    /**
     * 安全中心处理业务逻辑
     */
    public interface ISafeCenterPresenter extends IBasePresenter {
        void queryUserDetail();
        void responseUserInfo(BindOut bindOut);
        void updatePwd(UpdatePwdIn updatePwdIn);
        void responseUpdatePwd(String pwd);
        void bindData(BindIn bindIn);
        void responseBindData(String msg);
    }

    /**
     * 安全中心网络请求
     */
    public interface ISafeCenterModel extends IBaseModel {
        void queryUserDetail();
        void updatePwd(UpdatePwdIn updatePwdIn);
        void bindData(BindIn bindIn);
    }
}