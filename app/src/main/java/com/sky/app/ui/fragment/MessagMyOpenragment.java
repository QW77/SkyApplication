package com.sky.app.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.AskItem;
import com.sky.app.bean.Message;
import com.sky.app.bean.MessageAskTable;
import com.sky.app.contract.UserContract;
import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.DashlineItemDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.app.presenter.MessagePresenter;
import com.sky.app.ui.adapter.MessageAdaptor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 消息中心
 * Created by Administrator on 2017/2/30.
 */
public class MessagMyOpenragment extends BaseViewFragment<UserContract.IMessagePresenter>
        implements UserContract.IMessageView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.app_title)
    TextView title;
    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_message_list)
    LuRecyclerView mRecyclerView;

    MessageAdaptor messageAdaptor;
    List<Message> supplyDetailArrayList = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;

    @Override
    protected void init() {
        title.setText(R.string.app_message_string);
        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, AppUtils.dip2px(context, 48));
            mSwipeRefreshLayout.setColorSchemeResources(R.color.main_colorPrimary);
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    protected void initViewsAndEvents() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DashlineItemDivider(context,
                AppUtils.dip2px(context, 12), AppUtils.dip2px(context, 12)));

        messageAdaptor = new MessageAdaptor(context, supplyDetailArrayList);
        messageAdaptor.setOnClickListener(new BaseRecyclerAdapter.OnClickListener() {
            @Override
            public void onClick(View itemView, int pos) {
                Message message = new Message();
                message.setMsg_id(supplyDetailArrayList.get(pos).getMsg_id());
                getPresenter().deleteMessage(message);
            }
        });
        mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(messageAdaptor);
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
    protected int getContentViewLayoutID() {
        return R.layout.app_message_center;
    }

    @Override
    protected void onDestoryFragment() {

    }

    @Override
    protected UserContract.IMessagePresenter presenter() {
        return new MessagePresenter(context, this);
    }

    @Override
    public void showError(String error) {
        super.showError(error);
        T.showShort(context, error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setRefreshing(true);
        getPresenter().loadData();
    }

    @Override
    public void showAskTable(MessageAskTable messageAskTable) {

    }

    @Override
    public void showAskUp() {

    }

    @Override
    public void showAskComment() {

    }

    @Override
    public void getRefreshData(List<Message> list) {
        messageAdaptor.add(list);
        if(mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void getRefreshData1(List<AskItem.ListBean> list) {

    }

    @Override
    public void showAskList(List<AskItem.ListBean> list) {

    }


    @Override
    public void getLoadMoreData(List<Message> list) {
        messageAdaptor.addAll(list);
        mSwipeRefreshLayout.setRefreshing(false);
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuccess(String msg) {
        T.showShort(context, msg);
        onRefresh();
    }
}