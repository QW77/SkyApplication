package com.sky.app.model;

import android.content.Context;

import com.sky.app.api.ApiService;
import com.sky.app.bean.CollectPubIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.CollectPresenter;

/**
 * Created by sky on 2017/2/18.
 * 收藏
 */

public class CollectionModel extends BaseModel<CollectPresenter> implements ShopContract.ICollectionModel{

    public CollectionModel(Context context, CollectPresenter collectPresenter){
        super(context, collectPresenter);
    }

    @Override
    public void requestCollect(CollectPubIn collectPubIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestCollectPublish(UserBean.getInstance().getCacheUid(), collectPubIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseCollect("收藏成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showCollectError(error);
                    }
                }
        ));
    }

    @Override
    public void cancelCollect(CollectPubIn collectPubIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .cancelCollectPublish(UserBean.getInstance().getCacheUid(), collectPubIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseCollect("成功取消收藏");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showCollectError(error);
                    }
                }
        ));
    }
}
