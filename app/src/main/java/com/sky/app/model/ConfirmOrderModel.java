package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.bean.SingleOrderOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.ConfirmOrderPresenter;

/**
 * Created by sky on 2017/2/18.
 * 确认订单
 */

public class ConfirmOrderModel extends BaseModel<ConfirmOrderPresenter> implements OrderContract.IConfirmOrderModel{

    public ConfirmOrderModel(Context context, ConfirmOrderPresenter confirmOrderPresenter){
        super(context, confirmOrderPresenter);
    }

    @Override
    public void confirmOrder(SingleOrderIn singleOrderIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestConfirmOrder(UserBean.getInstance().getCacheUid(), singleOrderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().confirmOrderResult(new Gson().fromJson(success, SingleOrderOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void queryDefaultAddr() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .queryDefaultAddress(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().queryDefaultAddrResult(new Gson().fromJson(success, AddressDetail.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
