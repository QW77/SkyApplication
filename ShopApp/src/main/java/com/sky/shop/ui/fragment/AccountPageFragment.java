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
import com.sky.shop.adaptor.MyAccountBankAdaptor;
import com.sky.shop.bean.AccountBean;
import com.sky.shop.bean.BankIn;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.custom.RecycleViewDivider;
import com.sky.shop.presenter.fragment.AccountDetailFragmentPresenter;
import com.sky.shop.ui.activity.user.Add_Account_ApplyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现账户Tab页
 * Created by Administrator on 2017/2/30.
 */
public class AccountPageFragment extends BaseViewFragment<AccountContract.IAccountDetailPresenter>
        implements AccountContract.IAccountDetailView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.driver_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.account_bank_list)
    RecyclerView mRecyclerView;

    MyAccountBankAdaptor myAccountBankAdaptor;
    List<AccountBean> myAccountlList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    protected void init() {
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.driver_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(context, 12),
                AppUtils.getSystemColor(context, R.color.sky_color_f2f2f2)));
        myAccountBankAdaptor = new MyAccountBankAdaptor(context, myAccountlList);
        myAccountBankAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                //删除
                BankIn bankIn = new BankIn();
                bankIn.setBank_id(myAccountlList.get(pos).getBank_id());
                getPresenter().del(bankIn);
            }
        });
        mRecyclerView.setAdapter(myAccountBankAdaptor);
        onRefresh();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.app_account_list;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected AccountContract.IAccountDetailPresenter presenter() {
        return new AccountDetailFragmentPresenter(context, this);
    }

    @Override
    public void onRefresh() {
        //加载数据
        mSwipeRefreshLayout.setRefreshing(true);
        getPresenter().loadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void getRefreshData(List<AccountBean> t) {
        myAccountBankAdaptor.add(t);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showDelSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.add_account_tv)
    void addAccount(){
        startActivity(new Intent(getActivity(), Add_Account_ApplyActivity.class));
    }
}