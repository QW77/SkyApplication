package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ProductDeatilResponse;
import com.sky.shop.bean.ProductDetailRequest;
import com.sky.shop.contract.ProductContract;
import com.sky.shop.model.ProductDetailModel;

/**
 * Created by sky on 2017/2/10.
 * 商品详情
 */
public class ProductDetailPresenter extends BasePresenter<ProductContract.IProDetailView>
        implements ProductContract.IProDetailPresenter {
    private ProductContract.IProDetailModel iProDetailModel;

    public ProductDetailPresenter(Context context, ProductContract.IProDetailView iProDetailView){
        super(context, iProDetailView);
        iProDetailModel = new ProductDetailModel(context, this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void productDetailSuccess(ProductDeatilResponse productList) {
        getView().productDetailSuccess(productList);
        getView().hideProgress();
    }

    @Override
    public void getProductDetail(ProductDetailRequest productDetailRequest) {
        getView().showProgress();
        iProDetailModel.getProductDetail(productDetailRequest);
    }

    @Override
    public void destroy() {
        if (null != iProDetailModel){
            iProDetailModel.destroy();
        }
        super.destroy();
    }
}