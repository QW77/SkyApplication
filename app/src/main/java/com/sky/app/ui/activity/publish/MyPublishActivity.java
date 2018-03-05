package com.sky.app.ui.activity.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.ProductIn;
import com.sky.app.bean.SupplyDetail;
import com.sky.app.contract.PublishContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewActivity;
import com.sky.app.library.component.DashlineItemDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MyPublishPresenter;
import com.sky.app.ui.adapter.MyPublishAdaptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的发布
 */
public class MyPublishActivity extends BaseViewActivity<PublishContract.IMyPublishPresenter>
        implements PublishContract.IMyPublishlView, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.normal_toolbar)
    Toolbar toolbar;
    @BindView(R.id.app_title)
    TextView title;

    @BindView(R.id.app_swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_my_publish_list)
    LuRecyclerView mRecyclerView;

    MyPublishAdaptor publishAdaptor;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    List<SupplyDetail> supplyDetailArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_mine_my_publish);
    }

    @Override
    protected PublishContract.IMyPublishPresenter presenter() {
        return new MyPublishPresenter(this, this);
    }

    @Override
    protected void init() {
        title.setText(R.string.app_my_publish_string);
        toolbar.setNavigationIcon(R.mipmap.app_back_arrow_icon);

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void initViewsAndEvents() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new DashlineItemDivider(context,
                        AppUtils.dip2px(context, 12), AppUtils.dip2px(context, 12)));

        publishAdaptor = new MyPublishAdaptor(context, supplyDetailArrayList);
        publishAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int pos) {
                Intent i = new Intent(context, PublishDetailActivity.class);
                i.putExtra("product_id", supplyDetailArrayList.get(pos).getProduct_id());
                startActivity(i);
            }

        });
        publishAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                switch (itemView.getId()){
                    case R.id.del://删除
                        ProductIn productIn = new ProductIn();
                        productIn.setProduct_id(supplyDetailArrayList.get(pos).getProduct_id());
                        getPresenter().del(productIn);
                        break;
                    case R.id.edit://编辑
                        Intent i = new Intent(context, PublishActivity.class);
                        i.putExtra("product_id", supplyDetailArrayList.get(pos).getProduct_id());
                        startActivity(i);
                        break;
                }
            }
        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(publishAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    getPresenter().loadMore();
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
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestoryActivity() {

    }

    @Override
    public void getRefreshData(List<SupplyDetail> list) {
        publishAdaptor.add(list);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<SupplyDetail> list) {
        publishAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDelSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData();
    }
}
