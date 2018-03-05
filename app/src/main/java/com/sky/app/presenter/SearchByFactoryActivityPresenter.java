package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.CategoryList;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.SearchByFactoryModel;

/**
 * Created by sky on 2017/2/10.
 * 处理业务逻辑
 * 根据工厂来来搜索
 */

public class SearchByFactoryActivityPresenter extends BasePresenter<UserContract.ISearchByFactory>
        implements UserContract.ISearchByFactoryPresenter {

    private UserContract.ISearchByFactoryModel iSearchByFactoryModel;

    /**
     * 构造
     */
    public SearchByFactoryActivityPresenter(Context context,
                                            UserContract.ISearchByFactory mIsearch) {
        super(context, mIsearch);
        this.iSearchByFactoryModel = new SearchByFactoryModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iSearchByFactoryModel){
            iSearchByFactoryModel.destroy();
        }
        super.destroy();
    }


    @Override
    public void getData(String id) {
        iSearchByFactoryModel.getData(id);
    }

    @Override
    public void success(CategoryList categoryList) {
            getView().success(categoryList);
    }

    @Override
    public void getUserData(SearchUser searchUser) {
        getView().showProgress();
        iSearchByFactoryModel.getUserData(searchUser);
    }

    @Override
    public void userDataSuccess(UserBeanList userBeanList) {
        getView().userDataSuccess(userBeanList);
        getView().hideProgress();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        getView().showError(error);
        getView().hideProgress();
    }
}