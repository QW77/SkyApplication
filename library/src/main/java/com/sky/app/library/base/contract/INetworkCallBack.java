package com.sky.app.library.base.contract;

/**
 * Created by sky on 2017/2/15.
 * 网络监听回调
 */

public interface INetworkCallBack {

    /**
     * 没有网络
     */
    void noNetwork();

    /**
     * wifi
     */
    void wifiNetwork();

    /**
     * mobile
     */
    void mobileNetwork();
}
