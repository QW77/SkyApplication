package com.sky.app.library.base.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.contract.INetworkCallBack;
import com.sky.app.library.utils.AppUtils;

/**
 * Created by sky on 2017/2/15.
 * 网络监听
 */

public class MyReceiver extends BroadcastReceiver{
    private INetworkCallBack iNetworkCallBack;

    public MyReceiver(){

    }

    public MyReceiver(INetworkCallBack iNetworkCallBack){
        this.iNetworkCallBack = iNetworkCallBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //网络变化
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            switch (AppUtils.checkNetwork(context)){
                //没有网络
                case Constants.Network.NO_NETWORK:
                    if (null != this.iNetworkCallBack){
                        this.iNetworkCallBack.noNetwork();
                    }
                    break;
                //wifi
                case Constants.Network.NETWORK_WIFI:
                    if (null != this.iNetworkCallBack){
                        this.iNetworkCallBack.wifiNetwork();
                    }
                    break;
                //数据网络
                default:
                    if (null != this.iNetworkCallBack){
                        this.iNetworkCallBack.mobileNetwork();
                    }
                    break;
            }
        }
    }
}
