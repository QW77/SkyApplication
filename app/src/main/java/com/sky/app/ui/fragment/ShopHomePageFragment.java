package com.sky.app.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.sky.app.R;
import com.sky.app.bean.ProductCategory;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.ProductResponse;
import com.sky.app.bean.ShopProductDetail;
import com.sky.app.bean.ShopProductIn;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.ShopCenterPresenter;
import com.sky.app.ui.activity.product.ProductDetailActivity;
import com.sky.app.ui.activity.seller.UserGPS;
import com.sky.app.ui.adapter.ShopProductAdaptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 商品主页
 * Created by Administrator on 2017/2/30.
 */
public class ShopHomePageFragment extends BaseViewFragment<ShopContract.IShopCenterPresenter>
        implements ShopContract.IShopCenterView {

    @BindView(R.id.product_list)
    RecyclerView recyclerView;
    @BindView(R.id.app_scrollview)
    ScrollView scrollView;
    private String seller_id;

    private ShopProductAdaptor shopProductAdaptor;
    List<ShopProductDetail> shopProductDetails = new ArrayList<>();

    @Override
    protected void init() {
    }

    @Override
    protected void initViewsAndEvents() {
        seller_id = getArguments().getString("seller_id");
        shopProductAdaptor = new ShopProductAdaptor(context, shopProductDetails,
                new ShopProductAdaptor.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int parentId, int childId) {
                Intent i = new Intent(context, ProductDetailActivity.class);
                i.putExtra("product_id", shopProductDetails.get(parentId).getAll_products().get(childId).getProduct_id());
                startActivity(i);
            }

            @Override
            public void onItemViewClick(View itemView, int parentId, int childId) {
                Intent i = new Intent(context, ProductDetailActivity.class);
                i.putExtra("product_id", shopProductDetails.get(parentId).getRecom_products().get(childId).getProduct_id());
                startActivity(i);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 12),
                        AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        recyclerView.setAdapter(shopProductAdaptor);

        reFresh();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_shop_center_1;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected ShopContract.IShopCenterPresenter presenter() {
        return new ShopCenterPresenter(context, this);
    }

    @Override
    public void showHomeResponse(List<ShopProductDetail> list) {
        shopProductAdaptor.add(list);
    }

    @Deprecated
    @Override
    public void getRefreshData(List<ProductResponse> list) {

    }

    @Deprecated
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

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    /**
     * 刷新数据
     */
    private void reFresh(){
        ShopProductIn shopProductIn = new ShopProductIn();
        shopProductIn.setUser_id(seller_id);
        getPresenter().requestHomeData(shopProductIn);
    }
}