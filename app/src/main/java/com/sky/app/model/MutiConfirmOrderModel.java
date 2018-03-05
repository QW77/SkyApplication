package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.MutiOrderIn;
import com.sky.app.bean.MutiShopCarIn;
import com.sky.app.bean.MutiShopCarOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.OrderContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.MutiConfirmOrderPresenter;

/**
 * Created by sky on 2017/2/18.
 * 确认订单
 */

public class MutiConfirmOrderModel extends BaseModel<MutiConfirmOrderPresenter> implements OrderContract.IMutiConfirmOrderModel{

    public MutiConfirmOrderModel(Context context, MutiConfirmOrderPresenter mutiConfirmOrderPresenter){
        super(context, mutiConfirmOrderPresenter);
    }

    @Override
    public void confirmOrder(MutiOrderIn mutiOrderIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestMutiConfirmOrder(UserBean.getInstance().getCacheUid(), mutiOrderIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().confirmOrderResult("下单成功");
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

    @Override
    public void queryProductList(MutiShopCarIn mutiShopCarIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestQueryShoppingCarList(UserBean.getInstance().getCacheUid(), mutiShopCarIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().queryProductListResult(new Gson().fromJson(success, MutiShopCarOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
