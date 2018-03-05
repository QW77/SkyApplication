package com.sky.shop.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.ProductCategoryAdaptor;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.bean.ProductCategoryIn;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.custom.RecycleViewDivider;
import com.sky.shop.presenter.CategoryPresenter;
import com.sky.shop.ui.activity.shop.SecondCategoryActivity;
import com.sky.shop.utils.AppDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 目录
 * Created by Administrator on 2017/2/30.
 */
public class ShopCategoryPageFragment extends BaseViewFragment<ShopContract.ICategoryPresenter>
        implements ShopContract.ICategoryView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.category_list)
    RecyclerView categoryList;
    private ProductCategoryAdaptor productCategoryAdaptor;
    private List<ProductCategory> productCategories = new ArrayList<>();

    @Override
    protected void init() {
        //设置刷新时动画的颜色，可以设置4个
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            swipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            swipeRefreshLayout.setOnRefreshListener(this);
        }

        categoryList.setLayoutManager(new LinearLayoutManager(context));
        categoryList.setHasFixedSize(true);
        categoryList.addItemDecoration(
                new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 1),
                        AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        productCategoryAdaptor = new ProductCategoryAdaptor(context, productCategories, 1);
        productCategoryAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, final int pos) {
                switch (itemView.getId()){
                    case R.id.list_item:
                        Intent p = new Intent(context, SecondCategoryActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("category", productCategories.get(pos));
                        p.putExtras(bundle);
                        getActivity().startActivity(p);
                        break;
                    case R.id.update:
                        AppDialogUtils.showAddFirstDialog(getActivity(), productCategories.get(pos).getOne_dir_name(),
                                new IFirstCategoryCallBack() {
                            @Override
                            public void back(String categoryName) {
                                ProductCategory productCategory = new ProductCategory();
                                productCategory.setLevel(1);
                                productCategory.toString();
                                productCategory.setOne_dir_id(productCategories.get(pos).getOne_dir_id());
                                productCategory.setOne_dir_name(categoryName);
                                getPresenter().requestUpdateCategory(productCategory);
                            }
                        });
                        break;
                    case R.id.del:
                        ProductCategory productCategory = new ProductCategory();
                        productCategory.setLevel(1);
                        productCategory.setOne_dir_id(productCategories.get(pos).getOne_dir_id());
                        getPresenter().requestDelCategory(productCategory);
                        break;
                }
            }
        });
        categoryList.setAdapter(productCategoryAdaptor);
    }

    @Override
    protected void initViewsAndEvents() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_shop_category;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected ShopContract.ICategoryPresenter presenter() {
        return new CategoryPresenter(context, this);
    }

    @OnClick(R.id.add)
    void clickAdd(){
        AppDialogUtils.showAddFirstDialog(getActivity(), "", new IFirstCategoryCallBack() {
            @Override
            public void back(String categoryName) {
                ProductCategory productCategory = new ProductCategory();
                productCategory.setLevel(1);
                productCategory.setOne_dir_name(categoryName);
                getPresenter().requestAddFirstCategory(productCategory);
            }
        });
    }

    @Override
    public void responseProductCategory(List<ProductCategory> productCategories){
        productCategoryAdaptor.add(productCategories);
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void responseAddFirstCategory(String msg) {
        AppDialogUtils.closeAddFirstDialog();
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void responseDelSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void responseUpdateSuccess(String msg) {
        AppDialogUtils.closeAddFirstDialog();
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        ProductCategoryIn productCategoryIn = new ProductCategoryIn();
        productCategoryIn.setLevel("1");
        getPresenter().requestProductCategory(productCategoryIn);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        swipeRefreshLayout.setRefreshing(false);
    }

    public interface IFirstCategoryCallBack{
        void back(String categoryName);
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
}