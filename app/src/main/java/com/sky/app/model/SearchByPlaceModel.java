package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.AreaList;
import com.sky.app.bean.Category;
import com.sky.app.bean.CategoryList;
import com.sky.app.bean.DecorationCityList;
import com.sky.app.bean.Empty;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.bean.SearchDecorationCity;
import com.sky.app.bean.SearchUser;
import com.sky.app.bean.UserBeanList;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.SearchByPlaceActivityPresenter;


/**
 *通过搜索地址查询
 * Created by sky on 2017/2/18.
 */

public class SearchByPlaceModel extends BaseModel<SearchByPlaceActivityPresenter> implements UserContract.ISearchByPlaceModel{


    public SearchByPlaceModel(Context context, SearchByPlaceActivityPresenter presenter) {
        super(context, presenter);
    }

    @Override
    public void getDecrationCity(String id) {
        SearchDecorationCity searchDecorationCity = new SearchDecorationCity();
        searchDecorationCity.setTwo_dir_id(id);
        searchDecorationCity.setThree_dir_name("");
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchDecorationCity(searchDecorationCity),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().successDecrationCity(new Gson().fromJson(success, DecorationCityList.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void getData(String id) {
        Empty empty = new Empty();
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchPlace(empty),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().success(new Gson().fromJson(success, AreaList.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));

    }

    @Override
    public void getFirstCatogoryData() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchFirstCatogory(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().firstCatogoryDataSuccess(new Gson().fromJson(success, FirstCategoryOut.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);

                    }
                }));
    }

    @Override
    public void getSecondCatogoryData(String id) {
        Category category = new Category();
        category.setOne_dir_id(id);
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchSecondCatogory(category),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        getPresenter().secondCatogoryDataSuccess(new Gson().fromJson(success, CategoryList.class));
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
