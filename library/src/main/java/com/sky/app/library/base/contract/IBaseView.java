package com.sky.app.library.base.contract;

/**
 * Created by sky on 2017/2/10.
 * activity父接口
 */
public interface IBaseView {

    /**
     * 展示错误信息
     * @param error
     */
    void showError(String error);

    /**
     * 展示网络加载框
     */
    void showProgress();

    /**
     * 取消网络加载框
     */
    void hideProgress();
}