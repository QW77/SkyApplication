package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.contract.SellerContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.SellerFristCategoryModel;

/**
 * Created by hongbang on 2017/5/11.
 */

public class SellerFristCategoryPresenter extends BasePresenter<SellerContract.ISellerFirstCategory> implements SellerContract.ISellerFirstCategoryPresenter {


    private SellerContract.ISellerFirstCategoryModel iSellerFirstCategoryModel=null;
    public SellerFristCategoryPresenter(Context context, SellerContract.ISellerFirstCategory view) {
        super(context, view);
        iSellerFirstCategoryModel=new SellerFristCategoryModel(context,this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void getFirstCatogoryData() {
        iSellerFirstCategoryModel.getFirstCatogoryData();
    }

    @Override
    public void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut) {
        getView().firstCatogoryDataSuccess(firstCategoryOut);
    }

}
