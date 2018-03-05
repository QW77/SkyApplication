package com.sky.shop.model;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.ProductIntroduceIn;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.presenter.CardPresenter;

/**
 * 名片
 * Created by sky on 2017/2/18.
 */

public class CardModel extends BaseModel<CardPresenter> implements SellerContract.ICardModel{

    public CardModel(Context context, CardPresenter cardPresenter){
        super(context, cardPresenter);
    }

    @Override
    public void requestCardInfo() {
        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setLogin_user_id(UserBean.getInstance().getCacheUid());
        productIntroduceIn.setLogin_user_name(UserBean.getInstance().getUserCache().getNick_name());
        productIntroduceIn.setUser_id(UserBean.getInstance().getCacheUid());
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
    public void requestAddCard(ProductIntroduceOut productIntroduceOut) {
        productIntroduceOut.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .updateShopIntroduce(UserBean.getInstance().getCacheUid(), productIntroduceOut),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseAddCard("操作成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void setStatus(String status_code) {
        String user_id = UserBean.getInstance().getUser_id();
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .setStatus(user_id, status_code),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        Toast.makeText(context,"设置成功!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(String error) {

                    }
                }
        ));

    }

    @Override
    public void postlonlat(double longitude, double latitude) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .postlonlat(UserBean.getInstance().getCacheUid(),Double.toString(longitude),Double.toString(latitude)),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {

                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }


}
