package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.SearchByFactoryActivityPresenter;


/**
 *通过搜索工厂查询
 * Created by sky on 2017/2/18.
 */

public class SearchByFactoryModel extends BaseModel<SearchByFactoryActivityPresenter> implements UserContract.ISearchByFactoryModel{


    public SearchByFactoryModel(Context context, SearchByFactoryActivityPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void getData(String id) {
        Category category = new Category();
        category.setTwo_dir_id(id);
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchFactory(category),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        CategoryList categoryList = new Gson().fromJson(success, CategoryList.class);
                        getPresenter().success(categoryList);
                    }
                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));

    }

    @Override
    public void getUserData(SearchUser searchUser) {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchUser(searchUser),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().userDataSuccess(new Gson().fromJson(success, UserBeanList.class));
                    }
                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

}
