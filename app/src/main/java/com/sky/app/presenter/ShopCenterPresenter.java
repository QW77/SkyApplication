package com.sky.app.presenter;

import android.content.Context;

import com.sky.app.bean.ProductCategoryIn;
import com.sky.app.bean.ProductCategoryOut;
import com.sky.app.bean.ProductIntroduceIn;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.ProductList;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.ShopProductIn;
import com.sky.app.bean.ShopProductOut;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.bean.Constants;
import com.sky.app.library.base.presenter.BasePresenter;
import com.sky.app.model.ShopCenterModel;
import com.sky.app.ui.activity.seller.UserGPS;

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
        getView().hideProgress();
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
    public void requestProductCategory(ProductCategoryIn productCategoryIn) {
        iShopCenterModel.requestProductCategory(productCategoryIn);
    }

    @Override
    public void responseProductCategory(ProductCategoryOut productCategoryOut) {
        getView().responseProductCategory(productCategoryOut.getList());
    }

    @Override
    public void requestDescData(ProductIntroduceIn productIntroduceIn) {
        getView().showProgress();
        iShopCenterModel.requestDescData(productIntroduceIn);
    }

    @Override
    public void responseDescData(ProductIntroduceOut productIntroduceOut) {
        getView().responseDescData(productIntroduceOut);
        getView().hideProgress();
    }

//    @Override
//    public void getLonglat(String id) {
//        iShopCenterModel.getLonglat(id);
//    }
//
//    @Override
//    public void setLonglat(UserGPS userGPS) {
//        getView().setLonglat(userGPS);
//    }
}