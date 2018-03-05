package com.sky.app.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.ProductCategory;
import com.sky.app.bean.ProductIntroduceOut;
import com.sky.app.bean.ProductResponse;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.bean.ShopProductDetail;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.ShopCenterPresenter;
import com.sky.app.ui.activity.product.ProductDetailActivity;
import com.sky.app.ui.activity.seller.UserGPS;
import com.sky.app.ui.adapter.ShopProductListAdaptor;
import com.sky.app.ui.custom.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 全部商品
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
    private SearchProductRequest searchProductRequest;
    private String seller_id;

    @Override
    protected void init() {
        seller_id = getArguments().getString("seller_id");
        searchProductRequest = (SearchProductRequest) getArguments().getSerializable("search");

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.sky.app.refresh");
        filter.setPriority(Integer.MAX_VALUE);
        getActivity().registerReceiver(myReceiver, filter);
    }

    @Override
    protected void initViewsAndEvents() {
        searchProductRequest.setUser_id(seller_id);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppUtils.dip2px(context, 1), false));

        shopProductListAdaptor = new ShopProductListAdaptor(context, supplyDetailArrayList);
        shopProductListAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int pos) {
                Intent i = new Intent(context, ProductDetailActivity.class);
                i.putExtra("product_id", supplyDetailArrayList.get(pos).getProduct_id());
                startActivity(i);
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

        onRefresh();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_shop_center_all_product;
    }

    @Override
    protected void onDestoryFragment() {
        getActivity().unregisterReceiver(myReceiver);
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

    /**
     * 接口广播
     */
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.sky.app.refresh".equals(intent.getAction())){
                searchProductRequest.setOne_dir_id(intent.getStringExtra("one_dir_id"));
                searchProductRequest.setTwo_dir_id(intent.getStringExtra("two_dir_id"));
                searchProductRequest.setProduct_name(intent.getStringExtra("product_name"));
                onRefresh();
            }
        }

    };

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(getActivity());
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }
}