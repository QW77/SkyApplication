package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.BaseUser;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SupplyList;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.MyPublishPresenter;

/**
 * Created by sky on 2017/2/18.
 * 我的发布
 */

public class MyPublishModel extends BaseModel<MyPublishPresenter> implements PublishContract.IMyPublishModel{

    public MyPublishModel(Context context, MyPublishPresenter myPublishPresenter){
        super(context, myPublishPresenter);
    }

    @Override
    public void requestList(final int flag) {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestMyPublish(UserBean.getInstance().getCacheUid(), baseUser),
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

    @Override
    public void del(ProductIn productIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestDelMyPublish(UserBean.getInstance().getCacheUid(), productIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showDelSuccess("删除成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
