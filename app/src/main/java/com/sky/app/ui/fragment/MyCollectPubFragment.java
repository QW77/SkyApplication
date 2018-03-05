package com.sky.app.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sky.app.R;
import com.sky.app.bean.MyCollect;
import com.sky.app.bean.MyCollectIn;
import com.sky.app.bean.ProductIn;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.DashlineItemDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MyCollectPresenter;
import com.sky.app.ui.activity.publish.PublishDetailActivity;
import com.sky.app.ui.adapter.MyCollectPubAdaptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 订单Tab页
 * Created by Administrator on 2017/2/30.
 */
public class MyCollectPubFragment extends BaseViewFragment<UserContract.IMyCollectPresenter>
        implements UserContract.IMyCollectView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_my_collect_pub)
    LuRecyclerView mRecyclerView;

    MyCollectPubAdaptor myCollectPubAdaptor;

    List<MyCollect> myCollects = new ArrayList<>();
    MyCollectIn myCollectIn = new MyCollectIn();

    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;

    @Override
    protected void init() {
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.driver_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
        myCollectIn.setType(3);
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(
                new DashlineItemDivider(context,
                        AppUtils.dip2px(context, 12), AppUtils.dip2px(context, 12)));

        myCollectPubAdaptor = new MyCollectPubAdaptor(context, myCollects);
        myCollectPubAdaptor.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View itemView, int pos) {
                Intent i = new Intent(context, PublishDetailActivity.class);
                i.putExtra("product_id", myCollects.get(pos).getProduct_id());
                getActivity().startActivity(i);
            }

        });
        myCollectPubAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                switch (itemView.getId()){
                    case R.id.del://删除
                        ProductIn productIn = new ProductIn();
                        productIn.setProduct_id(myCollects.get(pos).getProduct_id());
                        getPresenter().del(productIn);
                        break;
                }
            }
        });

        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(myCollectPubAdaptor);
        mRecyclerView.setAdapter(mLuRecyclerViewAdapter);
        mRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (getPresenter().hasMore()){
                    getPresenter().loadMore(myCollectIn);
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
        return R.layout.app_my_collect_pub;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected UserContract.IMyCollectPresenter presenter() {
        return new MyCollectPresenter(context, this);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData(myCollectIn);
    }

    @Override
    public void getRefreshData(List<MyCollect> t) {
        myCollectPubAdaptor.add(t);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getLoadMoreData(List<MyCollect> t) {
        myCollectPubAdaptor.addAll(t);
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
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}