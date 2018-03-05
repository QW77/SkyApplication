package com.sky.shop.presenter;

import android.content.Context;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.AreaList;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.DecorationCityList;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.bean.SearchDecorationCity;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.ShopDataModel;

/**
 * Created by sky on 2017/2/10.
 * 商铺资料
 */
public class ShopDataPresenter extends BasePresenter<ShopContract.IShopDataView>
        implements ShopContract.IShopDataPresenter {
    private ShopContract.IShopDataModel iShopDataModel;

    public ShopDataPresenter(Context context, ShopContract.IShopDataView iShopDataView){
        super(context, iShopDataView);
        iShopDataModel = new ShopDataModel(context, this);
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void queryUserInfo() {
        getView().showProgress();
        iShopDataModel.queryUserInfo();
    }

    @Override
    public void responseUserInfo(UserBean userBean) {
        getView().responseUserInfo(userBean);
        getView().hideProgress();
    }

    @Override
    public void updateShopInfo(ProductIntroduceOut productIntroduceOut) {
        iShopDataModel.updateShopInfo(productIntroduceOut);
    }

    @Override
    public void responseUpdateShopInfo(String msg) {
        getView().responseUpdateShopInfo(msg);
    }

    @Override
    public void responseAreaList(AreaList areaList) {
        getView().responseAreaList(areaList);
    }

    @Override
    public void requestAreaList() {
        iShopDataModel.requestAreaList();
    }

    @Override
    public void requestDecorateCity(SearchDecorationCity searchDecorationCity) {
        iShopDataModel.requestDecorateCity(searchDecorationCity);
    }

    @Override
    public void responseDecorateCity(DecorationCityList decorationCityList) {
        getView().responseDecorateCity(decorationCityList);
    }

    @Override
    public void responseCompanyType(CategoryList categoryList) {
        getView().responseCompanyType(categoryList);
    }

    @Override
    public void requestCompanyType() {
        iShopDataModel.requestCompanyType();
    }

    @Override
    public void destroy() {
        if (null != iShopDataModel){
            iShopDataModel.destroy();
        }
        super.destroy();
    }
}