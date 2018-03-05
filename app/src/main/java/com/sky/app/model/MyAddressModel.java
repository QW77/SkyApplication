package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AddressIn;
import com.sky.app.bean.AddressOut;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.MyAddressPresenter;

/**
 * Created by sky on 2017/2/18.
 * 我的收货地址
 */

public class MyAddressModel extends BaseModel<MyAddressPresenter> implements MineContract.IMyAddressModel{

    public MyAddressModel(Context context, MyAddressPresenter myAddressPresenter){
        super(context, myAddressPresenter);
    }

    @Override
    public void requestMyAddress() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        . requestDelMyAddress(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().reFreshData(new Gson().fromJson(success, AddressOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void setMyAddress(AddressIn addressIn) {
        addressIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestSetMyAddress(UserBean.getInstance().getCacheUid(), addressIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSetAddressSuccess("成功设置默认地址");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void delMyAddress(AddressIn addressIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestDelMyAddress(UserBean.getInstance().getCacheUid(), addressIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showDelAddressSuccess("成功删除收货地址");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
