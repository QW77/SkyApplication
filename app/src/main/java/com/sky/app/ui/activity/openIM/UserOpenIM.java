package com.sky.app.ui.activity.openIM;

import com.sky.app.bean.ForgetIn;
import com.sky.app.bean.UserBean;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class UserOpenIM {
    /**
     * 个人中心
     */
    public interface IOpenIMModel extends IBaseModel {
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
         */
        void upDateOpenIM(OpenIMUserBean openIMUserBean);
    }

    /**
     * 个人中心处理业务逻辑
     */
    public interface IOpenIMPresenter extends IBasePresenter {
        void registerOpenIM(OpenIMUserBean openIMUserBean);
        void loginOpenIM(OpenIMUserBean openIMUserBean);
        void upDateOpenIM(OpenIMUserBean openIMUserBean);

        void showOpenSuccess(OpenIMUserBean openIMUserBean);
//        void showOnSuccess(String msg);
    }
    /**
     * UI方法
     */
    public interface IOpenIMView extends IBaseView {
        /**
         * 刷新界面
         */
//        void refreshOpenIM(OpenIMUsers openIMUsers);
        void showOnSuccess(OpenIMUserBean openIMUserBean);

    }
}
