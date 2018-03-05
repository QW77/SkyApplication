package com.sky.app.model.shop;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.StatusUPdata;
import com.sky.app.contract.SellerContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.shop.CardPresenter;

/**
 * 名片
 * Created by sky on 2017/2/18.
 */

public class CardModel extends BaseModel<CardPresenter> implements SellerContract.ICardModel{

    public CardModel(Context context, CardPresenter cardPresenter){
        super(context, cardPresenter);
    }

    @Override
    public void requestCardInfo(ProductIntroduceIn productIntroduceIn) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestDescData(productIntroduceIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseCardInfo(new Gson().fromJson(success, ProductIntroduceOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void updataStatus(String user_id) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).getStatus(user_id),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().updataStatusSuccess(new Gson().fromJson(success, StatusUPdata.class));
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                }
        ));
    }


}
