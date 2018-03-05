package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductCategoryOut;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.CategoryModel;

/**
 * Created by sky on 2017/2/10.
 * 目录
 */
public class CategoryPresenter extends BasePresenter<ShopContract.ICategoryView>
        implements ShopContract.ICategoryPresenter {
    private ShopContract.ICategoryModel iCategoryModel;

    public CategoryPresenter(Context context, ShopContract.ICategoryView iCategoryView){
        super(context, iCategoryView);
        iCategoryModel = new CategoryModel(context, this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void requestProductCategory(ProductCategoryIn productCategoryIn) {
        iCategoryModel.requestProductCategory(productCategoryIn);
    }

    @Override
    public void responseProductCategory(ProductCategoryOut productCategoryOut) {
        getView().responseProductCategory(productCategoryOut.getList());
    }

    @Override
    public void responseAddFirstCategory(String msg) {
        getView().responseAddFirstCategory(msg);
    }

    @Override
    public void requestAddFirstCategory(ProductCategory productCategory) {
        iCategoryModel.requestAddFirstCategory(productCategory);
    }

    @Override
    public void responseDelSuccess(String msg) {
        getView().responseDelSuccess(msg);
    }

    @Override
    public void requestDelCategory(ProductCategory productCategory) {
        iCategoryModel.requestDelCategory(productCategory);
    }

    @Override
    public void responseUpdateSuccess(String msg) {
        getView().responseUpdateSuccess(msg);
    }

    @Override
    public void requestUpdateCategory(ProductCategory productCategory) {
        iCategoryModel.requestUpdateCategory(productCategory);
    }

    @Override
    public void destroy() {
        if (null != iCategoryModel){
            iCategoryModel.destroy();
        }
        super.destroy();
    }
}