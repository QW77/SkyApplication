package com.sky.app.model;

import android.content.Context;

import com.sky.app.api.ApiService;
import com.sky.app.bean.PubCommentIn;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.PubCommentPresenter;


/**
 *发布评价
 * Created by sky on 2017/2/18.
 */

public class PubCommentModel extends BaseModel<PubCommentPresenter> implements ShopContract.IPubCommentModel{

    public PubCommentModel(Context context, PubCommentPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void publish(PubCommentIn pubCommentIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).publishComment(pubCommentIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().showSuccess("发布成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
