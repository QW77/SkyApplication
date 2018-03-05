package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.ShopCarIn;
import com.sky.app.bean.ShopCarOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MyCarModel;

/**
 * Created by sky on 2017/2/10.
 * 我的购物车处理业务逻辑
 */
public class MyCarPresenter extends BasePresenter<OrderContract.IMyCarView>
        implements OrderContract.IMyCarPresenter {

    private OrderContract.IMyCarModel iMyCarModel;

    /**
     * 构造
     */
    public MyCarPresenter(Context context, OrderContract.IMyCarView iMyCarView) {
        super(context, iMyCarView);
        iMyCarModel = new MyCarModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iMyCarModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void queryShoppingCar() {
        iMyCarModel.queryShoppingCar();
    }

    @Override
    public void queryShoppingCarResult(ShopCarOut shopCarOut) {
        getView().queryShoppingCarResult(shopCarOut.getList());
    }

    @Override
    public void del(ShopCarIn shopCarIn) {
        shopCarIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyCarModel.del(shopCarIn);
    }

    @Override
    public void delResult(String msg) {
        getView().delResult(msg);
    }

    @Override
    public void update(ShopCarIn shopCarIn) {
        shopCarIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyCarModel.update(shopCarIn);
    }

    @Override
    public void updateResult(String msg) {
        getView().updateResult(msg);
    }
}