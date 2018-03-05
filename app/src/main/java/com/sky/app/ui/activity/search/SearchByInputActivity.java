package com.sky.app.ui.activity.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.sky.app.R;
import com.sky.app.bean.CollectGoods;
import com.sky.app.bean.SearchProductRequest;
import com.sky.app.contract.ShopContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.SearchShopPresenter;
import com.sky.app.ui.activity.product.ProductDetailActivity;
import com.sky.app.ui.adapter.ProductListAdaptor;
import com.sky.app.ui.custom.DropdownButton;
import com.sky.app.ui.custom.DropdownButtonsController;
import com.sky.app.ui.custom.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 通过输入关键字来搜索
 */
public class SearchByInputActivity extends BaseViewActivity<ShopContract.ISearchShopPresenter>
        implements ShopContract.ISearchShopView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.app_search_toolbar)
    Toolbar toolbar;

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_order_list_rv)
    LuRecyclerView mRecyclerView;

    ProductListAdaptor productListAdaptor;
    List<CollectGoods> supplyDetailArrayList = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;

    @BindView(R.id.app_edit_content)
    EditText searchContent;
    @BindView(R.id.app_bg_layout)
    View bgLayout;
    @BindView(R.id.app_default_sort_btn)
    DropdownButton btn1;
    @BindView(R.id.app_distance_sort_btn)
    DropdownButton btn2;
    @BindView(R.id.app_filter_btn)
    DropdownButton btn3;

    @BindView(R.id.app_list_sort_dv)
    ListView listSortDv;//排序
    @BindView(R.id.app_filter_layout)
    RelativeLayout filterLayout;//筛选下拉框

    @BindView(R.id.app_price_begin_et)
    EditText beginPrice;
    @BindView(R.id.app_price_end_et)
    EditText endPrice;

    private SearchProductRequest searchProductRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_by_input);
    }

    @Override
    protected ShopContract.ISearchShopPresenter presenter() {
        return new SearchShopPresenter(context, this);
    }

    @Override
    protected void init() {
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }

        searchProductRequest = new SearchProductRequest();
        //初始化UI
        DropdownButtonsController.getInstance().setOnClickItem(new DropdownButtonsController.OnClickItem() {
            @Override
            public void item(String[] arrays, int position) {
                searchProductRequest.setMax_price("");
                searchProductRequest.setMin_price("");
                switch (position){
                    case 1:
                        searchProductRequest.setOrder_by("price_desc");
                        break;
                    case 2:
                        searchProductRequest.setOrder_by("price_asc");
                        break;
                    default:
                        searchProductRequest.setOrder_by("");
                        break;
                }
                onRefresh();
                DropdownButtonsController.getInstance().hide();
                DropdownButtonsController.getInstance().setSortTxt(arrays[position]);
            }
        });
        DropdownButtonsController.getInstance().setBgLayout(bgLayout);
        DropdownButtonsController.getInstance().setDefaultSortBtn(btn1);
        DropdownButtonsController.getInstance().setFilterBtn(btn3);
        DropdownButtonsController.getInstance().setFilterLayout(filterLayout);
        DropdownButtonsController.getInstance().setListSortDv(listSortDv);
        DropdownButtonsController.getInstance().setSellBtn(btn2);
        DropdownButtonsController.getInstance().init(context);
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppUtils.dip2px(context, 1), false));

        productListAdaptor = new ProductListAdaptor(context, supplyDetailArrayList);
        productListAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int pos) {
                Intent i = new Intent(context, ProductDetailActivity.class);
                i.putExtra("product_id", supplyDetailArrayList.get(pos).getProduct_id());
                startActivity(i);
            }

        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(productListAdaptor);
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
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @OnClick(R.id.app_bg_layout)
    public void dismissBackground(){
        DropdownButtonsController.getInstance().hide();
    }

    @OnClick(R.id.app_default_sort_btn)
    void defaultSort(){
        DropdownButtonsController.getInstance().show(0);
    }

    @OnClick(R.id.app_distance_sort_btn)
    void distanceSort(){
        DropdownButtonsController.getInstance().show(1);
        searchProductRequest.setOrder_by("sales_desc");
        searchProductRequest.setMax_price("");
        searchProductRequest.setMin_price("");
        onRefresh();
    }

    @OnClick(R.id.app_filter_btn)
    public void filterSort(){
        DropdownButtonsController.getInstance().show(2);
    }

    @OnClick(R.id.app_search_tv)
    void  search(){
        searchProductRequest.setProduct_name(searchContent.getText().toString().trim());
        onRefresh();
        DropdownButtonsController.getInstance().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData(searchProductRequest);
    }

    @OnClick(R.id.app_reset_tv)
    void clickReset(){
        beginPrice.setText("");
        endPrice.setText("");
    }

    @OnClick(R.id.app_comfirm_tv)
    void clickConfirm(){
        if (TextUtils.isEmpty(beginPrice.getText().toString().trim())){
            searchProductRequest.setMin_price("");
        }else{
            searchProductRequest.setMin_price(Long.parseLong(beginPrice.getText().toString().trim())*100 + "");
        }
        if (TextUtils.isEmpty(endPrice.getText().toString().trim())){
            searchProductRequest.setMax_price("");
        }else{
            searchProductRequest.setMax_price(Long.parseLong(endPrice.getText().toString().trim())*100 + "");
        }
        searchProductRequest.setOrder_by("");
        onRefresh();
        DropdownButtonsController.getInstance().hide();
    }

    @Override
    public void getRefreshData(List<CollectGoods> list) {
        productListAdaptor.add(list);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<CollectGoods> list) {
        productListAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }
}
