package com.sky.shop.presenter.activity;

import android.content.Context;

import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductCategoryOut;
import com.sky.shop.bean.ProductIn;
import com.sky.shop.bean.ProductList;
import com.sky.shop.bean.ProductUpAndDownIn;
import com.sky.shop.bean.SearchProductRequest;
import com.sky.shop.bean.ShopProductIn;
import com.sky.shop.bean.ShopProductOut;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.model.ShopCenterModel;

/**
 * Created by sky on 2017/2/10.
 * 商铺处理业务逻辑
 */
public class ShopCenterPresenter extends BasePresenter<ShopContract.IShopCenterView>
        implements ShopContract.IShopCenterPresenter {
    private ShopContract.IShopCenterModel iShopCenterModel;

    private int page = 1;
    private float total = 1;
    private int rows = 20;

    /**
     * 构造
     */
    public ShopCenterPresenter(Context context, ShopContract.IShopCenterView iShopCenterView) {
        super(context, iShopCenterView);
        iShopCenterModel = new ShopCenterModel(context, this);
    }

    @Override
    public void destroy() {
        if (null != iShopCenterModel){
            iShopCenterModel.destroy();
        }
        super.destroy();
    }

    @Override
    public void showError(String error) {
        getView().showError(error);
    }

    @Override
    public void requestHomeData(ShopProductIn shopProductIn) {
        iShopCenterModel.requestHomeData(shopProductIn);
    }

    @Override
    public void responseHomeData(ShopProductOut shopProductOut) {
        getView().showHomeResponse(shopProductOut.getList());
    }

    @Override
    public void loadMore(SearchProductRequest searchProductRequest) {
        searchProductRequest.setPage(page + 1);
        searchProductRequest.setRows(rows);
        iShopCenterModel.requestAllProduct(searchProductRequest, Constants.ListStatus.LOADMORE);
    }

    @Override
    public void loadData(SearchProductRequest searchProductRequest) {
        searchProductRequest.setPage(1);
        searchProductRequest.setRows(rows);
        iShopCenterModel.requestAllProduct(searchProductRequest, Constants.ListStatus.REFRESH);
    }

    @Override
    public boolean hasMore() {
        if (Math.ceil(total/rows) > page){
            return true;
        }
        return false;
    }

    @Override
    public void refreshData(ProductList productList) {
        this.page = productList.getPage();
        this.total = productList.getTotal();
        getView().getRefreshData(productList.getList());
    }

    @Override
    public void loadMoreData(ProductList productList) {
        this.page = productList.getPage();
        this.total = productList.getTotal();
        getView().getLoadMoreData(productList.getList());
    }

    @Override
    public void delProduct(ProductIn productIn) {
        iShopCenterModel.delProduct(productIn);
    }

    @Override
    public void responseDelProduct(String msg) {
        getView().showDelSuccess(msg);
    }

    @Override
    public void upAndDownProduct(ProductUpAndDownIn productUpAndDownIn) {
        iShopCenterModel.upAndDownProduct(productUpAndDownIn);
    }

    @Override
    public void responseUpAndDownProduct(String msg) {
        getView().responseUpAndDownProduct(msg);
    }

    @Override
    public void requestProductCategory(ProductCategoryIn productCategoryIn) {
        iShopCenterModel.requestProductCategory(productCategoryIn);
    }

    @Override
    public void responseProductCategory(ProductCategoryOut productCategoryOut) {
        getView().responseProductCategory(productCategoryOut.getList());
    }
}