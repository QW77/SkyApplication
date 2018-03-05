package com.sky.shop.ui.activity.shop;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.ProductCategoryAdaptor;
import com.sky.shop.bean.ProductCategory;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.custom.RecycleViewDivider;
import com.sky.shop.presenter.CategoryPresenter;
import com.sky.shop.ui.fragment.ShopCategoryPageFragment;
import com.sky.shop.utils.AppDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 二级级分类
 */
public class SecondCategoryActivity extends BaseViewActivity<ShopContract.ICategoryPresenter>
        implements ShopContract.ICategoryView {

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.category_list)
    RecyclerView categoryList;
    private ProductCategoryAdaptor productCategory2Adaptor;
    private List<ProductCategory> productCategories2 = new ArrayList<>();
    private ProductCategory productCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_shop_category2);
    }

    @Override
    protected ShopContract.ICategoryPresenter presenter() {
        return new CategoryPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void init() {
        title.setText(R.string.app_second_category_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        productCategory = (ProductCategory) getIntent().getSerializableExtra("category");

        categoryList.setLayoutManager(new LinearLayoutManager(context));
        categoryList.setHasFixedSize(true);
        categoryList.addItemDecoration(
                new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 2),
                        AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        productCategory2Adaptor = new ProductCategoryAdaptor(context, productCategories2, 2);
        productCategory2Adaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, final int pos) {
                switch (itemView.getId()){
                    case R.id.update://修改二级目录
                        AppDialogUtils.showAddSecondDialog(SecondCategoryActivity.this, productCategories2.get(pos).getTwo_dir_name(),
                                new ShopCategoryPageFragment.IFirstCategoryCallBack() {
                                    @Override
                                    public void back(String categoryName) {
                                        ProductCategory productCategory = new ProductCategory();
                                        productCategory.setLevel(2);
                                        productCategory.setOne_dir_id(productCategories2.get(pos).getOne_dir_id());
                                        productCategory.setTwo_dir_id(productCategories2.get(pos).getTwo_dir_id());
                                        productCategory.setTwo_dir_name(categoryName);
                                        getPresenter().requestUpdateCategory(productCategory);
                                    }
                                });
                        break;
                    /*
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
                     */
                    case R.id.del://删除目录
                        ProductCategory a = new ProductCategory();
                        a.setLevel(2);
                        a.setTwo_dir_id(productCategory.getTwo_dirs().get(pos).getTwo_dir_id());
                        getPresenter().requestDelCategory(a);
                        break;
                }
            }
        });
        categoryList.setAdapter(productCategory2Adaptor);
        productCategory2Adaptor.add(productCategory.getTwo_dirs());
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Deprecated
    @Override
    public void responseProductCategory(List<ProductCategory> productCategories) {
    }

    @Deprecated
    @Override
    public void responseAddFirstCategory(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @Override
    public void responseDelSuccess(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @Override
    public void responseUpdateSuccess(String msg) {
        T.showShort(context, msg);
        finish();
    }

    @OnClick(R.id.add)//新增
    void clickAdd(){
        AppDialogUtils.showAddSecondDialog(this, "", new ShopCategoryPageFragment.IFirstCategoryCallBack() {
            @Override
            public void back(String categoryName) {
                ProductCategory d = new ProductCategory();
                d.setLevel(2);
                d.setOne_dir_id(productCategory.getOne_dir_id());
                d.setTwo_dir_name(categoryName);
                getPresenter().requestAddFirstCategory(d);
            }
        });
    }
}
