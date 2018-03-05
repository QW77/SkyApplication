package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.ProductIntroduceModel;

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
        getView().hideProgress();
    }

    @Override
    public void requestDescData(ProductIntroduceIn productIntroduceIn) {
        getView().showProgress();
        productIntroduceIn.setLogin_user_id(UserBean.getInstance().getCacheUid());
        productIntroduceIn.setLogin_user_name(UserBean.getInstance().getUserCache().getNick_name());
        iProductIntroduceModel.requestDescData(productIntroduceIn);
    }

    @Override
    public void responseDescData(ProductIntroduceOut productIntroduceOut) {
        getView().showSuccess(productIntroduceOut);
        getView().hideProgress();
    }
}