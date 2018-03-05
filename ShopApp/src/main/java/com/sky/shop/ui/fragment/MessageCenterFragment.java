package com.sky.shop.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.app.library.base.adaptor.BaseRecyclerAdapter;
import com.sky.app.library.base.ui.BaseViewFragment;
import com.sky.app.library.component.DashlineItemDivider;
import com.sky.app.library.component.recycler.interfaces.OnLoadMoreListener;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerView;
import com.sky.app.library.component.recycler.recyclerview.LuRecyclerViewAdapter;
import com.sky.app.library.utils.AppUtils;
import com.sky.app.library.utils.T;
import com.sky.shop.R;
import com.sky.shop.adaptor.MessageAdaptor;
import com.sky.shop.bean.ForgetIn;
import com.sky.shop.bean.Message;
import com.sky.shop.contract.UserContract;
import com.sky.shop.presenter.fragment.MessagePresenter;
import com.sky.shop.ui.activity.OpenIMShop.OpenIMLoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 消息中心
 * Created by Administrator on 2017/2/30.
 */
public class MessageCenterFragment extends BaseViewFragment<UserContract.IMessagePresenter>
        implements UserContract.IMessageView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.app_title)
    TextView title;
    //    @BindView(R.id.app_shop_icon)
//    ImageView shopIcon;
    @BindView(R.id.app_swipe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.app_message_list)
    LuRecyclerView mRecyclerView;
    @BindView(R.id.alibaba)
    ImageView aliBB;

    MessageAdaptor messageAdaptor;
    List<Message> supplyDetailArrayList = new ArrayList<>();
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private ForgetIn forgetIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.alibaba)
    void ALbaba() {
//        Fragment fragment = mIMKit.getChattingFragment(target, appkey);
//        startActivityFromFragment(fragment,mIMKit.getConversationActivityIntent(),0);
        startActivity(new Intent(getActivity(), OpenIMLoginActivity.class));
    }

    @Override
    protected void init() {
        title.setText(R.string.app_message_center_string);
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
                if (getPresenter().hasMore()) {
                    getPresenter().loadMore();
                } else {
                    mRecyclerView.setNoMore(true);
                }
            }
        });

        //设置底部加载颜色
        mRecyclerView.setFooterViewColor(R.color.main_colorAccent, R.color.main_colorAccent, android.R.color.white);
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
    public void getRefreshData(List<Message> list) {
        messageAdaptor.add(list);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mRecyclerView.refreshComplete(20);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.app_shop_icon)
    void clickLeftIcon() {
        Intent a = new Intent();
        a.setAction("com.sky.shop.left");
        getActivity().sendBroadcast(a);
    }

    @Override
    public void onResume() {
        super.onResume();
//        ImageHelper.getInstance().displayDefinedImage(UserBean.getInstance().getUserCache().getPic_url(),
//                shopIcon, R.mipmap.app_default_photo, R.mipmap.app_default_icon);
    }
}