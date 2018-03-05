package com.sky.shop.presenter.activity;

import android.content.Context;
import android.util.Log;

import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.AreaList;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.DecorationCityList;
import com.sky.shop.bean.FirstCategoryOut;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.SearchByPlaceModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 根据地址来搜索
 */

public class SearchByPlaceActivityPresenter extends BasePresenter<ShopContract.ISearchByPlace>
        implements ShopContract.ISearchByPlacePresenter {

    private ShopContract.ISearchByPlaceModel iCenterModel;

    /**
     * 构造
     */
    public SearchByPlaceActivityPresenter(Context context,
                                          ShopContract.ISearchByPlace mIsearch) {
        super(context, mIsearch);
        this.iCenterModel = new SearchByPlaceModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iCenterModel) {
            iCenterModel.destroy();
        }
        super.destroy();
    }


    @Override
    public void showError(String error) {
        getView().showError(error);
        getView().hideProgress();
    }

    @Override
    public void getDecrationCity(String id) {
        iCenterModel.getDecrationCity(id);
    }

    @Override
    public void successDecrationCity(DecorationCityList decorationCityList) {
        getView().successDecrationCity(decorationCityList);
    }

    /**
     * 请求
     */
    @Override
    public void getData() {
        iCenterModel.getData();
    }

    /**
     * 请求成功回调
     */
    @Override
    public void success(AreaList areaList) {
        getView().success(areaList);
    }

    @Override
    public void getSecondCatogoryData(String ID) {
        getView().showProgress();
        iCenterModel.getSecondCatogoryData(ID);
    }

    @Override
    public void secondCatogoryDataSuccess(CategoryList categoryList) {
        getView().secondCatogoryDataSuccess(categoryList);
        getView().hideProgress();
    }

    @Override
    public void getFirstCatogoryData() {
        getView().showProgress();
        iCenterModel.getFirstCatogoryData();
    }

    @Override
    public void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut) {
        getView().firstCatogoryDataSuccess(firstCategoryOut);
        getView().hideProgress();
    }

    @Override
    public void requestCompanyType() {
        iCenterModel.requestCompanyType();
    }

    @Override
    public void responseCompanyType(CategoryList categoryList) {
        getView().responseCompanyType(categoryList);
    }
}