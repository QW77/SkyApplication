package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductCategoryOut;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.PropertyList;
import com.sky.shop.contract.ProductContract;
import com.sky.shop.model.AddProductModel;

/**
 * Created by sky on 2017/2/10.
 * 商品详情
 */
public class AddProductPresenter extends BasePresenter<ProductContract.IAddProView>
        implements ProductContract.IAddProPresenter {
    private ProductContract.IAddProModel iAddProModel;

    public AddProductPresenter(Context context, ProductContract.IAddProView iAddProView){
        super(context, iAddProView);
        iAddProModel = new AddProductModel(context, this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void requestAddProduct(ProductDeatilResponse productDeatilResponse) {
        iAddProModel.requestAddProduct(productDeatilResponse);
    }

    @Override
    public void responseAddProduct(String msg) {
        getView().showAddSuccess(msg);
    }

    @Override
    public void requestProductCategory(ProductCategoryIn productCategoryIn) {
        iAddProModel.requestProductCategory(productCategoryIn);
    }

    @Override
    public void responseProductCategory(ProductCategoryOut productCategoryOut) {
        getView().responseProductCategory(productCategoryOut.getList());
    }

    @Override
    public void requestEditProduct(ProductDeatilResponse productDeatilResponse) {
        iAddProModel.requestEditProduct(productDeatilResponse);
    }

    @Override
    public void responseEditProduct(String msg) {
        getView().responseEditSuccess(msg);
    }

    @Override
    public void getPropertySuccess(CategoryList categoryList){
        getView().getPropertySuccess(categoryList);
    }

    @Override
    public void requestProductProperty(){
        iAddProModel.requestProductProperty();
    }

    @Override
    public void destroy() {
        if (null != iAddProModel){
            iAddProModel.destroy();
        }
        super.destroy();
    }
}