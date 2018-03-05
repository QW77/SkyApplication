package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AlipayOut;
import com.sky.app.bean.CancelOrderIn;
import com.sky.app.bean.OrderIn;
import com.sky.app.bean.OrderOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.MyOrderModel;

import org.json.JSONObject;

/**
 * Created by sky on 2017/2/10.
 * 我的订单处理业务逻辑
 */
public class MyOrderPresenter extends BasePresenter<OrderContract.IMyOrderView>
        implements OrderContract.IMyOrderPresenter {

    private OrderContract.IMyOrderModel iMyOrderModel;

    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public MyOrderPresenter(Context context, OrderContract.IMyOrderView iMyOrderView) {
        super(context, iMyOrderView);
        iMyOrderModel = new MyOrderModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iMyOrderModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void loadMore() {
        getView().showProgress();
        OrderIn orderIn = new OrderIn();
        orderIn.setPage(page + 1);
        orderIn.setRows(rows);
        iMyOrderModel.requestMyOrder(orderIn, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData() {
        getView().showProgress();
        OrderIn orderIn = new OrderIn();
        orderIn.setPage(1);
        orderIn.setRows(rows);
        orderIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyOrderModel.requestMyOrder(orderIn, Constants.ListStatus.REFRESH);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(OrderOut orderOut) {
        this.page = orderOut.getPage();
        this.total = orderOut.getTotal();
        getView().getRefreshData(orderOut.getList());
        getView().hideProgress();
    }

    @Override
    public void loadMoreData(OrderOut orderOut) {
        this.page = orderOut.getPage();
        this.total = orderOut.getTotal();
        getView().getLoadMoreData(orderOut.getList());
        getView().hideProgress();
    }

    @Override
    public void cancel(CancelOrderIn cancelOrderIn) {
        getView().showProgress();
        cancelOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyOrderModel.cancelMyOrder(cancelOrderIn);
    }

    @Override
    public void confirm(CancelOrderIn cancelOrderIn) {
        getView().showProgress();
        cancelOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyOrderModel.confirmMyOrder(cancelOrderIn);
    }

    @Override
    public void responseCancel(String msg) {
        getView().responseCancel(msg);
        getView().hideProgress();
    }

    @Override
    public void responseConfirm(String msg) {
        getView().responseConfirm(msg);
        getView().hideProgress();
    }

    @Override
    public void del(CancelOrderIn cancelOrderIn) {
        getView().showProgress();
        cancelOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        iMyOrderModel.delMyOrder(cancelOrderIn);
    }

    @Override
    public void responseDel(String msg) {
        getView().responseDel(msg);
        getView().hideProgress();
    }

    @Override
    public void weixinPay(CancelOrderIn catchOrder) {
        getView().showProgress();
        iMyOrderModel.weixinPay(catchOrder);
    }

    @Override
    public void responseWeixinPay(JSONObject jsonObject) {
        getView().responseWeixinPay(jsonObject);
        getView().hideProgress();
    }

    @Override
    public void alipay(CancelOrderIn catchOrder) {
        getView().showProgress();
        iMyOrderModel.alipay(catchOrder);
    }

    @Override
    public void responseAlipay(AlipayOut alipayOut) {
        getView().responseAlipay(alipayOut);
        getView().hideProgress();
    }
}