package com.sky.app.library.base.ui;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sky.app.library.base.contract.IBasePresenter;
import com.sky.app.library.base.contract.IBaseView;
import com.sky.app.library.base.contract.INetworkCallBack;
import com.sky.app.library.utils.BaseAppManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * activity父类
 */
public abstract class BaseViewActivity<T extends IBasePresenter> extends AppCompatActivity
        implements IBaseView, INetworkCallBack, View.OnClickListener {

    protected Context context = null;
    private T presenter;
    private Unbinder unbinder;
    private MyReceiver myReceiver = new MyReceiver(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        BaseAppManager.getInstance().addActivity(this);

        //设置业务逻辑
        setPresenter(presenter());

        //监听网络
        registerReceiver();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        onDestoryActivity();

        if (null != presenter){
            getPresenter().destroy();
        }
        if (null != myReceiver){
            unregisterReceiver(myReceiver);
        }

        if (null != unbinder){
            unbinder.unbind();
        }
        super.onDestroy();
        BaseAppManager.getInstance().finishActivity(this);
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    public T getPresenter() {
        return presenter;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        //绑定视图
        unbinder = ButterKnife.bind(this);

        //初始化基本操作
        init();

        //初始化视图
        initViewsAndEvents();
    }

    /**
     * 获取实例化业务逻辑
     */
    protected abstract T presenter();

    /**
     * 初始化界面
     */
    protected abstract void initViewsAndEvents();

    /**
     * 初始化基本操作
     */
    protected abstract void init();

    /**
     * 监听网络变化
     */
    private void registerReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void noNetwork() {
    }

    @Override
    public void wifiNetwork() {
    }

    @Override
    public void mobileNetwork() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showError(String error) {

    }

    /**
     * 销毁activity
     */
    protected abstract void onDestoryActivity();

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }
}