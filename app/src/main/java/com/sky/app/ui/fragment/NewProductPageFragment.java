package com.sky.app.ui.fragment;

import com.sky.app.R;
import com.sky.app.bean.ProductCategory;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.ProductResponse;
import com.sky.app.bean.ShopProductDetail;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.ui.activity.seller.UserGPS;

import java.util.List;


/**
 * 新品上架
 * Created by Administrator on 2017/2/30.
 */
public class NewProductPageFragment extends BaseViewFragment<ShopContract.IShopCenterPresenter>
        implements ShopContract.IShopCenterView {

    @Override
    protected void init() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_home_page;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected ShopContract.IShopCenterPresenter presenter() {
        return null;
    }

    @Override
    public void showHomeResponse(List<ShopProductDetail> list) {

    }

    @Override
    public void getRefreshData(List<ProductResponse> list) {

    }

    @Override
    public void getLoadMoreData(List<ProductResponse> list) {

    }

    @Deprecated
    @Override
    public void responseProductCategory(List<ProductCategory> productCategories) {

    }

    @Deprecated
    @Override
    public void responseDescData(ProductIntroduceOut productIntroduceOut) {

    }

//    @Override
//    public void setLonglat(UserGPS userGPS) {
//
//    }
}