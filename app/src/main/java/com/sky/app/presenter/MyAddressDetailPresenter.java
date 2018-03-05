package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AddressDetail;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MyAddressDetailModel;

/**
 * Created by sky on 2017/2/10.
 * 我的收货地址详情处理业务逻辑
 */
public class MyAddressDetailPresenter extends BasePresenter<MineContract.IMyAddressDetailView>
        implements MineContract.IMyAddressDetailPresenter {

    private MineContract.IMyAddressDetailModel iMyAddressDetailModel;

    /**
     * 构造
     */
    public MyAddressDetailPresenter(Context context, MineContract.IMyAddressDetailView iMyAddressDetailView) {
        super(context, iMyAddressDetailView);
        iMyAddressDetailModel = new MyAddressDetailModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iMyAddressDetailModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void add(AddressDetail addressDetail) {
        iMyAddressDetailModel.add(addressDetail);
    }

    @Override
    public void edit(AddressDetail addressDetail) {
        iMyAddressDetailModel.edit(addressDetail);
    }

    @Override
    public void showSuccess(String msg) {
        getView().showSuccess(msg);
    }
}