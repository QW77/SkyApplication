package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.PublishIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.PublishContentPresenter;

/**
 * Created by sky on 2017/2/18.
 * 发布内容
 */

public class PublishContentModel extends BaseModel<PublishContentPresenter> implements PublishContract.IPublishContentModel{

    public PublishContentModel(Context context, PublishContentPresenter publishContentPresenter){
        super(context, publishContentPresenter);
    }

    @Override
    public void publish(PublishIn publishIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestPublishContent(UserBean.getInstance().getCacheUid(), publishIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().response("发布成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestDetail(ProductIn productIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestSupplyDetail(productIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDetail(new Gson().fromJson(success, SupplyDetail.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void edit(PublishIn publishIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestEditMyPublish(UserBean.getInstance().getCacheUid(), publishIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().response("编辑成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
