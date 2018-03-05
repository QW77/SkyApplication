package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.Case;
import com.sky.shop.bean.CaseIn;
import com.sky.shop.bean.CaseOut;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.model.SellerCenterModel;

/**
 * Created by sky on 2017/2/10.
 * 商户个人主页处理业务逻辑
 */
public class SellerCenterPresenter extends BasePresenter<SellerContract.ISellerCenterView>
        implements SellerContract.ISellerCenterPresenter {
    private SellerContract.ISellerCenterModel iSellerCenterModel;

    /**
     * 构造
     */
    public SellerCenterPresenter(Context context, SellerContract.ISellerCenterView iSellerCenterView) {
        super(context, iSellerCenterView);
        iSellerCenterModel = new SellerCenterModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iSellerCenterModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void requestDescData() {
        getView().showProgress();
        iSellerCenterModel.requestDescData();
    }

    @Override
    public void responseDescData(ProductIntroduceOut productIntroduceOut) {
        getView().showSuccess(productIntroduceOut);
        getView().hideProgress();
    }

    @Override
    public void loadData(CaseIn caseIn) {
        getView().showProgress();
        caseIn.setPage(1);
        caseIn.setRows(100);
        iSellerCenterModel.getCaseList(caseIn);
    }

    @Override
    public void refreshData(CaseOut caseOut) {
        getView().getRefreshData(caseOut.getList());
        getView().hideProgress();
    }

    @Override
    public void requestEditShopInfo(ProductIntroduceOut productIntroduceOut) {
        getView().showProgress();
        iSellerCenterModel.requestEditShopInfo(productIntroduceOut);
    }

    @Override
    public void responseEditShopInfo(String msg) {
        getView().responseEditShopInfo(msg);
        getView().hideProgress();
    }

    @Override
    public void requestAddCase(Case caseIn) {
        getView().showProgress();
        iSellerCenterModel.requestAddCase(caseIn);
    }

    @Override
    public void responseAddCase(String msg) {
        getView().responseAddCase(msg);
        getView().hideProgress();
    }

    @Override
    public void requestEditCase(Case caseIn) {
        getView().showProgress();
        iSellerCenterModel.requestEditCase(caseIn);
    }

    @Override
    public void responseEditCase(String msg) {
        getView().responseEditCase(msg);
        getView().hideProgress();
    }

    @Override
    public void requestDelCase(Case caseIn) {
        iSellerCenterModel.requestDelCase(caseIn);
    }

    @Override
    public void responseDelCase(String msg) {
        getView().responseDelCase(msg);
    }
}