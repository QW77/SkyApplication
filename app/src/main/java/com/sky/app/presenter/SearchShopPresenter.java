package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.CollectGoodsList;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.SearchShopModel;

/**
 * Created by sky on 2017/2/10.
 * 搜索商品处理业务逻辑
 */

public class SearchShopPresenter extends BasePresenter<ShopContract.ISearchShopView>
        implements ShopContract.ISearchShopPresenter {

    private int page = 1;
    private float total = 1;
    private int rows = 20;

    private ShopContract.ISearchShopModel iSearchShopModel;

    /**
     * 构造
     */
    public SearchShopPresenter(Context context, ShopContract.ISearchShopView iSearchShopView) {
        super(context, iSearchShopView);
        iSearchShopModel = new SearchShopModel(context, this);
    }

    @Override
    public void destroy() {
        super.destroy();
        iSearchShopModel.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadMore(SearchProductRequest searchProductRequest) {
        searchProductRequest.setPage(page + 1);
        searchProductRequest.setRows(rows);
        iSearchShopModel.getProducts(searchProductRequest, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(SearchProductRequest searchProductRequest) {
        searchProductRequest.setPage(1);
        searchProductRequest.setRows(rows);
        iSearchShopModel.getProducts(searchProductRequest, Constants.ListStatus.REFRESH);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(CollectGoodsList collectGoodsList) {
        this.page = collectGoodsList.getPage();
        this.total = collectGoodsList.getTotal();
        getView().getRefreshData(collectGoodsList.getList());
    }

    @Override
    public void loadMoreData(CollectGoodsList collectGoodsList) {
        this.page = collectGoodsList.getPage();
        this.total = collectGoodsList.getTotal();
        getView().getLoadMoreData(collectGoodsList.getList());
    }
}