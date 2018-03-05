package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.bean.SingleOrderOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.ConfirmOrderModel;

/**
 * Created by sky on 2017/2/10.
 * 确认订单处理业务逻辑
 */
public class ConfirmOrderPresenter extends BasePresenter<OrderContract.IConfirmOrderView>
        implements OrderContract.IConfirmOrderPresenter {

    private OrderContract.IConfirmOrderModel iConfirmOrderModel;

    /**
     * 构造
     */
    public ConfirmOrderPresenter(Context context, OrderContract.IConfirmOrderView iConfirmOrderView) {
        super(context, iConfirmOrderView);
        iConfirmOrderModel = new ConfirmOrderModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iConfirmOrderModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void confirmOrder(SingleOrderIn singleOrderIn) {
        singleOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        iConfirmOrderModel.confirmOrder(singleOrderIn);
    }

    @Override
    public void confirmOrderResult(SingleOrderOut singleOrderOut) {
        getView().confirmOrderResult(singleOrderOut);
    }

    @Override
    public void queryDefaultAddrResult(AddressDetail addressDetail) {
        getView().queryDefaultAddrResult(addressDetail);
    }

    @Override
    public void queryDefaultAddr() {
        iConfirmOrderModel.queryDefaultAddr();
    }
}