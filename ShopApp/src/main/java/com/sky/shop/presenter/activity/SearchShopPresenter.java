package com.sky.shop.presenter.activity;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.CollectGoodsList;
import com.sky.shop.bean.SearchProductRequest;
import com.sky.shop.bean.UserBean;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.SearchShopModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

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
    public SearchShopPresenter(RxAppCompatActivity rxAppCompatActivity, ShopContract.ISearchShopView iSearchShopView) {
        super(rxAppCompatActivity, iSearchShopView);
        iSearchShopModel = new SearchShopModel(rxAppCompatActivity, this);
    }

    @Override
    public void destroy() {
        if (null != iSearchShopModel){
            iSearchShopModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void loadMore(SearchProductRequest searchProductRequest) {
        searchProductRequest.setPage(page + 1);
        searchProductRequest.setRows(rows);
        searchProductRequest.setUser_id(UserBean.getInstance().getCacheUid());
        iSearchShopModel.getProducts(searchProductRequest, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(SearchProductRequest searchProductRequest) {
        searchProductRequest.setPage(1);
        searchProductRequest.setRows(rows);
        searchProductRequest.setUser_id(UserBean.getInstance().getCacheUid());
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