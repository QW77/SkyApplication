package com.sky.app.library.base.contract;

/**
 * Created by sky on 2017/2/10.
 * 父presenter
 */
public interface IBasePresenter {

    /**
     * 展示错误信息
     * @param error
     */
    void showError(String error);

    /**
     * 销毁
     */
    void destroy();
}
