package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AreaIn;
import com.sky.app.bean.AreaOut;
import com.sky.app.contract.MineContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.AreaPresenter;

/**
 * Created by sky on 2017/2/18.
 * 选择区域
 */

public class AreaModel extends BaseModel<AreaPresenter> implements MineContract.IAreaModel{

    public AreaModel(Context context, AreaPresenter areaPresenter){
        super(context, areaPresenter);
    }

    @Override
    public void get(AreaIn areaIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestArea(areaIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSuccess(new Gson().fromJson(success, AreaOut.class));                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
