package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.ProductIntroduceModel;

/**
 * Created by sky on 2017/2/10.
 * 店铺简介
 */
public class ProductIntroducePresenter extends BasePresenter<ShopContract.IProductIntroduceView>
        implements ShopContract.IProductIntroducePresenter {
    private ShopContract.IProductIntroduceModel iProductIntroduceModel;

    public ProductIntroducePresenter(Context context, ShopContract.IProductIntroduceView iProductIntroduceView){
        super(context, iProductIntroduceView);
        iProductIntroduceModel = new ProductIntroduceModel(context, this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void requestDescData() {
        iProductIntroduceModel.requestDescData();
    }

    @Override
    public void responseDescData(ProductIntroduceOut productIntroduceOut) {
        getView().showSuccess(productIntroduceOut);
    }

    @Override
    public void requestShopIntroduce(ProductIntroduceOut productIntroduceOut) {
        iProductIntroduceModel.requestShopIntroduce(productIntroduceOut);
    }

    @Override
    public void responseShopInfo(String msg) {
        getView().responseShopInfo(msg);
    }


    @Override
    public void postlonlat(double longitude, double latitude) {
        iProductIntroduceModel.postlonlat(longitude,latitude);
    }

    @Override
    public void destroy() {
        if (null != iProductIntroduceModel){
            iProductIntroduceModel.destroy();
        }
        super.destroy();
    }
}