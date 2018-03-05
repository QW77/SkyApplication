package com.sky.shop.presenter.activity;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.MineShopCenterModel;

/**
 * Created by sky on 2017/2/10.
 * 我的卖家中心商铺
 */
public class MineShopCenterPresenter extends BasePresenter<ShopContract.IMineShopCenterView>
        implements ShopContract.IMineShopCenterPresenter {

    private ShopContract.IMineShopCenterModel iMineShopCenterModel;

    /**
     * 构造
     */
    public MineShopCenterPresenter(Context context, ShopContract.IMineShopCenterView iMineShopCenterView) {
        super(context, iMineShopCenterView);
        iMineShopCenterModel = new MineShopCenterModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iMineShopCenterModel){
            iMineShopCenterModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void queryUserInfo() {
        iMineShopCenterModel.queryUserInfo();
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        getView().responseUserInfo(userBean);
    }
}