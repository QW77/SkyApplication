package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.AreaList;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.SearchByPlaceModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 根据地址来搜索
 */

public class SearchByPlaceActivityPresenter extends BasePresenter<UserContract.ISearchByPlace>
        implements UserContract.ISearchByPlacePresenter {

    private UserContract.ISearchByPlaceModel iCenterModel;

    /**
     * 构造
     */
    public SearchByPlaceActivityPresenter(Context context,
                                          UserContract.ISearchByPlace mIsearch) {
        super(context, mIsearch);
        this.iCenterModel = new SearchByPlaceModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iCenterModel){
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
    public void successDecrationCity(DecorationCityList categoryList) {
            getView().successDecrationCity(categoryList);
    }

    /**
     * 请求
     */
    @Override
    public void getData(String id) {
        iCenterModel.getData(id);
    }

    /**
     * 请求成功回调
     *
     */
    @Override
    public void success(AreaList areaList) {
        getView().success(areaList);
    }



    @Override
    public void getFirstCatogoryData() {
        iCenterModel.getFirstCatogoryData();
    }

    @Override
    public void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut) {
        List<FirstCategoryDetail> categoryList = new ArrayList<>();
        for (FirstCategoryDetail f : firstCategoryOut.getList()){
            categoryList.add(f);
        }
        getView().firstCatogoryDataSuccess(categoryList);
    }

    @Override
    public void getSecondCatogoryData(String  ID) {
        iCenterModel.getSecondCatogoryData(ID);
    }

    @Override
    public void secondCatogoryDataSuccess(CategoryList categoryList) {
        getView().secondCatogoryDataSuccess(categoryList);
    }

    @Override
    public void getUserData(SearchUser searchUser) {
        getView().showProgress();
        iCenterModel.getUserData(searchUser);
    }

    @Override
    public void userDataSuccess(UserBeanList userBeanList) {
        getView().userDataSuccess(userBeanList);
        getView().hideProgress();
    }
}