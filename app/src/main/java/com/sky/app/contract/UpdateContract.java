package com.sky.app.contract;

import com.sky.app.bean.UpdateIn;
import com.sky.app.bean.UpdateOut;
import com.sky.app.library.base.contract.IBaseModel;
import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;

/**
 * 升级
 */

public class UpdateContract {

    /**
     * 升级更新UI方法
     */
    public interface IUpdateView extends IBaseView {
        void showDownloadSuccess(UpdateOut updateOut);
    }

    /**
     * 升级处理业务逻辑
     */
    public interface IUpdatePresenter extends IBasePresenter {
        void showDownloadSuccess(UpdateOut updateOut);
        void getVersion(UpdateIn updateIn);
    }

    /**
     * 升级网络请求
     */
    public interface IUpdateModel extends IBaseModel {
        void getVersion(UpdateIn updateIn);
    }
}
