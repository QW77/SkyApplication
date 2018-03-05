package com.sky.app.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.UpdateIn;
import com.sky.app.bean.UpdateOut;
import com.sky.app.contract.UpdateContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.UpdatePresenter;

/**
 * Created by sky on 2017/2/18.
 * 选择区域
 */

public class UpdateModel extends BaseModel<UpdatePresenter> implements UpdateContract.IUpdateModel{

    public UpdateModel(Context context, UpdatePresenter updatePresenter){
        super(context, updatePresenter);
    }

    @Override
    public void getVersion(UpdateIn updateIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).getVersion(updateIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showDownloadSuccess(new Gson().fromJson(success, UpdateOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
