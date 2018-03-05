package com.sky.shop.contract;

import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.shop.bean.QQWeixinIn;
import com.sky.shop.bean.UserBean;

/**
 * Created by sky on 2017/2/10.
 * qq／微信契约类
 */

public class QQContract {

    /**
     * 自动登录更新UI方法
     */
    public interface IQQView extends IBaseView {
        /**
         * 展示用户
         */
        void showUserInfo();
    }

    /**
     * 自动登录处理业务逻辑
     */
    public interface IQQPresenter extends IBasePresenter {
        /**
         * qq登录
         * @param qqWeixinIn
         */
        void qqLogin(QQWeixinIn qqWeixinIn);

        /**
         * 微信登录
         * @param qqWeixinIn
         */
        void weixinLogin(QQWeixinIn qqWeixinIn);

        /**
         * 刷新用户界面
         * @param userBean
         */
        void refreshData(UserBean userBean);
    }

    /**
     * 自动登录网络请求
     */
    public interface IQQModel extends IBaseModel{
        /**
         * qq登录
         */
        void qqLogin(QQWeixinIn qqWeixinIn);

        /**
         * 微信登录
         */
        void weixinLogin(QQWeixinIn qqWeixinIn);
    }

}