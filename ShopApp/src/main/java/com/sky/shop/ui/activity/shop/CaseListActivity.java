package com.sky.shop.ui.activity.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.CaseGridviewAdapter;
import com.sky.shop.bean.Case;
import com.sky.shop.bean.CaseIn;
import com.sky.shop.bean.ProductIntroduceOut;
import com.sky.shop.contract.SellerContract;
import com.sky.shop.presenter.SellerCenterPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.sky.shop.R.id.del;

/**
 * 案例列表
 */
public class CaseListActivity extends BaseViewActivity<SellerContract.ISellerCenterPresenter>
    implements SellerContract.ISellerCenterView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.app_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.case_gridview)
    GridView autoHeightGridView;
    private CaseGridviewAdapter caseGridviewAdapter;
    private List<Case> caseList = new ArrayList<>();
    private CaseIn caseIn = new CaseIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_case_list);
    }

    @Override
    protected SellerContract.ISellerCenterPresenter presenter() {
        return new SellerCenterPresenter(context, this);
    }

    @Override
    protected void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置刷新时动画的颜色，可以设置4个
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            swipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_case_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        caseGridviewAdapter = new CaseGridviewAdapter(context, caseList);
        caseGridviewAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.edit:
                        Intent i = new Intent(context, CaseEditActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("caseList", caseList.get((Integer) v.getTag()));
                        i.putExtras(bundle);
                        startActivity(i);
                        break;
                    case del:
                        getPresenter().requestDelCase(caseList.get((Integer) v.getTag()));
                        break;
                }
            }
        });
        autoHeightGridView.setAdapter(caseGridviewAdapter);
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
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    @Deprecated
    @Override
    public void showSuccess(ProductIntroduceOut productIntroduceOut) {

    }

    @Override
    public void getRefreshData(List<Case> list) {
        swipeRefreshLayout.setRefreshing(false);
        caseList.clear();
        caseList.addAll(list);
        caseGridviewAdapter.notifyDataSetChanged();
    }

    @Deprecated
    @Override
    public void responseEditShopInfo(String msg) {

    }

    @Deprecated
    @Override
    public void responseAddCase(String msg) {

    }

    @Deprecated
    @Override
    public void responseEditCase(String msg) {

    }

    @Override
    public void responseDelCase(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        swipeRefreshLayout.setRefreshing(false);
        T.showShort(context, error);
    }

    @OnClick(R.id.upload)
    void upload(){
        startActivity(new Intent(context, CaseEditActivity.class));
    }

    @Override
    public void onRefresh() {
        getPresenter().loadData(caseIn);
    }
}
