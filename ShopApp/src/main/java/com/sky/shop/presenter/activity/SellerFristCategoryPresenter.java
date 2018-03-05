package com.sky.shop.presenter.activity;

import android.content.Context;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.FirstCategoryOut;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.SellerFristCategoryModel;

/**
 * Created by hongbang on 2017/5/11.
 */

public class SellerFristCategoryPresenter extends BasePresenter<ShopContract.ISellerFirstCategory>
        implements ShopContract.ISellerFirstCategoryPresenter {


    private ShopContract.ISellerFirstCategoryModel iSellerFirstCategoryModel=null;
    public SellerFristCategoryPresenter(Context context, ShopContract.ISellerFirstCategory view) {
        super(context, view);
        iSellerFirstCategoryModel = new SellerFristCategoryModel(context,this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void getFirstCatogoryData() {
        getView().showProgress();
        iSellerFirstCategoryModel.getFirstCatogoryData();
    }

    @Override
    public void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut) {
        getView().firstCatogoryDataSuccess(firstCategoryOut);
        getView().hideProgress();
    }

    @Override
    public void destroy() {
        if (null != iSellerFirstCategoryModel){
            iSellerFirstCategoryModel.destroy();
        }
        super.destroy();
    }
}
