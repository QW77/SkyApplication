package com.sky.shop.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.ShopProductListAdaptor;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.bean.ProductIn;
import com.sky.shop.bean.ProductResponse;
import com.sky.shop.bean.ProductUpAndDownIn;
import com.sky.shop.bean.SearchProductRequest;
import com.sky.shop.bean.ShopProductDetail;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.custom.CategoryMenuDialog;
import com.sky.shop.custom.GridSpacingItemDecoration;
import com.sky.shop.presenter.activity.ShopCenterPresenter;
import com.sky.shop.ui.activity.shop.ProductAddActivity;
import com.sky.shop.ui.activity.shop.ProductDetailActivity;
import com.sky.shop.ui.activity.shop.ProductEditActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商品
 * Created by Administrator on 2017/2/30.
 */
public class AllProductPageFragment extends BaseViewFragment<ShopContract.IShopCenterPresenter>
        implements ShopContract.IShopCenterView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_product_list_rv)
    LuRecyclerView mRecyclerView;

    ShopProductListAdaptor shopProductListAdaptor;
    List<ProductResponse> supplyDetailArrayList = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private SearchProductRequest searchProductRequest = new SearchProductRequest();

    @Override
    protected void init() {
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppUtils.dip2px(context, 1), false));

        shopProductListAdaptor = new ShopProductListAdaptor(context, supplyDetailArrayList);
        shopProductListAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int pos) {
                Intent k = new Intent(context, ProductDetailActivity.class);
                k.putExtra("product_id", supplyDetailArrayList.get(pos).getProduct_id());
                startActivity(k);
            }

        });
        shopProductListAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                switch (itemView.getId()){
                    case R.id.edit:
                        Intent a = new Intent(context, ProductEditActivity.class);
                        a.putExtra("product_id", supplyDetailArrayList.get(pos).getProduct_id());
                        startActivity(a);
                        break;
                    case R.id.del:
                        ProductIn productIn = new ProductIn();
                        productIn.setProduct_id(supplyDetailArrayList.get(pos).getProduct_id());
                        getPresenter().delProduct(productIn);
                        break;
                    case R.id.upAndDown:
                        ProductUpAndDownIn productUpAndDownIn = new ProductUpAndDownIn();
                        productUpAndDownIn.setState(supplyDetailArrayList.get(pos).getState() == 0 ? 1 : 0);
                        productUpAndDownIn.setProduct_id(supplyDetailArrayList.get(pos).getProduct_id());
                        getPresenter().upAndDownProduct(productUpAndDownIn);
                        break;
                }
            }
        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(shopProductListAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    getPresenter().loadMore(searchProductRequest);
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent ,android.R.color.white);
        //设置底部加载文字提示
        mRecyclerView.setFooterViewHint("拼命加载中", "已经全部加载完", "网络不给力啊，点击再试一次吧");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_shop_center_all_product;
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
    }

    @Override
    public void getRefreshData(List<ProductResponse> list) {
        shopProductListAdaptor.add(list);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
    }

    @Override
    public void getLoadMoreData(List<ProductResponse> list) {
        shopProductListAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
    }

    @Override
    public void showDelSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void responseUpAndDownProduct(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void responseProductCategory(List<ProductCategory> productCategories) {
        new CategoryMenuDialog(context, productCategories, new CategoryMenuDialog.CallBack() {
            @Override
            public void data(String one, String two,String one_name,String two_name) {
                searchProductRequest.setOne_dir_id(one);
                searchProductRequest.setTwo_dir_id(two);
                onRefresh();
            }
        }).show();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData(searchProductRequest);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.publish_product)
    void publishPro(){
        startActivity(new Intent(context, ProductAddActivity.class));
    }

    @OnClick(R.id.product_category)
    void clickCategoryFilter(){
        //请求商品分类
        ProductCategoryIn productCategoryIn = new ProductCategoryIn();
        productCategoryIn.setLevel("1");
        getPresenter().requestProductCategory(productCategoryIn);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
}