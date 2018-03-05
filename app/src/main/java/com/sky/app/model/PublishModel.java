package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.SupplyFilter;
import com.sky.app.bean.SupplyList;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.PublishPresenter;

/**
 * Created by sky on 2017/2/18.
 * 发布
 */

public class PublishModel extends BaseModel<PublishPresenter> implements PublishContract.IPublishModel{

    public PublishModel(Context context, PublishPresenter publishPresenter){
        super(context, publishPresenter);
    }

    @Override
    public void requestPublishList(SupplyFilter supplyFilter, final int flag) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestSupplyList(supplyFilter),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag){
                            case Constants.ListStatus.LOADMORE:
                                getPresenter().loadMoreData(new Gson().fromJson(success, SupplyList.class));
                                break;
                            case Constants.ListStatus.REFRESH:
                                getPresenter().refreshData(new Gson().fromJson(success, SupplyList.class));
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
}
