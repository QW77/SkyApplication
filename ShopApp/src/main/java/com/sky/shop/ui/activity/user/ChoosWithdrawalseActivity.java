package com.sky.shop.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.MyAccountBankAdaptor;
import com.sky.shop.bean.AccountBean;
import com.sky.shop.bean.BankIn;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.custom.RecycleViewDivider;
import com.sky.shop.presenter.fragment.AccountDetailFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 選擇申请提现
 * Created by zhanlf on 2017/6/8
 */
public class ChoosWithdrawalseActivity extends BaseViewActivity<AccountContract.IAccountDetailPresenter>
        implements AccountContract.IAccountDetailView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView driverTitle;
    @BindView(R.id.driver_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.account_bank_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.add_account_tv)
    TextView add_account_tv;
    MyAccountBankAdaptor myAccountBankAdaptor;
    List<AccountBean> myAccountlList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_account_chack_list);
    }
    @Override
    protected void init() {
        driverTitle.setText("选择提现账户");
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(this, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.driver_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, AppUtils.dip2px(this, 12),
                AppUtils.getSystemColor(this, R.color.sky_color_f2f2f2)));
        myAccountBankAdaptor = new MyAccountBankAdaptor(this, myAccountlList);
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
        myAccountBankAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                AccountBean bean = new AccountBean();
                bean=myAccountlList.get(pos);
                Intent intent = new Intent();
                intent.putExtra("accountbean", bean);
                setResult(0, intent);
                finish();
            }
        });
    }

    @Override
    protected AccountContract.IAccountDetailPresenter presenter() {
        return new AccountDetailFragmentPresenter(this, this);
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
        T.showShort(this, msg);
        onRefresh();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(this, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @OnClick(R.id.add_account_tv)
    void clickAccount(){
        startActivity(new Intent(context, Add_Account_ApplyActivity.class));
    }
}