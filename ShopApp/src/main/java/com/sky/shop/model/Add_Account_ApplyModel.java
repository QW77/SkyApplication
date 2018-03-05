package com.sky.shop.model;

import android.content.Context;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.Add_Account_ApplyBean;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.presenter.Add_Account_ApplyPresenter;

/**
 * Created by sky on 2017/2/18.
 * 登录
 */

public class Add_Account_ApplyModel extends BaseModel<Add_Account_ApplyPresenter> implements AccountContract.Add_Account_ApplyModel{

    public Add_Account_ApplyModel(Context context, Add_Account_ApplyPresenter add_Account_ApplyPresenter){
        super(context, add_Account_ApplyPresenter);
    }

    @Override
    public void getAdd_Account_ApplyModel(String userid, Add_Account_ApplyBean info) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).AddRecord(userid,info),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSuccess();
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

}
