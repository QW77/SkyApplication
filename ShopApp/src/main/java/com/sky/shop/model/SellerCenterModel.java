package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.Case;
import com.sky.shop.bean.CaseIn;
import com.sky.shop.bean.CaseOut;
import com.sky.shop.bean.ProductIntroduceIn;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.presenter.SellerCenterPresenter;

/**
 * 商户个人主页
 * Created by sky on 2017/2/18.
 */

public class SellerCenterModel extends BaseModel<SellerCenterPresenter> implements SellerContract.ISellerCenterModel{

    public SellerCenterModel(Context context, SellerCenterPresenter sellerCenterPresenter){
        super(context, sellerCenterPresenter);
    }

    @Override
    public void requestDescData() {
        ProductIntroduceIn productIntroduceIn = new ProductIntroduceIn();
        productIntroduceIn.setUser_id(UserBean.getInstance().getCacheUid());
        productIntroduceIn.setLogin_user_name(UserBean.getInstance().getUserCache().getNick_name());
        productIntroduceIn.setLogin_user_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestDescData(productIntroduceIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDescData(new Gson().fromJson(success, ProductIntroduceOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void getCaseList(CaseIn caseIn) {
        caseIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).requestUserCaseList(caseIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().refreshData(new Gson().fromJson(success, CaseOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestEditShopInfo(ProductIntroduceOut productIntroduceOut) {
        productIntroduceOut.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .updateShopIntroduce(UserBean.getInstance().getCacheUid(), productIntroduceOut),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseEditShopInfo("操作成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestAddCase(Case caseIn) {
        caseIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestAddCase(UserBean.getInstance().getCacheUid(), caseIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseAddCase("操作成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestEditCase(Case caseIn) {
        caseIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestEditCase(UserBean.getInstance().getCacheUid(), caseIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseEditCase("操作成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }

    @Override
    public void requestDelCase(Case caseIn) {
        caseIn.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .requestDelCase(UserBean.getInstance().getCacheUid(), caseIn),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseDelCase("操作成功");
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
