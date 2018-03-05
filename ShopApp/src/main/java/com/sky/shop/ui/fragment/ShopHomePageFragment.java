package com.sky.shop.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.RecycleViewDivider;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.L;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.ShopProductAdaptor;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductResponse;
import com.sky.shop.bean.ShopProductDetail;
import com.sky.shop.bean.ShopProductIn;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.ShopCenterPresenter;
import com.sky.shop.ui.activity.shop.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品主页
 * Created by Administrator on 2017/2/30.
 */
public class ShopHomePageFragment extends BaseViewFragment<ShopContract.IShopCenterPresenter>
        implements ShopContract.IShopCenterView{

    @BindView(R.id.product_list)
    RecyclerView recyclerView;

    private ShopProductAdaptor shopProductAdaptor;
    List<ShopProductDetail> shopProductDetails = new ArrayList<>();

    @Override
    protected void init() {
    }

    @Override
    protected void initViewsAndEvents() {
        shopProductAdaptor = new ShopProductAdaptor(context, shopProductDetails,
                new ShopProductAdaptor.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int parentId, int childId) {
                Intent k = new Intent(context, ProductDetailActivity.class);
                k.putExtra("product_id", shopProductDetails.get(parentId).getAll_products()
                        .get(childId).getProduct_id());
                startActivity(k);
            }

            @Override
            public void onItemViewClick(View itemView, int parentId, int childId) {
                Intent k = new Intent(context, ProductDetailActivity.class);
                k.putExtra("product_id", shopProductDetails.get(parentId).getRecom_products()
                        .get(childId).getProduct_id());
                startActivity(k);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 12),
                        AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        recyclerView.setAdapter(shopProductAdaptor);
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

    @Deprecated
    @Override
    public void showHomeResponse(List<ShopProductDetail> list) {
        shopProductAdaptor.add(list);
    }

    @Deprecated
    @Override
    public void getRefreshData(List<ProductResponse> list) {

    }

    @Override
    public void getLoadMoreData(List<ProductResponse> list) {

    }

    @Deprecated
    @Override
    public void showDelSuccess(String msg) {

    }

    @Override
    public void responseUpAndDownProduct(String msg) {

    }

    @Deprecated
    @Override
    public void responseProductCategory(List<ProductCategory> productCategories) {

    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }

    public void onRefresh() {
        ShopProductIn shopProductIn = new ShopProductIn();
        getPresenter().requestHomeData(shopProductIn);
    }

    @Override
    public void onResume() {
        super.onResume();
        L.msg("===onResume==");
        onRefresh();
    }

    @Override
    public void onPause() {
        super.onPause();
        L.msg("====onPause===");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        L.msg("===onCreateView===");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.msg("====onAttach=====");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.msg("====onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.msg("===onActivityCreated===");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.msg("====onDetach===");
    }

    @Override
    public void onStart() {
        super.onStart();
        L.msg("==onStart===");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.msg("===onStop==");
    }
}