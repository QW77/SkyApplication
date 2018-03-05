package com.sky.app.model;

import android.content.Context;

import com.sky.app.api.ApiService;
import com.sky.app.bean.AddressDetail;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.MyAddressDetailPresenter;

/**
 * Created by sky on 2017/2/18.
 * 我的收货地址详情
 */

public class MyAddressDetailModel extends BaseModel<MyAddressDetailPresenter> implements MineContract.IMyAddressDetailModel{

    public MyAddressDetailModel(Context context, MyAddressDetailPresenter myAddressDetailPresenter){
        super(context, myAddressDetailPresenter);
    }

    @Override
    public void add(AddressDetail addressDetail) {
        addressDetail.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAddMyAddress(UserBean.getInstance().getCacheUid(), addressDetail),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSuccess("添加成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void edit(AddressDetail addressDetail) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestUpdateMyAddress(UserBean.getInstance().getCacheUid(), addressDetail),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSuccess("修改成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
