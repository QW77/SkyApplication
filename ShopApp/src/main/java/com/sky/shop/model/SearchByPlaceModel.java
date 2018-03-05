package com.sky.shop.model;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.shop.api.ApiService;
import com.sky.shop.bean.AreaList;
import com.sky.shop.bean.Category;
import com.sky.shop.bean.CategoryList;
import com.sky.shop.bean.DecorationCityList;
import com.sky.shop.bean.Empty;
import com.sky.shop.bean.FirstCategoryOut;
import com.sky.shop.bean.SearchDecorationCity;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.SearchByPlaceActivityPresenter;

/**
 *通过搜索地址查询
 * Created by sky on 2017/2/18.
 */

public class SearchByPlaceModel extends BaseModel<SearchByPlaceActivityPresenter> implements ShopContract.ISearchByPlaceModel{


    public SearchByPlaceModel(Context context, SearchByPlaceActivityPresenter presenter) {
        super(context, presenter);
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
                })
        );

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
//                        Log.e("123456_getDecrationCity",success);
                        getPresenter().successDecrationCity(new Gson().fromJson(success, DecorationCityList.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void getData() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchPlace(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
//                        Log.e("123456_getData",success);
                        getPresenter().success(new Gson().fromJson(success, AreaList.class));
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
//                        Log.e("123456_getSecond",success);
                        getPresenter().secondCatogoryDataSuccess(new Gson().fromJson(success, CategoryList.class));
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }

    @Override
    public void requestCompanyType() {
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class)
                        .searchFactory(new Empty()),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
//                        Log.e("123456_requestCompany",success);
                        getPresenter().responseCompanyType(new Gson().fromJson(success, CategoryList.class));
                    }
                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}
