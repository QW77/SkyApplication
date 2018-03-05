package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.MutiOrderIn;
import com.sky.app.bean.MutiShopCarIn;
import com.sky.app.bean.MutiShopCarOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MutiConfirmOrderModel;

/**
 * Created by sky on 2017/2/10.
 * 确认订单处理业务逻辑
 */
public class MutiConfirmOrderPresenter extends BasePresenter<OrderContract.IMutiConfirmOrderView>
        implements OrderContract.IMutiConfirmOrderPresenter {

    private OrderContract.IMutiConfirmOrderModel iMutiConfirmOrderModel;

    /**
     * 构造
     */
    public MutiConfirmOrderPresenter(Context context, OrderContract.IMutiConfirmOrderView iMutiConfirmOrderView) {
        super(context, iMutiConfirmOrderView);
        iMutiConfirmOrderModel = new MutiConfirmOrderModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iMutiConfirmOrderModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void confirmOrder(MutiOrderIn mutiOrderIn) {
        mutiOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMutiConfirmOrderModel.confirmOrder(mutiOrderIn);
    }

    @Override
    public void confirmOrderResult(String msg) {
        getView().confirmOrderResult(msg);
    }

    @Override
    public void queryDefaultAddrResult(AddressDetail addressDetail) {
        getView().queryDefaultAddrResult(addressDetail);
    }

    @Override
    public void queryDefaultAddr() {
        iMutiConfirmOrderModel.queryDefaultAddr();
    }

    @Override
    public void queryProductList(MutiShopCarIn mutiShopCarIn) {
        mutiShopCarIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMutiConfirmOrderModel.queryProductList(mutiShopCarIn);
    }

    @Override
    public void queryProductListResult(MutiShopCarOut mutiShopCarOut) {
        getView().queryProductListResult(mutiShopCarOut.getList());
    }
}