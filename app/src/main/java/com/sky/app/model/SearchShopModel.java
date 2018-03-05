package com.sky.app.model;

import android.content.Context;

import com.google.gson.Gson;
import com.sky.app.api.ApiService;
import com.sky.app.bean.CollectGoodsList;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.model.BaseModel;
import com.sky.app.library.utils.http.HttpUtils;
import com.sky.app.presenter.SearchShopPresenter;


/**
 * 搜索商品
 * Created by sky on 2017/2/18.
 */

public class SearchShopModel extends BaseModel<SearchShopPresenter>
        implements ShopContract.ISearchShopModel {

    public SearchShopModel(Context context, SearchShopPresenter searchShopPresenter) {
        super(context, searchShopPresenter);
    }

    @Override
    public void getProducts(SearchProductRequest searchProductRequest, final int flag) {
        searchProductRequest.setUser_id("");
        searchProductRequest.setState(1);
        addSubscription(HttpUtils.getInstance(context).init(Constants.Url.BASE_URL).exec(
                HttpUtils.getInstance(context).createApi(ApiService.class).searchProduct(searchProductRequest),
                new HttpUtils.IHttpCallBackListener() {
                    @Override
                    public void onSuccess(String success) {
                        switch (flag){
                            case Constants.ListStatus.LOADMORE:
                                getPresenter().loadMoreData(new Gson().fromJson(success, CollectGoodsList.class));
                                break;
                            case Constants.ListStatus.REFRESH:
                                getPresenter().refreshData(new Gson().fromJson(success, CollectGoodsList.class));
                                break;
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        getPresenter().showError(error);
                    }
                }));
    }
}