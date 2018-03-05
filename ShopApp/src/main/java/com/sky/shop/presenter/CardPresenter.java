package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.model.CardModel;

/**
 * Created by sky on 2017/2/10.
 * 名片
 */
public class CardPresenter extends BasePresenter<SellerContract.ICardView>
        implements SellerContract.ICardPresenter {
    private SellerContract.ICardModel iCardModel;

    /**
     * 构造
     */
    public CardPresenter(Context context, SellerContract.ICardView iCardView) {
        super(context, iCardView);
        iCardModel = new CardModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iCardModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void responseCardInfo(ProductIntroduceOut productIntroduceOut) {
        getView().responseCardInfo(productIntroduceOut);
        getView().hideProgress();
    }

    @Override
    public void requestCardInfo() {
        getView().showProgress();
        iCardModel.requestCardInfo();
    }

    @Override
    public void requestAddCard(ProductIntroduceOut productIntroduceOut) {
        getView().showProgress();
        iCardModel.requestAddCard(productIntroduceOut);
    }

    @Override
    public void responseAddCard(String msg) {
        getView().responseAddCard(msg);
        getView().hideProgress();
    }

    @Override
    public void setStatus(String status_code) {
        iCardModel.setStatus(status_code);
    }

    @Override
    public void postlonlat(double longitude, double latitude) {
        iCardModel.postlonlat(longitude, latitude);
    }


}