package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AlipayOut;
import com.sky.app.bean.CancelOrderIn;
import com.sky.app.bean.OrderIn;
import com.sky.app.bean.OrderOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.MyOrderPresenter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sky on 2017/2/18.
 * 我的订单
 */

public class MyOrderModel extends BaseModel<MyOrderPresenter> implements OrderContract.IMyOrderModel{

    public MyOrderModel(Context context, MyOrderPresenter myOrderPresenter){
        super(context, myOrderPresenter);
    }

    @Override
    public void requestMyOrder(OrderIn orderIn, final int flag) {
        orderIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestMyOrder(UserBean.getInstance().getCacheUid(), orderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag){
                            case Constants.ListStatus.LOADMORE:
                                getPresenter().loadMoreData(new Gson().fromJson(success, OrderOut.class));
                                break;
                            case Constants.ListStatus.REFRESH:
                                getPresenter().refreshData(new Gson().fromJson(success, OrderOut.class));
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void cancelMyOrder(CancelOrderIn cancelOrderIn) {
        cancelOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCancelMyOrder(UserBean.getInstance().getCacheUid(), cancelOrderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseCancel("取消订单成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void confirmMyOrder(CancelOrderIn cancelOrderIn) {
        cancelOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestConfirmMyOrder(UserBean.getInstance().getCacheUid(), cancelOrderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseConfirm("成功确认收货");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void delMyOrder(CancelOrderIn cancelOrderIn) {
        cancelOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestDelMyOrder(UserBean.getInstance().getCacheUid(), cancelOrderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDel("删除成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void weixinPay(CancelOrderIn catchOrder) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .weixinPay(UserBean.getInstance().getCacheUid(), catchOrder),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        L.msg("微信返回:" + success);
                        try {
                            getPresenter().responseWeixinPay(new JSONObject(success));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void alipay(CancelOrderIn catchOrder) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .alipay(UserBean.getInstance().getCacheUid(), catchOrder),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseAlipay(new Gson().fromJson(success, AlipayOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
