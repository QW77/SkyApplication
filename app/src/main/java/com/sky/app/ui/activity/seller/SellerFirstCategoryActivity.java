package com.sky.app.ui.activity.seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.FirstCategoryDetail;
import com.sky.app.bean.FirstCategoryOut;
import com.sky.app.bean.SellMessageComplete;
import com.sky.app.contract.SellerContract;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.SellerFristCategoryPresenter;
import com.sky.app.ui.adapter.FirstCategoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商户选择一级分类
 */
public class SellerFirstCategoryActivity extends BaseViewActivity<SellerContract
        .ISellerFirstCategoryPresenter> implements

        SellerContract.ISellerFirstCategory {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_first_category);
    }

    @Override
    protected SellerContract.ISellerFirstCategoryPresenter presenter() {
        return new SellerFristCategoryPresenter(this, this);
    }

    @Override
    protected void initViewsAndEvents() {
        getPresenter().getFirstCatogoryData();
    }

    @Override
    protected void init() {
        appTitle.setText("选择商户类型");
        normalToolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        normalToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                    Intent intent = new Intent(SellerFirstCategoryActivity.this, SellerSecondCategoryActivity
                            .class);
                    SellMessageComplete sellMessageComplete = (SellMessageComplete) getIntent().getSerializableExtra("upload");
                    sellMessageComplete.setOne_dir_id(one_dir_id);
                    intent.putExtra("upload",sellMessageComplete);
                    startActivity(intent);
                    break;
                }

        }
    }

    @Override
    protected void onDestoryActivity() {

    }
}
