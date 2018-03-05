package com.sky.shop.ui.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.FirstCategoryAdapter;
import com.sky.shop.bean.FirstCategoryDetail;
import com.sky.shop.bean.FirstCategoryOut;
import com.sky.shop.bean.SellMessageComplete;
import com.sky.shop.contract.ShopContract;
import com.sky.shop.presenter.activity.SellerFristCategoryPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商户选择一级分类
 */
public class SellerFirstCategoryActivity extends BaseViewActivity<ShopContract
        .ISellerFirstCategoryPresenter> implements

        ShopContract.ISellerFirstCategory {

    @BindView(R.id.app_title)
    TextView appTitle;
    @BindView(R.id.normal_toolbar)
    Toolbar normalToolbar;
    @BindView(R.id.seller_first_category_gridview)
    GridView sellerFirstCategoryGridview;
    @BindView(R.id.choose)
    TextView choose;
    @BindView(R.id.choose_relative)
    RelativeLayout chooseRelative;
    @BindView(R.id.seller_first_category_next)
    TextView sellerFirstCategoryNext;

    private FirstCategoryAdapter firstCategoryAdapter;
    private List<FirstCategoryDetail> firstCategoryDetailList = new ArrayList<>();
    private String one_dir_id;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_first_category);
    }

    @Override
    protected ShopContract.ISellerFirstCategoryPresenter presenter() {
        return new SellerFristCategoryPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
        getPresenter().getFirstCatogoryData();
    }

    @Override
    protected void init() {
        appTitle.setText("选择商户类型");
//        normalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
//        normalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        type = getIntent().getStringExtra("type");//个人还是企业

        firstCategoryAdapter = new FirstCategoryAdapter(context, firstCategoryDetailList);
        sellerFirstCategoryGridview.setAdapter(firstCategoryAdapter);
        sellerFirstCategoryGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chooseRelative.setVisibility(View.VISIBLE);
                choose.setText(firstCategoryDetailList.get(i).getOne_dir_name());
                one_dir_id=firstCategoryDetailList.get(i).getOne_dir_id();
            }
        });

    }


    @Override
    public void firstCatogoryDataSuccess(FirstCategoryOut firstCategoryOut) {
        firstCategoryDetailList.addAll(firstCategoryOut.getList());
        firstCategoryAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.choose_relative, R.id.seller_first_category_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choose_relative:
                chooseRelative.setVisibility(View.GONE);
                one_dir_id="";
                break;
            case R.id.seller_first_category_next:

                if(TextUtils.isEmpty(one_dir_id)){
                    T.showShort(this,"请选择分类");
                    return;
                }else{
                    Intent intent = new Intent(context, SellerSecondCategoryActivity.class);
                    SellMessageComplete sellMessageComplete = new SellMessageComplete();
                    sellMessageComplete.setOne_dir_id(one_dir_id);
                    intent.putExtra("upload", sellMessageComplete);
                    intent.putExtra("type", type);
                    startActivity(intent);
                    break;
                }

        }
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void showProgress() {
        super.showProgress();
        DialogUtils.showLoading(this);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        DialogUtils.hideLoading();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
    }
}
