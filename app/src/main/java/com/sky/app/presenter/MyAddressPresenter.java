package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AddressIn;
import com.sky.app.bean.AddressOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MyAddressModel;

/**
 * Created by sky on 2017/2/10.
 * 我的收货地址处理业务逻辑
 */
public class MyAddressPresenter extends BasePresenter<MineContract.IMyAddressView>
        implements MineContract.IMyAddressPresenter {

    private MineContract.IMyAddressModel iMyAddressModel;

    /**
     * 构造
     */
    public MyAddressPresenter(Context context, MineContract.IMyAddressView iMyAddressView) {
        super(context, iMyAddressView);
        iMyAddressModel = new MyAddressModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iMyAddressModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadData() {
        iMyAddressModel.requestMyAddress();
    }

    @Override
    public void reFreshData(AddressOut addressOut) {
        getView().reFreshData(addressOut.getList());
    }

    @Override
    public void showSetAddressSuccess(String msg) {
        getView().showSetAddressSuccess(msg);
    }

    @Override
    public void showDelAddressSuccess(String msg) {
        getView().showDelAddressSuccess(msg);
    }

    @Override
    public void del(AddressIn addressIn) {
        addressIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyAddressModel.delMyAddress(addressIn);
    }

    @Override
    public void setDefaultAddress(AddressIn addressIn) {
        addressIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyAddressModel.setMyAddress(addressIn);
    }
}