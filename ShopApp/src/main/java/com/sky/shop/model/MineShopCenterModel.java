package com.sky.shop.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.BaseUser;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.MineShopCenterPresenter;

/**
 * 我的卖家中心商铺
 * Created by sky on 2017/2/18.
 */

public class MineShopCenterModel extends BaseModel<MineShopCenterPresenter> implements ShopContract.IMineShopCenterModel{

    public MineShopCenterModel(Context context, MineShopCenterPresenter mineShopCenterPresenter){
        super(context, mineShopCenterPresenter);
    }

    @Override
    public void queryUserInfo() {
        BaseUser baseUser = new BaseUser();
        baseUser.setUser_id(UserBean.getInstance().getCacheUid());
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .queryUserInfo(UserBean.getInstance().getCacheUid(), baseUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().responseUserInfo(new Gson().fromJson(success, UserBean.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }
        ));
    }
}
