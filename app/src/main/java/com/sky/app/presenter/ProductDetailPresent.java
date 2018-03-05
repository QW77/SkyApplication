package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.CommentList;
import com.sky.app.bean.CommentRequest;
import com.sky.app.bean.CommentResponse;
import com.sky.app.bean.ProductDeatilResponse;
import com.sky.app.bean.ProductDetailRequest;
import com.sky.app.bean.SingleOrderIn;
import com.sky.app.bean.UserBean;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.ProductDetailModel;

import java.util.List;

/**
 * Created by hongbang on 2017/5/8.
 */

public class ProductDetailPresent extends BasePresenter<ShopContract.IProductDetailView>  implements  ShopContract.IProductDetailPresenter {

    ShopContract.IProductDetailModel  productDetailModel;

    public ProductDetailPresent(Context context, ShopContract.IProductDetailView view) {
        super(context, view);
        productDetailModel=new ProductDetailModel(context,this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void getProductDetail(ProductDetailRequest productDetailRequest) {
        getView().showProgress();
        productDetailRequest.setUser_id(UserBean.getInstance().getCacheUid());
        productDetailModel.getProductDetail(productDetailRequest);
    }

    @Override
    public void productDetailSuccess(ProductDeatilResponse productList) {
        getView().productDetailSuccess(productList);
        getView().hideProgress();
    }

    @Override
    public void getProductComment(CommentRequest commentRequest) {
        commentRequest.setType(2);
        commentRequest.setPage(1);
        commentRequest.setRows(1);
        productDetailModel.getProductComment(commentRequest);
    }

    @Override
    public void ProductCommentSuccess(CommentList commentResponse) {
        List<CommentResponse> commentResponses = commentResponse.getList();
        getView().ProductCommentSuccess(null == commentResponses || commentResponses.isEmpty() ? null : commentResponses.get(0));
    }

    @Override
    public void addShoppingCar(SingleOrderIn singleOrderIn) {
        singleOrderIn.setUser_id(UserBean.getInstance().getCacheUid());
        productDetailModel.addShoppingCar(singleOrderIn);
    }

    @Override
    public void showAddResult(String msg) {
        getView().showAddResult(msg);
    }
}
