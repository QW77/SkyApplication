package com.sky.shop.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ListView;

import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.DashlineItemDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.DialogUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.QueryAccountAdaptor;
import com.sky.shop.bean.ApplyRecordBean;
import com.sky.shop.bean.ApplyRecordIn;
import com.sky.shop.contract.AccountContract;
import com.sky.shop.custom.DropdownAccountButtonsController;
import com.sky.shop.custom.DropdownButton;
import com.sky.shop.presenter.fragment.AccountQueryFragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 查询账户Tab页
 * Created by Administrator on 2017/2/30.
 */
public class QueryAccountPageFragment extends BaseViewFragment<AccountContract.IAccountQueryPresenter>
        implements AccountContract.IAccountQueryView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.driver_bg_layout)
    View background;
    @BindView(R.id.driver_list_sort_dv)
    ListView listSort;
    @BindView(R.id.driver_status_sort_btn)
    DropdownButton statusBtn;
    @BindView(R.id.driver_date_sort_btn)
    DropdownButton dateSort;
    QueryAccountAdaptor queryAccountAdaptor;

    @BindView(R.id.driver_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.account_query)
    LuRecyclerView mRecyclerView;
    List<ApplyRecordBean> applyRecordBeanArrayList = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private ApplyRecordIn applyRecordIn = new ApplyRecordIn();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    protected void init() {
        //初始化UI
        DropdownAccountButtonsController.getInstance().setDriverBgLayout(background);
        DropdownAccountButtonsController.getInstance().setDriverListSortDv(listSort);
        DropdownAccountButtonsController.getInstance().setQueryBtn(dateSort);
        DropdownAccountButtonsController.getInstance().setStatusBtn(statusBtn);
        DropdownAccountButtonsController.getInstance().init(context);
        //初始化UI
        DropdownAccountButtonsController.getInstance().setOnClickItem(new DropdownAccountButtonsController.OnClickItem() {
            @Override
            public void item(String[] arrays, int position) {
                applyRecordIn.setCreate_time("");
                switch (position){
                    case 0:
                        applyRecordIn.setState("");
                        break;
                    case 1:
                        applyRecordIn.setState("1");
                        break;
                    case 2:
                        applyRecordIn.setState("2");
                        break;
                }
                DropdownAccountButtonsController.getInstance().hide();
                DropdownAccountButtonsController.getInstance().setSortTxt(arrays[position]);
                onRefresh();
            }
        });

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
        mRecyclerView.addItemDecoration(new DashlineItemDivider(context,
                AppUtils.dip2px(context, 12), AppUtils.dip2px(context, 12)));
        queryAccountAdaptor = new QueryAccountAdaptor(context, applyRecordBeanArrayList);
        queryAccountAdaptor.setOnClickListener(new QueryAccountAdaptor.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
            }
        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(queryAccountAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    getPresenter().loadMore(applyRecordIn);
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
        return R.layout.app_account_query;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected AccountContract.IAccountQueryPresenter presenter() {
        return new AccountQueryFragmentPresenter(context, this);
    }

    @OnClick(R.id.driver_bg_layout)
    public void dismissBackground(){
        DropdownAccountButtonsController.getInstance().hide();
    }

    @OnClick(R.id.driver_status_sort_btn)
    public void clickStatus(){
        DropdownAccountButtonsController.getInstance().show(0);
    }

    @OnClick(R.id.driver_date_sort_btn)
    void clickDate(){
        DropdownAccountButtonsController.getInstance().show(1);
        DialogUtils.showDateDialog(getActivity(), new DialogUtils.IDatePickerDialog() {
            @Override
            public void showDate(String year, String month, String day) {
                applyRecordIn.setState("");
                applyRecordIn.setCreate_time(year + "-" + month + "-" + day);
                onRefresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData(applyRecordIn);
    }

    @Override
    public void getRefreshData(List<ApplyRecordBean> t) {
        queryAccountAdaptor.add(t);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<ApplyRecordBean> t) {
        queryAccountAdaptor.addAll(t);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}